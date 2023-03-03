package com.example.testing.classes;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"userID","projectID"},foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "accID",childColumns = "userID"),@ForeignKey(entity = Project.class, parentColumns = "projectID",childColumns = "projectID") } )
public class AppliedVolunteer {
 private int userID;
 private int projectID;
 private String CNIC;
 private String phoneNumber;
 private int age;

    public AppliedVolunteer(int userID, int projectID, String CNIC, String phoneNumber, int age) {
        this.userID = userID;
        this.projectID = projectID;
        this.CNIC = CNIC;
        this.phoneNumber = phoneNumber;

        this.age = age;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
