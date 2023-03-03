package com.example.testing.interfaces.database_interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.testing.classes.DateConverter;
import com.example.testing.classes.DonationAllocation;

import java.util.Date;
import java.util.List;

@Dao
public interface DonationAllocation_Dao {
    @Insert
    void insertDonationAllocation(DonationAllocation donationAlloc);
    @Delete
    void deleteDonationAllocation(DonationAllocation donationAlloc);
    @Update
    void updateDonationAllocation(DonationAllocation donationAlloc);

    @Query("Select * from DonationAllocation")
    List<DonationAllocation> getAllDonationAllocations();

    @Query("Select * from DonationAllocation where DonationAllocation.projectID=:projID order by DonationAllocation.timeStamp desc")
    List<DonationAllocation> getDonationAllocByProjID(int projID);

    @Query("Select sum(amountSpent) from DonationAllocation join Project on DonationAllocation.projectID = Project.projectID join NGO on Project.ngoID = NGO.accID where NGO.accID=:NGOID")
    int getTotalDonationAllocationOfNGO(int NGOID);

    @Query("Select timeStamp from DonationAllocation join Project on DonationAllocation.projectID = Project.projectID join NGO on Project.ngoID = NGO.accID where NGO.accID=:NGOID ORDER BY timestamp DESC LIMIT 1")
    @TypeConverters({DateConverter.class})
    Date getLatestNGODonationAllocationEntry(int NGOID);
}
