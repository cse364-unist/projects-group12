package com.group12.rest2night.controller;

import java.io.IOException;
import java.util.*;


import com.group12.rest2night.entity.Movie;
import com.group12.rest2night.service.MovieService;
import com.group12.rest2night.service.RecommendationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    
    @Autowired
    private MovieService movieService;
    
    @GetMapping("/recommendation/type1")
    public ResponseEntity<List<Movie>> getMovies(@RequestBody String input) {
        try{
            JSONObject js = new JSONObject(input.toLowerCase());
            HashMap<String,String> args = new HashMap<>();
            for(String key: js.keySet())
                args.put(key,js.getString(key));
            return ResponseEntity.ok(recommendationService.getMovies(args));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/movies/recommendations")
    public ResponseEntity<?> getRecommendationsOnOccasion(@RequestBody String input) throws IOException {
        JSONObject js = null; 
        try{
            js = new JSONObject(input.toLowerCase());
            HashMap<String,String> args = new HashMap<>();
            for(String key: js.keySet())
                args.put(key,js.getString(key));
        
            if (args.containsKey("occasion") && args.get("occasion").equalsIgnoreCase("family night")) {
                String[] familyGenres = {"Animation", "Adventure", "Comedy", "Fantasy", "Sci-Fi", 
                                        "Action", "Musical", "Drama", "Mystery", "Family"};
                List<String> genreList = new ArrayList<>();
                Collections.addAll(genreList, familyGenres);
                Collections.shuffle(genreList);
                String[] randomGenres = new String[3];
                for (int i = 0; i < 3; i++) {
                    randomGenres[i] = genreList.get(i);
                }
                Set<String> familyGenresSet = new HashSet<>(Arrays.asList(randomGenres));
                List<Movie> movies = movieService.allMovies();
                List<Movie> filteredMovies = filterMoviesByGenres(movies, familyGenresSet);
                return ResponseEntity.ok(filteredMovies);
            }
            // Add a default return statement
            return ResponseEntity.ok("No movies found.");
        } catch (Exception e){
            return ResponseEntity.ok("Please pass JSON in the right format.\n");
        }
    }


    private List<Movie> filterMoviesByGenres(List<Movie> movies, Set<String> randomGenres) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            for (String genre : movie.getGenres()) {
                if (randomGenres.contains(genre)) {
                    filteredMovies.add(movie);
                    break;
                }
            }
        }
        return filteredMovies;
    }

}