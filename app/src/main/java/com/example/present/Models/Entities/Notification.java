package com.example.present.Models.Entities;

public class Notification {
    private int id;
    private int requestCode;
    private int creatorId;
    private int receiverId;
    private String action;
    private int chatSenderId;
    private int meetingId;
    private String contentTitle;
    private String contentText;
    private int state;//0 unread, 1 read

    public Notification(){}


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", requestCode=" + requestCode +
                ", creatorId=" + creatorId +
                ", receiverId=" + receiverId +
                ", action='" + action + '\'' +
                ", chatSenderId=" + chatSenderId +
                ", meetingId=" + meetingId +
                ", contentTitle='" + contentTitle + '\'' +
                ", contentText='" + contentText + '\'' +
                ", state=" + state +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getChatSenderId() {
        return chatSenderId;
    }

    public void setChatSenderId(int chatSenderId) {
        this.chatSenderId = chatSenderId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
