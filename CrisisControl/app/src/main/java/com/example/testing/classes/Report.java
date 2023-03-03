package com.example.testing.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int reportID;
    private int NGOID;
    private int userID;
    private String description;
    private boolean reportManaged;

    public Report(int NGOID, int userID, String description, boolean reportManaged) {
        this.NGOID = NGOID;
        this.userID = userID;
        this.description = description;
        this.reportManaged = reportManaged;
    }

    @Ignore
    public Report(Report r) {
        this.reportID = r.reportID;
        this.NGOID = r.NGOID;
        this.userID = r.userID;
        this.description = r.description;
        this.reportManaged = r.reportManaged;

    }

    public int getReportID() {
        return reportID;
    }


    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getNGOID() {
        return NGOID;
    }

    public void setNGOID(int NGOID) {
        this.NGOID = NGOID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReportManaged() {
        return reportManaged;
    }

    public void setReportManaged(boolean reportManaged) {
        this.reportManaged = reportManaged;
    }
}
