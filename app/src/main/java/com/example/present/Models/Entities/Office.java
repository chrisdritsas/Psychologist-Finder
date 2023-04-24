package com.example.present.Models.Entities;

public class Office {
    private int id;
    private int userDataId;
    private int views;
    private String address;
    private String addressCode;
    private String phone;
    private String mobile;
    private String biography;
    private String workHours;
    private double onlinePrice;
    private double meetingPrice;

    public Office(){

    }
    public Office(int id){
        this.id=id;
    }

    public Office(int id, int userDataId, String address, String addressCode, String phone, String mobile, String biography, String workHours, double onlinePrice, double meetingPrice) {
        this.id = id;
        this.userDataId = userDataId;
        this.address = address;
        this.addressCode = addressCode;
        this.phone = phone;
        this.mobile = mobile;
        this.biography = biography;
        this.workHours = workHours;
        this.onlinePrice = onlinePrice;
        this.meetingPrice = meetingPrice;
    }
    public Office(int id, int userDataId, int views, String address, String addressCode, String phone, String mobile, String biography, String workHours, double onlinePrice, double meetingPrice) {
        this.id = id;
        this.userDataId = userDataId;
        this.views = views;
        this.address = address;
        this.addressCode = addressCode;
        this.phone = phone;
        this.mobile = mobile;
        this.biography = biography;
        this.workHours = workHours;
        this.onlinePrice = onlinePrice;
        this.meetingPrice = meetingPrice;
    }

    public Office(int userDataId, String address, String addressCode, String phone, String mobile, String biography, String workHours, double onlinePrice, double meetingPrice) {
        this.userDataId = userDataId;
        this.address = address;
        this.addressCode = addressCode;
        this.phone = phone;
        this.mobile = mobile;
        this.biography = biography;
        this.workHours = workHours;
        this.onlinePrice = onlinePrice;
        this.meetingPrice = meetingPrice;
    }
    public Office(String address, String addressCode, String phone, String mobile, String biography, String workHours, double onlinePrice, double meetingPrice) {
        this.address = address;
        this.addressCode = addressCode;
        this.phone = phone;
        this.mobile = mobile;
        this.biography = biography;
        this.workHours = workHours;
        this.onlinePrice = onlinePrice;
        this.meetingPrice = meetingPrice;
    }

    public void fixStrings() {
        if (this.getAddress() != null) {
            this.setAddress(this.getAddress().trim());
        }
        if (this.getAddressCode() != null) {
            this.setAddressCode(this.getAddressCode().trim());
        }
        if (this.getPhone() != null) {
            this.setPhone(this.getPhone().trim());
        }
        if (this.getMobile() != null) {
            this.setMobile(this.getMobile().trim());
        }
        if (this.getBiography() != null) {
            this.setBiography(this.getBiography().trim());
        }
        if (this.getWorkHours() != null) {
            this.setWorkHours(this.getWorkHours().trim());
        }
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", userDataId=" + userDataId +
                ", address='" + address + '\'' +
                ", addressCode='" + addressCode + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", biography='" + biography + '\'' +
                ", workHours='" + workHours + '\'' +
                ", onlinePrice='" + onlinePrice + '\'' +
                ", meetingPrice='" + meetingPrice + '\'' +
                ", views='" + views + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(int userDataId) {
        this.userDataId = userDataId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public double getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(double onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public double getMeetingPrice() {
        return meetingPrice;
    }

    public void setMeetingPrice(double meetingPrice) {
        this.meetingPrice = meetingPrice;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}