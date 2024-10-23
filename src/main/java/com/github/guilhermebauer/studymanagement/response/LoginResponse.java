package com.github.guilhermebauer.studymanagement.response;

public class LoginResponse {

    private String email;
    private String token;


    public LoginResponse(String email, String token) {
        this.email = email;
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
