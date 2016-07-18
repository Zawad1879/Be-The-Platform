package me.argha.tonu.model;


import java.io.Serializable;

public class Message implements Serializable {
    String id, message, createdAt;
//    User user;
    User sender;
    User receiver;

    public Message() {
    }

    public Message(String id, String message, String createdAt, User sender, User receiver) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.sender = sender;
        this.receiver = receiver;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}