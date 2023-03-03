package com.example.testing.interfaces.database_interfaces;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Account;
import com.example.testing.classes.NGO;

import java.util.List;

@Dao
public interface NGO_Dao {

    @Insert
    void insertNGOAccount(Account account, NGO ngo);

    @Delete
    void deleteNGO(NGO ngo);

    @Update
    void updateNGO(NGO ngo);

    @Query("Select * from NGO where Ngo.statusActive=1")
    List<NGO> getAllNGOs();

    @Query("Select * from NGO where Ngo.statusActive=0")
    List<NGO> getBlockedNGOs();

    @Query("Select * from NGO")
    LiveData<List<NGO>> getActiveAndBlockedNGOS();

    @Query("Select * from NGO where NGO.accID=:ID and Ngo.statusActive=1")
    NGO getNGOByID(int ID);

    @Query("Select * from NGO where NGO.accID=:ID")
    NGO getNGOByIDLogin(int ID);

    @Query("Select * from NGO where NGO.name=:name and Ngo.statusActive=1")
    NGO getNGOByName(String name);

    @Query("Select NGO.Name from NGO join Project on NGO.accID=Project.ngoID where Project.projectID=:projID and Ngo.statusActive=1")
    String getNGONameByProjID(int projID);

    @Query("Select * from NGO join Project on NGO.accID=Project.ngoID where Project.projectID=:projID and Ngo.statusActive=1")
    NGO getNGOByProjID(int projID);

    @Query("Select count(*) from NGO where Ngo.statusActive=1")
    int getCountNGOAccounts();

    @Query("Select * from NGO where (NGO.name Like '%' || :search || '%' or NGO.address Like '%' || :search || '%') and Ngo.statusActive=1")
    LiveData<List<NGO>> SearchNGO(String search);

    @Query("Select * from NGO where Ngo.statusActive=1 order by NGO.ratings desc")
    List<NGO> NGOSortByRatings();

    @Query("Select * from NGO where Ngo.statusActive=1 order by NGO.ratings asc")
    List<NGO> NGOSortByRatings_asc();


}
