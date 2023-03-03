package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Camp;
import com.example.testing.classes.Coordinates;

import java.util.List;

@Dao
public interface Camp_Dao {

    @Insert
    void insertCamp(Camp camp);

    @Delete
    void deleteCamp(Camp camp);

    @Update
    void updateCamp(Camp camp);

    @Query("Select * from Camp")
    List<Camp> getAllCamps();

    @Query("Select latitude,longitude,locationName from Camp join NGO on Camp.NGOID=NGO.accID where NGO.statusActive=1")
    List<Coordinates> getAllCoordinates();


    @Query("Select latitude,longitude,locationName from Camp where Camp.NGOID=:ngoID")
    List<Coordinates> getCoordinatesByNGOID(int ngoID);
}
