package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity(tableName = "Camp",indices = {@Index(value={"NGOID","latitude","longitude"},unique = true)})
public class Camp implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int campID;
    private int NGOID;
    @Embedded
    @NonNull
    private Coordinates location;

    public Camp(int NGOID,Coordinates location) {
        this.NGOID = NGOID;
        this.location = location;
    }

    public int getCampID() {
        return campID;
    }

    public void setCampID(int campID) {
        this.campID = campID;
    }

    public int getNGOID() {
        return NGOID;
    }

    public void setNGOID(int NGOID) {
        this.NGOID = NGOID;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    protected Camp(Parcel in) {
        campID = in.readInt();
        NGOID = in.readInt();
        location = in.readParcelable(Coordinates.class.getClassLoader());
    }

    public static final Creator<Camp> CREATOR = new Creator<Camp>() {
        @Override
        public Camp createFromParcel(Parcel in) {
            return new Camp(in);
        }

        @Override
        public Camp[] newArray(int size) {
            return new Camp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(campID);
        parcel.writeInt(NGOID);
        parcel.writeParcelable(location, i);
    }
    


}
