package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testing.classes.Ratings;

@Dao
public interface Ratings_Dao {
    @Insert
    void insertRating(Ratings ratings);

    @Query("Select * from Ratings where userID=:uID and NGOID=:nID")
    Ratings checkAlreadyRated(int uID, int nID);
}
