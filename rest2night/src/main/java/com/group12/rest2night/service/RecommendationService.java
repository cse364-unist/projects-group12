package com.group12.rest2night.service;

import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.entity.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

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
            throw new RuntimeErrorException(null, "Gender key is not given\n");
        else if (args.get("age") == null)
            throw new RuntimeErrorException(null, "Age key is not given\n");
        else if (args.get("occupation") == null)
            throw new RuntimeErrorException(null, "Occupation key is not given\n") ;
        else if (args.get("genre") == null)
            throw new RuntimeErrorException(null, "Genre key is not given\n");

        //* Gender check
        String gender = args.get("gender").toLowerCase();

        //* Age check
        if (args.get("age").length() > 0) {
            age = Integer.parseInt(args.get("age"));
        } else age = -1;

        //* Genres check
        if (args.get("genre").length() != 0) {
            genres = args.get("genre").toLowerCase().split("\\|");
        }

        String occupation = args.get("occupation").toLowerCase();

        try {

            ArrayList<ArrayList<Integer>> userLists = getAllUsers(occupation, age, gender);
            HashSet<Integer> movies = movieService.getMoviesWith(Arrays.asList(genres));

            if (movies.size() <= 0) {
                throw new RuntimeErrorException(null, "No movie found that satisfies requested genres");
            }

            return getTopN(userLists, movies, 10);
        }

        catch (IOException e) {
            new IOException(e);
        }
        return null;
    }

    public ArrayList<ArrayList<Integer>> getAllUsers(String occupation, Integer age, String gender) throws IOException {
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

    public List<Movie> getTopN(ArrayList<ArrayList<Integer>> userLists, HashSet<Integer> movies, int n) throws IOException {
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
                if (sortedScores.get(k) >= 3.0) {
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

    public HashMap<Integer, int[]> getRatings(ArrayList<Integer> userIDs, HashSet<Integer> movieIDs) throws IOException {
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
}
