package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Donation;
import com.example.testing.classes.DonationAllocation;
import com.example.testing.classes.Project;

import java.util.ArrayList;
import java.util.List;

public class DonationHistoryTableActivity extends AppCompatActivity {
    private ArrayList<Donation> donationList;
    private ArrayList<DonationAllocation> donationAllocList;
    private CCDatabase db;
    private TableLayout donationTable,donationAllocationTable;
    int NGOID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history_table);
        Intent intent = getIntent();
        db=CCDatabase.getInstance(this);
        NGOID=intent.getIntExtra("NGOID",-1);
        donationList = new ArrayList<>();
        donationAllocList = new ArrayList<>();
        if(NGOID!=-1) {
            ArrayList<Project> NGOProjects = (ArrayList<Project>) db.projectDao().getNGOProjectsByNGOID(NGOID);
            for (int i = 0; i < NGOProjects.size(); i++) {
                donationList.addAll((ArrayList<Donation>) db.donationDao().getDonationsByProjID(NGOProjects.get(i).getProjectID()));
               donationAllocList.addAll((ArrayList<DonationAllocation>) db.donationAllocationDao().getDonationAllocByProjID(NGOProjects.get(i).getProjectID()));

            }
        }else{
            donationList= (ArrayList<Donation>) db.donationDao().getDonationsByUserID(CurrentAccount.getCurrentAccID());

        }
        if(NGOID!=-1){
            initTableDonations();
           initTableDonationAllocation();
        }
        else {
            initTableDonations();
        }


    }









    public void initTableDonations(){
        TextView title = findViewById(R.id.DonationTableTitle);
        title.setVisibility(View.VISIBLE);
        donationTable =findViewById(R.id.NGODonationHistoryTable);
        TableRow tbrow0 = new TableRow(this);
//        TableRow.LayoutParams lp =new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//        tbrow0.setLayoutParams(lp);
        TextView tv0 = new TextView(this);
        tv0.setText(" NGO Name ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Project Name ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Donor Name ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Date ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Amount ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" Status ");
        tv5.setTextColor(Color.WHITE);
        tbrow0.addView(tv5);
        donationTable.addView(tbrow0);
        for (int i = 0; i < donationList.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("\t"+db.NGODao().getNGONameByProjID(donationList.get(i).getProjectID())+"\t");
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("\t"+db.projectDao().getProjectByID(donationList.get(i).getProjectID()).getTitle()+"\t");
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("\t"+db.userDao().getUserByID(donationList.get(i).getUserId()).getName()+"\t");
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("\t"+donationList.get(i).getTimeStamp().toString()+"\t");
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(String.valueOf("\t"+donationList.get(i).getAmount())+"\t");
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            if(donationList.get(i).getStatus()==Donation.STATUS_COMPLETED) {
                t6v.setText("Completed");
            }
            else{
                t6v.setText("Pending");
            }
            t6v.setTextColor(Color.WHITE);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);
            donationTable.addView(tbrow);
        }
    }


    public void initTableDonationAllocation(){
        TextView title = findViewById(R.id.DonationAllocTableTitle);
        title.setVisibility(View.VISIBLE);
        donationAllocationTable =findViewById(R.id.NGODonationAllocHistoryTable);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" NGO Name ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Project Name ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Date ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Amount ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Description ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);
        donationAllocationTable.addView(tbrow0);
        for (int i = 0; i < donationAllocList.size(); i++){
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("\t"+db.NGODao().getNGONameByProjID(donationAllocList.get(i).getProjectID())+"\t");
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("\t"+db.projectDao().getProjectByID(donationAllocList.get(i).getProjectID()).getTitle()+"\t");
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("\t"+donationAllocList.get(i).getTimeStamp().toString()+"\t");
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(String.valueOf("\t"+donationAllocList.get(i).getAmountSpent())+"\t");
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(String.valueOf("\t"+donationAllocList.get(i).getDescription())+"\t");
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            donationAllocationTable.addView(tbrow);
        }

    }
}