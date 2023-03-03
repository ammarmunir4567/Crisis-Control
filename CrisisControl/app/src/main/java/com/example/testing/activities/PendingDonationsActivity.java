package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testing.R;
import com.example.testing.adapters.DonationPendingRVAdapter;
import com.example.testing.adapters.LiveFeedRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.Donation;

import java.util.ArrayList;

public class PendingDonationsActivity extends AppCompatActivity {
    private RecyclerView pendingDonationRV;
    private DonationPendingRVAdapter adapter;
    private CCDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_donations);
        db=CCDatabase.getInstance(this);
        pendingDonationRV = findViewById(R.id.donationPendingRV);
        adapter = new DonationPendingRVAdapter(this);
        adapter.setDonations((ArrayList<Donation>) db.donationDao().getPendingDonations());
        pendingDonationRV.setAdapter(adapter);
        pendingDonationRV.setLayoutManager(new LinearLayoutManager(this));



    }
}