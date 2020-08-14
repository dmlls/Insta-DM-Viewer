package com.example.instadmviewer;

import java.util.LinkedList;

public class Conversation {
    private LinkedList<Message> messages;

    public Conversation() {}

    public Conversation(LinkedList<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }
}
