package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.adapters.AppliedVolunteerRVAdapter;
import com.example.testing.classes.AppliedVolunteer;
import com.example.testing.classes.CCDatabase;

import java.util.ArrayList;

public class AppliedVolunteerListActivity extends AppCompatActivity {
    private RecyclerView appliedVolunteerRV;
    private AppliedVolunteerRVAdapter adapter;
    private ArrayList<AppliedVolunteer> volunteers;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_volunteer_list);
        appliedVolunteerRV=findViewById(R.id.AppliedVolunteerRV);
        adapter = new AppliedVolunteerRVAdapter(this);
        Intent intent = getIntent();
        int projID=intent.getIntExtra("ProjID",0);
        volunteers = new ArrayList<>();
        db=CCDatabase.getInstance(this);
        if(projID!=0) {
            volunteers = (ArrayList<AppliedVolunteer>) db.volunteersDao().getProjectVolunteers(projID);
            adapter.setVolunteers(volunteers);
            appliedVolunteerRV.setAdapter(adapter);
            appliedVolunteerRV.setLayoutManager(new LinearLayoutManager(this));


        }else{
            Toast.makeText(this, "No volunteers applied yet", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}