package com.example.testing.interfaces.database_interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Account;
import com.example.testing.classes.Admin;

import java.util.List;

@Dao
public interface Admin_Dao {
    @Insert
    void insertAdmin(Account account, Admin admin);

    @Delete
    void deleteAdmin(Admin admin);

    @Update
    void updateAdmin(Admin admin);

    @Query("Select * from Admin")
    List<Admin> getAllAdminAccounts();

    @Query("Select * from Admin where Admin.accID=:AdminID")
    Admin getAdminByID(int AdminID);

    @Query("Select accID from Admin where CNIC=:newCNIC")
    int getAdminIDfromCNIC(String newCNIC);



}
