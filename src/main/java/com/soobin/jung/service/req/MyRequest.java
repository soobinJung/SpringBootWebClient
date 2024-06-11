package com.soobin.jung.service.req;

public class MyRequest {
    private String username;
    private String password;
    private String email;

    // 기본 생성자
    public MyRequest() {
    }

    // 매개변수 있는 생성자
    public MyRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}