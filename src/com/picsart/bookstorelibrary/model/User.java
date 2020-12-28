package com.picsart.bookstorelibrary.model;

import java.util.StringJoiner;

public class User {

    private Integer userId;
    private String name;
    private String surName;
    private String userName;
    private String email;
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "", "\n");
        joiner.add(userId.toString());
        joiner.add(name + " " + surName);
        joiner.add(userName);
        joiner.add(email);
        joiner.add(password);

        return joiner.toString();
    }
}
