package com.example.aseproject.messages;

public class Message {
    private String id;
    private String title;
    private String message;
    private String messageType;

    Message(String id, String title, String message, String messageType) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.messageType = messageType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
