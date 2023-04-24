package com.example.present.Models.Entities;

import java.io.Serializable;

public class Meeting implements Serializable {
    private int id;
    private int patientId;
    private int doctorId;
    private int state;//0 pending,1approved by doctor,2 canceled by patient, 3 canceled by doctor
    private String dateTime;



    private String patientDescription;
    private String doctorDescription;


    public Meeting(){
    }

    public Meeting(int id, int state, String patientDescription, String doctorDescription) {
        this.id = id;
        this.state = state;
        this.patientDescription = patientDescription;
        this.doctorDescription = doctorDescription;
    }
    public Meeting(int id, int patientId, int doctorId, int state, String dateTime, String patientDescription, String doctorDescription) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.state = state;
        this.dateTime = dateTime;
        this.patientDescription = patientDescription;
        this.doctorDescription = doctorDescription;
    }

    public Meeting(int patientId, int doctorId, int state, String dateTime, String patientDescription, String doctorDescription) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.state = state;
        this.dateTime = dateTime;
        this.patientDescription = patientDescription;
        this.doctorDescription = doctorDescription;
    }
    public Meeting(int doctorId, int state, String dateTime, String patientDescription, String doctorDescription) {
        this.doctorId = doctorId;
        this.state = state;
        this.dateTime = dateTime;
        this.patientDescription = patientDescription;
        this.doctorDescription = doctorDescription;
    }


    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", state=" + state +
                ", dateTime='" + dateTime + '\'' +
                ", patientDescription='" + patientDescription + '\'' +
                ", doctorDescription='" + doctorDescription + '\'' +
                '}';
    }

    public void fixStrings() {
        if (this.getDateTime() != null) {
            this.setDateTime(this.getDateTime().trim());
        }
        if (this.getDoctorDescription() != null) {
            this.setDoctorDescription(this.getDoctorDescription().trim());
        }
        if (this.getPatientDescription() != null) {
            this.setPatientDescription(this.getPatientDescription().trim());
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDoctorDescription() {
        return doctorDescription;
    }

    public void setDoctorDescription(String doctorDescription) {
        this.doctorDescription = doctorDescription;
    }
    public String getPatientDescription() {
        return patientDescription;
    }

    public void setPatientDescription(String patientDescription) {
        this.patientDescription = patientDescription;
    }
    public String getStateToString(){
        if(state==0){
            return "Pending";
        }
        if(state==1){
            return "Approved by doctor";
        }
        if(state==2){
            return "Canceled by patient";
        }
        if(state==3){
            return "Canceled by doctor";
        }
        return "Unknown State";
    }
}
