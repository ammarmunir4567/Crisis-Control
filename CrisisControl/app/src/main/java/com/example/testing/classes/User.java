package com.example.testing.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class User extends Account implements Parcelable {
public static final int USER_TYPE_DONOR = 10, USER_TYPE_VOLUNTEER=11, USER_TYPE_AFFECTEE=12;


    private  String name;
    @TypeConverters({DateConverter.class})
    private Date DOB;
    private String address;
    private int userType;


    public User(int accID, String userName, String Pass,String name, Date DOB, String address,int userType) {
        super(accID,userName,Pass);

        this.name = name;
        this.DOB = DOB;
      //  this.CNIC = CNIC;
        this.address = address;
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Ignore
    public User (User u){
        this.accID=u.accID;
        this.setUserName(u.getUserName());
        this.setPass(u.getPass());
        this.address=u.address;
        this.name=u.name;
       // this.CNIC=u.CNIC;
        this.userType=u.userType;

    }

    @Ignore
    public User(String name, Date DOB, String CNIC, String address){
        super();

        this.name = name;
        this.DOB = DOB;
       // this.CNIC = CNIC;
        this.address = address;
    }

    @Ignore
    public User(){
    }



    protected User(Parcel in) {
        super(in);

        name = in.readString();
       // CNIC = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

//    public void setCNIC(String CNIC) {
//        this.CNIC = CNIC;
//    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getName() {
        return name;
    }

    public Date getDOB() {
        return DOB;
    }

//    public String getCNIC() {
//        return CNIC;
//    }

    public String getAddress() {
        return address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(name);
       // parcel.writeString(CNIC);
        parcel.writeString(address);
    }
}
