package com.group12.rest2night.service;

import com.group12.rest2night.utils.Utils;
import org.json.JSONArray;

import java.io.IOException;
import java.util.*;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class Recommendation {

    public static String recommendMovies(HashMap<String, String> args) throws IOException {
        int limit = 10;
        if (args.size() > 2) return "Please, pass exactly 1 or 2 keys to JSON!\n";
        if (args.get("limit") != null) limit = Integer.parseInt(args.get("limit"));
        try {
            String[] genres = Utils.getGenres(args.get("title"));
            ArrayList<ArrayList<Integer>> userLists = new ArrayList<ArrayList<Integer>>();
            userLists.add(Utils.getUsers("", -1, -1, ""));
            HashMap<Integer, String[]> first_movies = Utils.getMovies(genres, args.get("title").toLowerCase());
            HashMap<Integer, String[]> second_movies = Utils.getMovies(args.get("title").toLowerCase());
            for(Integer k: first_movies.keySet())
                second_movies.remove(k);
            ArrayList<Integer> firstMovieID = new ArrayList<>(first_movies.keySet());
            ArrayList<Integer> secondMovieID = new ArrayList<>(second_movies.keySet());
            Collections.sort(firstMovieID);
            Collections.sort(secondMovieID);
            JSONArray topN1 = Utils.getTopN(userLists, firstMovieID, first_movies, limit);
            JSONArray topN2 = Utils.getTopN(userLists, secondMovieID, second_movies, limit - topN1.length());
            topN1.putAll(topN2);
            return topN1.toString(2);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.toString();
        } catch (NullPointerException e) {
            return "Title is not given\n";
        }
    }

    public static String getMovies(HashMap<String, String> args) {
        String gender;
        int age;
        String work;
        boolean is_empty = false;
        String[] genres = new String[5];
        StringBuilder errorMessage = new StringBuilder();
        HashMap<String, Integer> workID = new HashMap<>();
        Utils.setOccupationHash(workID); // now workID contains all mappings

        Set<String> genreTypes = new HashSet<String>();
        try {
            Utils.setGenres(genreTypes);
        } catch (Exception e) {
            return e.toString();
        }
        if (args.get("gender") == null)
            return "Gender key is not given\n";
        else if (args.get("age") == null)
            return "Age key is not given\n";
        else if (args.get("occupation") == null)
            return "Occupation key is not given\n";
        else if (args.get("genre") == null)
            return "Genre key is not given\n";
        //* Args check
        if (args.size() != 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            errorMessage.append("Please, pass exactly 4 keys to JSON!\n");
            errorMessage.append("Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n");
            return errorMessage.toString();
        }

        //* Gender check
        gender = args.get("gender").toLowerCase();
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            errorMessage.append("Please, provide a proper argument for Gender\n");
            errorMessage.append("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n");
            return errorMessage.toString();
        }

        //* Age check
        if (args.get("age").length() > 0) {
            for (int i = 0; i < args.get("age").length(); i++) {
                if (!Character.isDigit(args.get("age").charAt(i))) {
                    errorMessage.append("Please, enter a valid argument for age!\n");
                    errorMessage.append("It should be a positive integer, containing only digits!\n");
                    errorMessage.append("Age shall not exceed " + Integer.MAX_VALUE + "\n");
                    return errorMessage.toString();
                }
            }
            age = Integer.parseInt(args.get("age"));
        } else age = -1;

        //* Genres check
        if (args.get("genre").length() == 0) {
            is_empty = true;
        } else {
            genres = args.get("genre").toLowerCase().split("\\|");
            HashSet<String> set = new HashSet<>();
            for (String genre : genres) {
                set.add(genre);
                if (!genreTypes.contains(genre)) {
                    errorMessage.append("There is not such registered genre as ").append(genre).append("\n");
                    return errorMessage.toString();
                }
            }

            if (set.size() != genres.length) {
                errorMessage.append("Please enter valid input for genres\n");
                errorMessage.append("Genres should not repeat\n");
                return errorMessage.toString();
            }
        }


        //! toLowerCase may fail for ! character? or \?

        try {
            work = args.get("occupation").toLowerCase();
            Integer occup_id = workID.get(work);
            if (work.compareTo("") == 0) occup_id = 0;
            //* Occupation check
            if (occup_id == null) {
                errorMessage.append("There is no such registered occupation as ").append(work).append("!\n");
                errorMessage.append("If you want to see some other ratings, please use \"other\" as an argument\n");
                return errorMessage.toString();
            }

            //* Here, real implementation begins
            ArrayList<ArrayList<Integer>> userLists = Utils.getAllUsers(work, occup_id, age, gender);
            HashMap<Integer, String[]> movies = !is_empty ? Utils.getMovies(genres, "") : Utils.getMovies("");
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args.get("genre")).append("\n");
                return errorMessage.toString();
            }
            Collections.sort(movieID);
            JSONArray top10 = Utils.getTopN(userLists, movieID, movies, 10);
            return top10.toString(2);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
            return e.toString();
        }
    }
}
