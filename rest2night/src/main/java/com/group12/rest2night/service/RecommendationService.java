package com.group12.rest2night.service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;

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
            genres = args.get("genre").toLowerCase().split("\\|");
        }

        String occupation = args.get("occupation").toLowerCase();

        ArrayList<ArrayList<Integer>> userLists = getAllUsers(occupation, age, gender);
        HashSet<Integer> movies = movieService.getMoviesWith(Arrays.asList(genres));

        if (movies.size() <= 0) {
            throw new RuntimeException("No movie found that satisfies requested genres");
        }

        return getTopN(userLists, movies, 10);
        
    }

    public ArrayList<ArrayList<Integer>> getAllUsers(String occupation, Integer age, String gender) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        lists.add(userService.getUsersWith(occupation, age, gender));
        lists.add(userService.getUsersWith("", age, gender));
        lists.add(userService.getUsersWith(occupation, age, ""));
        lists.add(userService.getUsersWith(occupation, -1, gender));
        lists.add(userService.getUsersWith("", age, ""));
        lists.add(userService.getUsersWith(occupation, -1, ""));
        lists.add(userService.getUsersWith("", -1, gender));
        lists.add(userService.getUsersWith("", -1, ""));
        return lists;
    }

    public List<Movie> getTopN(ArrayList<ArrayList<Integer>> userLists, HashSet<Integer> movies, int n)  {
        int count = 0;
        int index = 0;
        ArrayList<Integer> printedList = new ArrayList<>();
        List<Movie> listOfMovies = new ArrayList<>();
        while (count < n) {
            int allvotes = 0;
            double totalMean = 0;
            if (index >= userLists.size()) break;
            ArrayList<Integer> list = userLists.get(index);
            HashMap<Integer, int[]> ratings = getRatings(list, movies);
            HashMap<Integer, Double> scores = new HashMap<>();
            for (Integer k : ratings.keySet()) {
                allvotes += ratings.get(k)[1];
                totalMean += (double) ratings.get(k)[0] / (double) ratings.get(k)[1];
            }
            if (allvotes != 0)
                totalMean /= allvotes;
            for (Integer k : ratings.keySet()) {
                scores.put(k, weightedRating((double) ratings.get(k)[0] / (double) ratings.get(k)[1],
                        (double) ratings.get(k)[1],
                        10,
                        totalMean));
            }
            LinkedHashMap<Integer, Double> sortedScores = scores.entrySet().stream()
                    .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> {
                                throw new AssertionError();
                            },
                            LinkedHashMap::new
                    ));
            for (Integer k : sortedScores.keySet()) {
                //NOTE: DECREASE THRESHOLD TO IMPROVE TIME EFFICIENCY
                if (sortedScores.get(k) >= 2.5) {
                    if (printedList.contains(k))
                        continue;
                    else
                        printedList.add(k);

                    listOfMovies.add(movieService.findMovie(k).orElse(null));
                    count++;
                }
                if (count >= n) break;
            }
            index++;
        }
        return listOfMovies;
    }

    public static double weightedRating(double R, double v, double m, double C) {
        return (R * v + C * m) / (v + m);
    }

    public HashMap<Integer, int[]> getRatings(ArrayList<Integer> userIDs, HashSet<Integer> movieIDs) {
        HashMap<Integer, int[]> ratingList = new HashMap<>();

        for (int userId : userIDs) {
            for (Rating rating : userService.getRatingsOfUser(userId)) {
                int movieId = rating.getMovieId();
                if (movieIDs.contains(movieId)) {
                    int[] movieRating = ratingList.getOrDefault(movieId, new int[]{0, 0});
                    movieRating[0] += rating.getRating();
                    movieRating[1]++;
                    ratingList.put(movieId, movieRating);
                }
            }
        }
        return ratingList;
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
