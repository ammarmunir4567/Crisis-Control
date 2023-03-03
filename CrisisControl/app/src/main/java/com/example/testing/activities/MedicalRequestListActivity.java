package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.testing.R;
import com.example.testing.adapters.MedicalRequestRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.MedicalRequest;

import java.util.ArrayList;

public class MedicalRequestListActivity extends AppCompatActivity {
    private  RecyclerView mrRV;
    private ArrayList<MedicalRequest> medicalRequests;
    private MedicalRequestRVAdapter adapter;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_request_list);

        Intent intent = getIntent();
        int ngoID=intent.getIntExtra("NGOID",-1);
        mrRV=findViewById(R.id.MedicalRequestRV);
        medicalRequests=new ArrayList<>();
        db=CCDatabase.getInstance(this);
        adapter = new MedicalRequestRVAdapter(this);
        if(ngoID==-1){
            medicalRequests=(ArrayList<MedicalRequest>) db.medicalRequestDao().getMedicalRequests();
            adapter.setAllMedicalRequests(medicalRequests);
        }else{
            medicalRequests=(ArrayList<MedicalRequest>) db.medicalRequestDao().getAcceptedMedicalRequests(ngoID);
            adapter.setAcceptedMedicalRequests(medicalRequests);
        }
    mrRV.setAdapter(adapter);
    mrRV.setLayoutManager(new LinearLayoutManager(this));

    }
}