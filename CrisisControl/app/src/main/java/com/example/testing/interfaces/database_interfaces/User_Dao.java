package com.example.testing.interfaces.database_interfaces;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Account;
import com.example.testing.classes.User;

import java.util.List;

@Dao
public interface User_Dao {

    @Insert
    void insertUserAccount(Account account, User user);


    @Delete
    void deleteUser(User user,Account account);

    @Update
    void updateUser(User user,Account account);

    @Query("Select * from User")
    List<User> getAllUsers();

    @Query("Select * from User where User.accID=:ID")
    User getUserByID(int ID);

    @Query("Select count(*) from User")
    int getCountOfUsers();

    @Query("Select count(*) from User where User.userType=:type")
    int getCountOfTypeAccount(int type);

    @Query("Select Pass from User where User.userName=:username")
    List<String> getUserPass(String username);

}
