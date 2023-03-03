package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testing.classes.Donation;

import java.util.List;

@Dao
public interface Donation_Dao {

    @Insert
    void insertDonation(Donation donation);

    @Delete
    void deleteDonation(Donation donation);

    @Update
    void updateDonation(Donation donation);

    @Query("Select * from Donation")
    List<Donation> getAllDonations();


    @Query("Select * from Donation where Donation.projectID=:projID order by Donation.timeStamp desc")
    List<Donation> getDonationsByProjID(int projID);

    @Query("Select * from Donation where Donation.userId=:userID order by Donation.timeStamp desc")
    List<Donation> getDonationsByUserID(int userID);

    @Query("Select Sum(amount) from Donation where Donation.projectID=:projID")
    int getProjectTotalDonatedAmount(int projID);

    @Query("Select count(*) from Donation")
    int getCountOfDonations();

    @Query("Select sum(amount) from Donation")
    int getTotalDonationAmount();

    @Query("Select sum(amount) from Donation join Project on Donation.projectID=Project.projectID join NGO on Project.ngoID = NGO.accID where NGO.accID=:NGOID")
    int getTotalDonationOfNGO(int NGOID);

    @Query("Select Donation.donationId,Donation.userId,Donation.projectID,Donation.timeStamp,Donation.amount,Donation.status from Donation join Project on Donation.projectID=Project.projectID join NGO on NGO.accID=Project.ngoID where status=0 and statusActive=1")
    List<Donation> getPendingDonations();

    @Query("Select count(*) from Donation join Project on Donation.projectID=Project.projectID join NGO on NGO.accID=Project.ngoID where status=0 and statusActive=1")
    int getCountPendingDonations();







}
