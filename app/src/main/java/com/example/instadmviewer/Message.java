package com.example.instadmviewer;

import java.time.ZonedDateTime;

public class Message {
    private String sender;
    private ZonedDateTime dateTime;
    private String text;

    public Message(String sender, ZonedDateTime dateTime, String text) {
        this.sender = sender;
        this.dateTime = dateTime;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getText() {
        return text;
    }
}
