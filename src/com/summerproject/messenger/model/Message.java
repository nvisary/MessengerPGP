package com.summerproject.messenger.model;

public class Message {
    private String name;
    private String message;

    public Message(String username, String message) {
        name = username;
        this.message = message;
    }


    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
