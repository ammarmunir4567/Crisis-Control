package com.example.testing.classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ratings {
    @PrimaryKey(autoGenerate = true)
    private int ratingID;
    private int userID;
    private int NGOID;
    private double rating;

    public Ratings(int userID, int NGOID,double rating) {
        this.userID = userID;
        this.NGOID = NGOID;
        this.rating=rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNGOID() {
        return NGOID;
    }

    public void setNGOID(int NGOID) {
        this.NGOID = NGOID;
    }
}
