package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.android.gms.maps.model.LatLng;

public class Coordinates implements Parcelable {

    private double longitude;
    private double latitude;
    private String locationName;

    public Coordinates(double latitude, double longitude, String locationName) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationName = locationName;
    }

    @Ignore
    public Coordinates() {

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LatLng getLocationLatLng(){
      return new LatLng(latitude,longitude);
    }
    @Ignore
    protected Coordinates(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
        locationName = in.readString();
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(locationName);
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }
    @Ignore
    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };
}
