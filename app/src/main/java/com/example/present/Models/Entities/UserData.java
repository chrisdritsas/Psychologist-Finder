package com.example.present.Models.Entities;

public class UserData {

    private int id;
    private int userId;
    private String name;
    private String surname;
    private String address;
    private String addressCode;
    private String city;
    private String telephone;
    private String profilePicture;
    private Office office;


    public UserData() {

    }

    public UserData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserData(String name, String surname, String address, String addressCode, String city, String telephone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.addressCode = addressCode;
        this.city = city;
        this.telephone = telephone;
    }

    public UserData(int id, int user_id, String name, String surname, String address, String addressCode, String city, String telephone) {
        this.id = id;
        this.userId = user_id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.addressCode = addressCode;
        this.city = city;
        this.telephone = telephone;
    }

    public UserData(int id, int user_id, String name, String surname, String city, String profilePicture, Office office) {
        this.id = id;
        this.userId = user_id;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.profilePicture = profilePicture;
        this.office = office;

    }

    public UserData(int user_id, String name, String surname, String address, String addressCode, String city, String telephone, String profilePicture) {
        this.userId = user_id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.addressCode = addressCode;
        this.city = city;
        this.telephone = telephone;
        this.profilePicture = profilePicture;
    }

    public void fixStrings() {
        if (this.getName() != null) {
            this.setName(this.getName().trim());
        }
        if (this.getSurname() != null) {
            this.setSurname(this.getSurname().trim());
        }
        if (this.getAddress() != null) {
            this.setAddress(this.getAddress().trim());
        }
        if (this.getAddressCode() != null) {
            this.setAddressCode(this.getAddressCode().trim());
        }
        if (this.getCity() != null) {
            this.setCity(this.getCity().trim());
        }
        if (this.getTelephone() != null) {
            this.setTelephone(this.getTelephone().trim());
        }

    }

    @Override
    public String toString() {
        if (office != null) {
            return "UserData{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", address='" + address + '\'' +
                    ", addressCode='" + addressCode + '\'' +
                    ", city='" + city + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", office=" + office.toString() +
                    '}';
        } else {
            return "UserData{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", address='" + address + '\'' +
                    ", addressCode='" + addressCode + '\'' +
                    ", city='" + city + '\'' +
                    ", telephone='" + telephone + '\'' +
                    '}';
        }
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNameSurname() {
        return name + " " + surname;
    }

    public String getFullAddress() {
        if (office == null) {
            return city;
        } else {
            return office.getAddress() + ", " + city + " " + office.getAddressCode();
        }
    }

    public String getContactInfo() {
        if (office == null) {
            return "";
        } else {
            return office.getMobile() + ", " + office.getPhone();
        }
    }


}
