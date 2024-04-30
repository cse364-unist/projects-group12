package com.group12.rest2night.controller;

import java.util.*;
import com.group12.rest2night.service.Recommendation;
import org.json.JSONObject;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    @GetMapping("/users/recommendations")
    public String getMovies(@RequestBody String input) {
        JSONObject js = null;
        try{
            js = new JSONObject(input.toLowerCase());
            HashMap<String,String> args = new HashMap<>();
            for(String key: js.keySet())
                args.put(key,js.getString(key));
            return Recommendation.getMovies(args);
        }catch (Exception e){
            return "Please pass json in right format\n";
        }
    }
    @GetMapping("/movies/recommendations")
    public String getRecommendations(@RequestBody String input) throws IOException {
        JSONObject js = null;
        try {
            HashMap<String,String> args = new HashMap<>();
            js = new JSONObject(input.toLowerCase());
            for (String key : js.keySet()) {
                if (key.equals("limit")) args.put(key, Integer.toString(js.getInt(key)));
                else args.put(key, js.getString(key));
            }
            return Recommendation.recommendMovies(args);
        }catch (Exception e){
            return "Please pass json in right format\n";
        }
    }
}