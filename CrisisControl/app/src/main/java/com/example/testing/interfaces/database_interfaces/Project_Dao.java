package com.example.testing.interfaces.database_interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Project;

import java.util.List;

@Dao
public interface Project_Dao {
    @Insert
    void insertProject(Project proj);

    @Delete
    void deleteProject(Project proj);

    @Update
    void updateProject(Project proj);

    @Query("Select * from Project")
    List<Project> getAllProjects();

    @Query("Select * from Project where Project.projectID=:ID")
    Project getProjectByID(int ID);

    @Query("Select * from Project where Project.ngoID=:ID")
    List<Project> getNGOProjectsByNGOID(int ID);

    @Query("Select NGOID from Project where Project.title=:title")
    int getNGOIDFromProjectTitle(String title);

    @Query("Select * from Project where Project.title=:title and Project.ngoID=:ngoID")
    Project getProjectFromTitleAndNGOID(String title,int ngoID);

    @Query("Select Project.* from Project join NGO on Project.NGOID=Ngo.accID where(Project.title Like '%' || :search || '%' or NGO.name Like '%' || :search || '%') and NGO.statusActive=1")
    LiveData<List<Project>> searchProject(String search);

    @Query("Select * from Project order by VolunteersNeeded desc")
    List<Project> getProjectsSortedByVolunteers_Desc();

    @Query("Select * from Project order by VolunteersNeeded asc")
    List<Project> getProjectsSortedByVolunteers_Asc();

    @Query("Select * from Project order by donationAsked desc")
    List<Project> getProjectsSortedByDonations_Desc();

    @Query("Select * from Project order by donationAsked asc")
    List<Project> getProjectsSortedByDonations_Asc();

}
