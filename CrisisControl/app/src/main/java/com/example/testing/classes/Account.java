package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Account implements Parcelable {

    @PrimaryKey
    protected int accID;
    private String userName;
    private String Pass;


    public Account(int accID, String userName, String Pass) {
        this.accID=accID;
        this.userName = userName;
        this.Pass = Pass;
    }

    @Ignore
    protected Account(Parcel in) {
        accID = in.readInt();
        userName = in.readString();
        Pass = in.readString();
    }
    @Ignore
    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
    @Ignore
    public Account() {

    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return Pass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(accID);
        parcel.writeString(userName);
        parcel.writeString(Pass);
    }
}
