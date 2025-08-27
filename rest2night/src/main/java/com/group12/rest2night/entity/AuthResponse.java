package com.group12.rest2night.entity;

public class AuthResponse {
    private String token;

    public AuthResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
