package com.github.guilhermebauer.studymanagement.response;

public class LoginResponse {


    private String token;
    private String email;

    public LoginResponse(String username, String token) {
        this.email = username;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
