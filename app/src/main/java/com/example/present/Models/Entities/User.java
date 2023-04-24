package com.example.present.Models.Entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String passwordConfirm;
    private boolean isDoctor;

    public User(){

    }

    public User(int id, String email, String password, boolean isDoctor) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String email, boolean isDoctor) {
        this.id = id;
        this.email = email;
        this.isDoctor = isDoctor;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, boolean isDoctor) {
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }

    public User(String email, String password, String passwordConfirm, boolean isDoctor) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.isDoctor = isDoctor;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", isDoctor=" + isDoctor +
                '}';
    }

    public void fixStrings() {
        if (this.getEmail() != null) {
            this.setEmail(this.email.toLowerCase());
            this.setEmail(this.getEmail().trim());
        }
        if (this.getPassword() != null) {
            this.setPassword(this.getPassword().replaceAll("\\s", ""));
        }
    }

    public boolean isPasswordConfirmInvalid() {
        if (this.password.equals(this.passwordConfirm)) {
            return false;
        }
        return true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id){this.id=id;}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }
}

