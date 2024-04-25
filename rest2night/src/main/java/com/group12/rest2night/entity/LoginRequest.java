package com.group12.rest2night.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
