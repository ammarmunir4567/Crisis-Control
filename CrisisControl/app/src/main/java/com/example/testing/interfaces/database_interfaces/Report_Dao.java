package com.example.testing.interfaces.database_interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Report;

import java.util.List;

@Dao
public interface Report_Dao {
    @Insert
    void insertReport(Report report);
    @Delete
    void deleteReport(Report report);
    @Update
    void updateReport(Report report);

    @Query("Select * from Report")
    List<Report> getAllReports();

    @Query("Select * from Report where Report.reportManaged=0")
    List<Report> getUnmanagedReports();

    @Query("Select * from Report where Report.reportManaged=1")
    List<Report> getManagedReports();

    @Query("Select * from Report where reportID=:reportID")
    Report getReportByID(int reportID);

    @Query("Select count(*) from Report")
    int getCountOfReports();



}
