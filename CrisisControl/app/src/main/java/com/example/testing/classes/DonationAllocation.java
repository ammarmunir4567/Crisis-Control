package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class DonationAllocation implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int allocationID;
    private double amountSpent;
    @TypeConverters({DateConverter.class})
    private Date timeStamp;
    private int projectID;
    private String Description;

    public DonationAllocation(double amountSpent, Date timeStamp, int projectID, String Description) {
        this.amountSpent = amountSpent;
        this.timeStamp = timeStamp;
        this.projectID = projectID;
        this.Description = Description;
    }

    public void setAllocationID(int allocationID) {
        this.allocationID = allocationID;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAllocationID() {
        return allocationID;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getDescription() {
        return Description;
    }

    @Ignore
    protected DonationAllocation(Parcel in) {
        amountSpent = in.readDouble();
        timeStamp=new Date(in.readLong());
        projectID = in.readInt();
        Description = in.readString();
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amountSpent);
        dest.writeLong(timeStamp == null ? 0 : timeStamp.getTime());
        dest.writeInt(projectID);
        dest.writeString(Description);
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Creator<DonationAllocation> CREATOR = new Creator<DonationAllocation>() {
        @Override
        public DonationAllocation createFromParcel(Parcel in) {
            return new DonationAllocation(in);
        }

        @Override
        public DonationAllocation[] newArray(int size) {
            return new DonationAllocation[size];
        }
    };


}
