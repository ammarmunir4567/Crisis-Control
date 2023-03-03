package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.AppliedVolunteer;

import java.util.List;

@Dao
public interface Volunteers_Dao {
    @Insert
    void insertVolunteer(AppliedVolunteer vol);
    @Delete
    void deleteVolunteer(AppliedVolunteer vol);
    @Update
    void updateVolunteer(AppliedVolunteer vol);

    @Query("Select * from AppliedVolunteer")
    List<AppliedVolunteer> getAllVolunteers();

    @Query("Select * from AppliedVolunteer where userID=:uid")
    List<AppliedVolunteer> getUserVolunteers(int uid);

    @Query("Select * from AppliedVolunteer where projectID=:pid")
    List<AppliedVolunteer> getProjectVolunteers(int pid);

    @Query("Select count(*) from AppliedVolunteer where projectID=:pid")
    int getCountProjectVolunteers(int pid);

    @Query("Select count(*) from AppliedVolunteer")
    int getCountOfAppliedVolunteers();

    @Query("Select * from AppliedVolunteer where userID=:uID and AppliedVolunteer.projectID=:pID")
    AppliedVolunteer checkAlreadyApplied(int uID, int pID);


}
