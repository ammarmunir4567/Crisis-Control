package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
@Entity
public class NGO extends Account implements Parcelable {

    private String name;
    private String desc;
    private String address;
    private boolean isRegistered;
    private boolean statusActive;
    private double ratings;
    private int totalRatings;
    private String bankAccountNum;
    private String bankName;


    public NGO(int accID,String userName, String Pass, String name, String desc, String address, boolean isRegistered, boolean statusActive, double ratings, int totalRatings,String bankAccountNum,String bankName) {
        super(accID,userName, Pass);
        this.name = name;
        this.desc = desc;
        this.address = address;
        this.isRegistered = isRegistered;
        this.statusActive = statusActive;
        this.ratings = ratings;
        this.totalRatings = totalRatings;
        this.bankAccountNum = bankAccountNum;
        this.bankName = bankName;
    }

    @Ignore
    protected NGO(Parcel in) {
        super(in);
        name = in.readString();
        desc = in.readString();
        address = in.readString();
        isRegistered = in.readByte() != 0;
        statusActive = in.readByte() != 0;
        ratings = in.readDouble();
        totalRatings = in.readInt();
        bankAccountNum = in.readString();
        bankName = in.readString();
    }

    @Ignore
    public static final Creator<NGO> CREATOR = new Creator<NGO>() {
        @Override
        public NGO createFromParcel(Parcel in) {
            return new NGO(in);
        }

        @Override
        public NGO[] newArray(int size) {
            return new NGO[size];
        }
    };

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Ignore
    public NGO(NGO n){
        this.accID=n.accID;
        this.name = n.name;
        this.desc = n.desc;
        this.address = n.address;
        this.isRegistered = n.isRegistered;
        this.statusActive = n.statusActive;
        this.ratings = n.ratings;
        this.totalRatings = n.totalRatings;
        this.bankAccountNum = n.bankAccountNum;
        this.bankName = n.bankName;
    }


    @Ignore
    public NGO() {

    }

    public String getBankAccountNum() {
        return bankAccountNum;
    }

    public void setBankAccountNum(String bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }

    public int getTotalRatings() {
        return totalRatings;
    }


    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isStatusActive() {
        return statusActive;
    }

    public void setStatusActive(boolean statusActive) {
        this.statusActive = statusActive;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    @Override
    @Ignore
    public String toString() {
        return "NGO{" +

                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", address='" + address + '\'' +
                ", isRegistered=" + isRegistered +
                ", statusActive=" + statusActive +
                ", ratings=" + ratings +
                '}';
    }


    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel,i);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeString(address);
        parcel.writeByte((byte) (isRegistered ? 1 : 0));
        parcel.writeByte((byte) (statusActive ? 1 : 0));
        parcel.writeDouble(ratings);
        parcel.writeInt(totalRatings);
        parcel.writeString(bankAccountNum);
        parcel.writeString(bankName);
    }
}
