package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.MedicalRequest;

public class MedicalRequestSubmissionFormActivity extends AppCompatActivity {
    private TextView city,addr,desc,injured;
    private Button submit;
    private CCDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_request_submission_form);
        initViews();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(city.getText().toString().equals("") || addr.getText().toString().equals("") || desc.getText().toString().equals("") || injured.getText().toString().equals("")) {
                    Toast.makeText(MedicalRequestSubmissionFormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(injured.getText().toString())==0){
                    Toast.makeText(MedicalRequestSubmissionFormActivity.this, "Request medical help if injured", Toast.LENGTH_SHORT).show();
                }
                else{
                    MedicalRequest m = new MedicalRequest(city.getText().toString(),addr.getText().toString(),desc.getText().toString(),Integer.parseInt(injured.getText().toString()),false,0, CurrentAccount.getCurrentAccID());
                    db.medicalRequestDao().addMedicalRequest(m);
                    Toast.makeText(MedicalRequestSubmissionFormActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }


    private void initViews(){
        city=findViewById(R.id.MedReqCity);
        addr=findViewById(R.id.MedReqAddress);
        desc=findViewById(R.id.MedReqDescription);
        injured=findViewById(R.id.MedReqInjured);
        submit=findViewById(R.id.MedReqSubmit);
        db=CCDatabase.getInstance(this);


    }
}