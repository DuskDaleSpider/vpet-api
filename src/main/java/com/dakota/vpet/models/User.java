package com.dakota.vpet.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    private String id;
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
