package com.group12.rest2night.service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;

import com.group12.rest2night.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingRepository ratingRepository;

    public List<Movie> getMovies(HashMap<String, String> args) {
        int age;
        String[] genres = new String[5];
        if (args.get("gender") == null)
            throw new RuntimeException("Gender key is not given\n");
        else if (args.get("age") == null)
            throw new RuntimeException("Age key is not given\n");
        else if (args.get("occupation") == null)
            throw new RuntimeException("Occupation key is not given\n") ;
        else if (args.get("genre") == null)
            throw new RuntimeException("Genre key is not given\n");

        String gender = args.get("gender").toLowerCase();
        if (args.get("age").length() > 0) {
            age = Integer.parseInt(args.get("age"));
        } else age = -1;

        if (args.get("genre").length() != 0) {
            genres = args.get("genre").split("\\|");
        }

        String occupation = args.get("occupation").toLowerCase();

        List<List<Integer>> userLists = getAllUsers(occupation, age, gender);
        List<Integer> movies = movieService.getMoviesWith(Arrays.asList(genres));

        if (movies.size() <= 0) {
            throw new RuntimeException("No movie found that satisfies requested genres");
        }

        return getTopN(userLists, movies, 10);
        
    }

    public List<List<Integer>> getAllUsers(String occupation, Integer age, String gender) {
        List<List<Integer>> lists = new ArrayList<>();

        if (occupation == null) occupation = "";
        if (age == null) age = -1;
        if (gender == null) gender = "";

        lists.add(userService.getUsersWith(occupation, age, gender));

        if (!occupation.equals("") || age != -1 || !gender.equals("")) {
            if (!occupation.equals("")) {
                lists.add(userService.getUsersWith("", age, gender));
            }
            if (age != -1) {
                lists.add(userService.getUsersWith(occupation, -1, gender));
            }
            if (!gender.equals("")) {
                lists.add(userService.getUsersWith(occupation, age, ""));
            }

            if (age != -1 && !gender.equals("")) {
                lists.add(userService.getUsersWith("", age, ""));
            }
            if (!occupation.equals("") && !gender.equals("")) {
                lists.add(userService.getUsersWith(occupation, -1, ""));
            }
            if (!occupation.equals("") && age != -1) {
                lists.add(userService.getUsersWith("", -1, gender));
            }

            lists.add(userService.getUsersWith("", -1, ""));
        }

        return lists;
    }

    public List<Movie> getTopN(List<List<Integer>> userLists, List<Integer> movies, int n) {
        Set<Integer> movieSet = new HashSet<>(movies);
        Set<Integer> printedSet = new HashSet<>();
        List<Movie> listOfMovies = new ArrayList<>();

        int count = 0;
        int index = 0;

        System.out.println("MOVIE SET SIZE: " + movies.size());

        while (count < n && index < userLists.size()) {
            List<Integer> users = userLists.get(index++);
            if (users.isEmpty()) continue;
            System.out.println("USERS SIZE: " + users.size());

            long start = System.nanoTime();
            Map<Integer, int[]> ratings;
//            if(users.size() < movies.size()){
//                ratings = getRatings2(users, movieSet);
//            } else {
//                ratings = getRatingsSecond(new HashSet<>(users), movieSet);
//            }
            ratings = getRatings(users, movies);
            long end = System.nanoTime();
            System.out.println("Time for first method: " + (end - start) / 1_000_000 + " ms");

            if (ratings.isEmpty()) continue;

            int allVotes = 0;
            double totalMean = 0.0;
            Map<Integer, Double> scores = new HashMap<>();

            for (Map.Entry<Integer, int[]> entry : ratings.entrySet()) {
                int movieId = entry.getKey();
                int sum = entry.getValue()[0];
                int votes = entry.getValue()[1];

                if (votes > 0) {
                    double avg = (double) sum / votes;
                    allVotes += votes;
                    totalMean += avg;
                    scores.put(movieId, avg);
                }
            }

            if (allVotes > 0) totalMean /= allVotes;

            for (Map.Entry<Integer, Double> entry : scores.entrySet()) {
                int movieId = entry.getKey();
                double avg = entry.getValue();
                int votes = ratings.get(movieId)[1];

                double wr = weightedRating(avg, votes, 10, totalMean);
                scores.put(movieId, wr);
            }

            PriorityQueue<Map.Entry<Integer, Double>> pq =
                    new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));
            pq.addAll(scores.entrySet());

            while (!pq.isEmpty() && count < n) {
                Map.Entry<Integer, Double> entry = pq.poll();
                int movieId = entry.getKey();
                double score = entry.getValue();

                if (score >= 2.5 && printedSet.add(movieId)) {
                    Movie movie = movieService.findMovie(movieId).orElse(null);
                    if (movie != null) {
                        listOfMovies.add(movie);
                        count++;
                    }
                }
            }
        }

        return listOfMovies;
    }


    public static double weightedRating(double R, double v, double m, double C) {
        return (R * v + C * m) / (v + m);
    }

    public Map<Integer, int[]> getRatings(List<Integer> userIDs, List<Integer> movieIDs) {
        Map<Integer, int[]> ratingMap = new HashMap<>();

        // Single DB query: fetch all ratings for given users & movies
        List<Rating> ratings = ratingRepository.findByUserIdAndMovieIdIn(userIDs, movieIDs);

        for (Rating rating : ratings) {
            int movieId = rating.getMovieId();
            ratingMap.computeIfAbsent(movieId, k -> new int[2]);
            int[] movieRating = ratingMap.get(movieId);
            movieRating[0] += rating.getRating();
            movieRating[1]++;
        }
        return ratingMap;
    }


    public Map<Integer, int[]> getRatings2(List<Integer> userIDs, Set<Integer> movieIDs) {
        Map<Integer, int[]> ratingMap = new HashMap<>();

        for (int userId : userIDs) {
            List<Rating> ratings = userService.getRatingsOfUser(userId);
            for (Rating rating : ratings) {
                int movieId = rating.getMovieId();
                if (!movieIDs.contains(movieId)) continue;

                ratingMap.computeIfAbsent(movieId, k -> new int[2]);
                int[] movieRating = ratingMap.get(movieId);
                movieRating[0] += rating.getRating();
                movieRating[1]++;
            }
        }
        return ratingMap;
    }


    public List<Movie> getMoviesOnOccasion(String occasion){

        List<String> genres = new ArrayList<>();

        if(occasion.equals("familyNight")){
            String[] family_genres = {"Animation", "Adventure", "Comedy", "Fantasy", "Sci-Fi", 
                                        "Action", "Musical", "Drama", "Children's"};
            genres = Arrays.asList(family_genres);
        } else if(occasion.equals("dateNight")){
            String[] dateGenres = {"Romance", "Adventure", "Comedy", "Fantasy", 
                                "Action", "Thriller", "Drama", "Mystery"};
            genres = Arrays.asList(dateGenres);
        } else throw new RuntimeException("No such occasion");

        Collections.shuffle(genres);

        HashMap<String,String> args = new HashMap<>();
        args.put("gender", "");
        args.put("age", "");
        args.put("occupation", "");
        String stringOfGenres = genres.get(0) + "|" + genres.get(3) + "|" + genres.get(6);
        args.put("genre", stringOfGenres);

        return getMovies(args);
        
    }
}
