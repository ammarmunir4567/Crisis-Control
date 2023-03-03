package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.MedicalRequest;

import java.util.List;

@Dao
public interface MedicalRequest_Dao {
    @Insert
    void addMedicalRequest(MedicalRequest medicalRequest);
    @Delete
    void deleteMedicalRequest(MedicalRequest medicalRequest);
    @Update
    void updateMedicalRequest(MedicalRequest medicalRequest);

    @Query("Select * from MedicalRequest where accepted=0")
    List<MedicalRequest> getMedicalRequests();

    @Query("Select * from MedicalRequest where accepted=1 and acceptedNGOID=:nID")
    List<MedicalRequest> getAcceptedMedicalRequests(int nID);
}
