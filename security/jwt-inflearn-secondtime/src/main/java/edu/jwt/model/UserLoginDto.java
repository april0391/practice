package edu.jwt.model;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;
}
