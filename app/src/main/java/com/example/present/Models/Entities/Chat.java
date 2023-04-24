package com.example.present.Models.Entities;

public class Chat {
    private int id;
    private int senderId;
    private int receiverId;
    private String message;

    public Chat(){

    }
    public Chat(int id, int senderId, int receiverId, String message) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public Chat(int receiverId, String message) {
        this.receiverId = receiverId;
        this.message = message;
    }
    public Chat(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public void fixStrings() {
        if (this.getMessage() != null) {
            this.setMessage(this.getMessage().trim());
        }
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
