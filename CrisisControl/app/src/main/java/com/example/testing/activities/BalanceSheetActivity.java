package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.DateConverter;
import com.example.testing.classes.Donation;
import com.example.testing.classes.DonationAllocation;
import com.example.testing.classes.NGO;
import com.example.testing.classes.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BalanceSheetActivity extends AppCompatActivity {
    private TextView totalUsers,totalDonors,totalVolunteers,totalAffectees,totalNGOs,totalNumberOfDonations,totalDonationAmount,totalAppliedVolunteers,totalReports,totalPending;
    private TableLayout NGOBalanceSheet;
    private ArrayList<NGO> Ngos;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        initViews();
        initStats();
        Ngos = new ArrayList<>();
        Ngos = (ArrayList<NGO>) db.NGODao().getAllNGOs();
        initBalanceSheetTable();

    }

    private void initBalanceSheetTable() {
        TextView title = findViewById(R.id.DonationTableTitle);
        title.setVisibility(View.VISIBLE);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" NGO Name ");
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Total Donation ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Total Donation Allocated ");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Last Allocation Date ");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);
        NGOBalanceSheet.addView(tbrow0);
        for (int i = 0; i < Ngos.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setTextColor(Color.BLUE);
            t1v.setText(Ngos.get(i).getName());
            int finalI = i;
            t1v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BalanceSheetActivity.this,DonationHistoryTableActivity.class);
                    intent.putExtra("NGOID",Ngos.get(finalI).getAccID());
                    startActivity(intent);
                }
            });
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(db.donationDao().getTotalDonationOfNGO(Ngos.get(i).getAccID())));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(String.valueOf(db.donationAllocationDao().getTotalDonationAllocationOfNGO(Ngos.get(i).getAccID())));
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            Date d = new Date();
            d= db.donationAllocationDao().getLatestNGODonationAllocationEntry(Ngos.get(i).getAccID());
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
            try {
                if(d!=null) {
                    t4v.setText(formatter1.format(formatter.parse(d.toString())));
                }
                else{
                    t4v.setText("No Donations Allocated Yet");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            NGOBalanceSheet.addView(tbrow);
        }

    }

    private void initStats() {
        totalUsers.setText(String.valueOf(db.userDao().getCountOfUsers()));
        totalDonors.setText(String.valueOf(db.userDao().getCountOfTypeAccount(User.USER_TYPE_DONOR)));
        totalVolunteers.setText(String.valueOf(db.userDao().getCountOfTypeAccount(User.USER_TYPE_VOLUNTEER)));
        totalAffectees.setText(String.valueOf(db.userDao().getCountOfTypeAccount(User.USER_TYPE_AFFECTEE)));
        totalNGOs.setText(String.valueOf(db.NGODao().getCountNGOAccounts()));
        totalNumberOfDonations.setText(String.valueOf(db.donationDao().getCountOfDonations()));
        totalDonationAmount.setText(String.valueOf(db.donationDao().getTotalDonationAmount()));
        totalAppliedVolunteers.setText(String.valueOf(db.volunteersDao().getCountOfAppliedVolunteers()));
        totalReports.setText(String.valueOf(db.reportDao().getCountOfReports()));
        totalPending.setText(String.valueOf(db.donationDao().getCountPendingDonations()));
    }

    private void initViews() {
        totalUsers=findViewById(R.id.totalUserAccounts);
        totalDonors=findViewById(R.id.totalDonorAccounts);
        totalVolunteers=findViewById(R.id.totalVolunteerAccounts);
        totalNGOs=findViewById(R.id.totalNGOAccounts);
        totalNumberOfDonations=findViewById(R.id.totalNumberOfDonations);
        totalDonationAmount=findViewById(R.id.totalDonationAmount);
        totalAppliedVolunteers=findViewById(R.id.totalAppliedVolunteers);
        totalReports=findViewById(R.id.totalNumberOfReports);
        totalAffectees=findViewById(R.id.totalAffecteeAccounts);
        totalPending=findViewById(R.id.totalNumberOfDonationsPending);
        NGOBalanceSheet=findViewById(R.id.NGOBalanceSheet);
        db=CCDatabase.getInstance(this);
    }
}