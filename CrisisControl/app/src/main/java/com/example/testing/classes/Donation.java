package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Donation implements Parcelable {
    @Ignore
    public static final int STATUS_PENDING=0,STATUS_COMPLETED=1;



    @PrimaryKey(autoGenerate = true)
    private int donationId;
    private int userId;
    private int projectID;
    @TypeConverters({DateConverter.class})
    private Date timeStamp;
    private double amount;
    private int status;

    public Donation(int userId, int projectID, Date timeStamp, double amount, int status) {
        this.userId = userId;
        this.projectID = projectID;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.status = status;
    }
    @Ignore
    public Donation(Donation d){
        this.donationId = d.donationId;
        this.userId = d.userId;
        this.projectID = d.projectID;
        this.timeStamp = d.timeStamp;
        this.amount = d.amount;
        this.status = d.status;
    }
    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getDonationId() {
        return donationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Ignore
    protected Donation(Parcel in) {
        donationId = in.readInt();
        userId = in.readInt();
        projectID = in.readInt();
        amount = in.readDouble();
        status = in.readInt();
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(donationId);
        dest.writeInt(userId);
        dest.writeInt(projectID);
        dest.writeDouble(amount);
        dest.writeInt(status);
    }
    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    public static final Creator<Donation> CREATOR = new Creator<Donation>() {
        @Override
        public Donation createFromParcel(Parcel in) {
            return new Donation(in);
        }

        @Override
        public Donation[] newArray(int size) {
            return new Donation[size];
        }
    };
}
