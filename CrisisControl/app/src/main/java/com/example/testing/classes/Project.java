package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Project implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int projectID;
    private String title;
    private String description;
    private int ngoID;
    private double donationAsked;
    private int volunteersNeeded;
    @TypeConverters({DateConverter.class})
    private Date startingDate;

    public Project(String title, String description, int ngoID, double donationAsked, int volunteersNeeded, Date startingDate) {
        this.title = title;
        this.description = description;
        this.ngoID = ngoID;
        this.donationAsked = donationAsked;
        this.volunteersNeeded = volunteersNeeded;
        this.startingDate = startingDate;
    }
    @Ignore
    public Project(Project p){
        this.projectID=p.projectID;
        this.title = p.title;
        this.description = p.description;
        this.ngoID = p.ngoID;
        this.donationAsked = p.donationAsked;
        this.volunteersNeeded = p.volunteersNeeded;
        this.startingDate = p.startingDate;
    }

    @Ignore
    protected Project(Parcel in) {
        projectID = in.readInt();
        title = in.readString();
        description = in.readString();
        ngoID = in.readInt();
        donationAsked = in.readDouble();
        volunteersNeeded = in.readInt();
        startingDate = new Date(in.readLong());
    }

        @Ignore
    public Project() {

    }

    @Override
    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(projectID);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(ngoID);
        dest.writeDouble(donationAsked);
        dest.writeInt(volunteersNeeded);
        dest.writeLong(startingDate == null ? 0 : startingDate.getTime());
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }
    @Ignore
    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNgoID() {
        return ngoID;
    }

    public void setNgoID(int ngo) {
        this.ngoID = ngo;
    }

    public double getDonationAsked() {
        return donationAsked;
    }

    public void setDonationAsked(double donationAsked) {
        this.donationAsked = donationAsked;
    }


    public int getVolunteersNeeded() {
        return volunteersNeeded;
    }

    public void setVolunteersNeeded(int volunteersNeeded) {
        this.volunteersNeeded = volunteersNeeded;
    }


    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @Override
    @Ignore
    public String toString() {
        return "Project{" +
                "projectID='" + projectID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ngoID=" + ngoID +
                ", donationAsked=" + donationAsked +
                ", volunteersNeeded=" + volunteersNeeded +
                ", startingDate=" + startingDate +
                '}';
    }
}
