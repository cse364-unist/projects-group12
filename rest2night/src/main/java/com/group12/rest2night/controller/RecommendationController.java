package com.group12.rest2night.controller;

import java.util.*;


import com.group12.rest2night.entity.Movie;
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

}