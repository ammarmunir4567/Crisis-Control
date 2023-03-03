package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Account;

import java.util.List;
@Dao
public interface Account_Dao {
    @Insert
    void insertAccDetails(Account acc);

    @Delete
    void deleteAccDetails(Account acc);

    @Update
    void updateAccDetails(Account acc);

    @Query("Select * from Account")
    List<Account> getAllAccounts();



    @Query("Select Account.accID + 1 from Account  order by Account.accID desc limit 1")
    int getNewAccountID();

    @Query("Select * from Account where accID=:ID")
    Account getAccountByID(int ID);

    @Query("Select accID from Account where userName=:user and Pass=:pass")
    int getAccIDByLogin(String user, String pass);

    @Query("Select * from Account where userName=:username")
    Account getAccByUserName(String username);

}
