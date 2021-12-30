package com.bridgelabz.jwtaexample.Model;

import lombok.Data;

public @Data class jwtRequest {
    String username;
    String password;

    public jwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Override
    public String toString(){
        return "JwtRequest{" +
                "username='" + username + '\'' +
                ",password='" + password + '\'' +
                '}';
    }
}
