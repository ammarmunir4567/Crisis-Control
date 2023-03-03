package com.example.testing.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MedicalRequest {
    @PrimaryKey(autoGenerate = true)
    private int medRequestID;
    private String city;
    private String address;
    private String description;
    private int injured;
    private boolean accepted;
    private int acceptedNGOID;
    private int requestedUserID;

    public MedicalRequest(String city, String address, String description, int injured, boolean accepted, int acceptedNGOID, int requestedUserID) {
        this.city = city;
        this.address = address;
        this.description = description;
        this.injured = injured;
        this.accepted = accepted;
        this.acceptedNGOID = acceptedNGOID;
        this.requestedUserID=requestedUserID;
    }
    @Ignore
    public MedicalRequest(MedicalRequest m) {
        this.medRequestID=m.medRequestID;
        this.city = m.city;
        this.address = m.address;
        this.description = m.description;
        this.injured = m.injured;
        this.accepted = m.accepted;
        this.acceptedNGOID = m.acceptedNGOID;
        this.requestedUserID=m.requestedUserID;
    }



    public int getMedRequestID() {
        return medRequestID;
    }

    public void setMedRequestID(int medRequestID) {
        this.medRequestID = medRequestID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInjured() {
        return injured;
    }

    public void setInjured(int injured) {
        this.injured = injured;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getAcceptedNGOID() {
        return acceptedNGOID;
    }

    public void setAcceptedNGOID(int acceptedNGOID) {
        this.acceptedNGOID = acceptedNGOID;
    }

    public int getRequestedUserID() {
        return requestedUserID;
    }

    public void setRequestedUserID(int requestedUserID) {
        this.requestedUserID = requestedUserID;
    }
}
