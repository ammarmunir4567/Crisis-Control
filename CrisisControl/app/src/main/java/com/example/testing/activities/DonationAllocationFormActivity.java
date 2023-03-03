package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.DonationAllocation;

import java.util.Calendar;

public class DonationAllocationFormActivity extends AppCompatActivity {
    private EditText edAmountSpent,edProjectName,edDescription;
    private DatePicker dpExpenditureDate;
    private Button submit;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_allocation_form);
        initViews();
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(dpExpenditureDate.getYear(),dpExpenditureDate.getMonth(),dpExpenditureDate.getDayOfMonth());
                if(edAmountSpent.getText().toString().equals("")){
                    Toast.makeText(DonationAllocationFormActivity.this, "Please enter amount spent", Toast.LENGTH_SHORT).show();
                }
                else if (edProjectName.getText().toString().equals("")){
                    Toast.makeText(DonationAllocationFormActivity.this, "Please Enter the Project Title", Toast.LENGTH_SHORT).show();
                }
                else if (db.projectDao().getNGOIDFromProjectTitle(edProjectName.getText().toString())==0){
                    Toast.makeText(DonationAllocationFormActivity.this, "Please Enter a Valid Project Title", Toast.LENGTH_SHORT).show();
                }
                else if (edDescription.getText().toString().equals("")){
                    Toast.makeText(DonationAllocationFormActivity.this, "Please Enter Description of Expenditure", Toast.LENGTH_SHORT).show();
                }
                else if(cal.getTimeInMillis()>Calendar.getInstance().getTimeInMillis() || cal.getTimeInMillis()<db.projectDao().getProjectFromTitleAndNGOID(edProjectName.getText().toString(), CurrentAccount.getCurrentAccID()).getStartingDate().getTime()){
                    Toast.makeText(DonationAllocationFormActivity.this, "Please Select Valid Date", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.donationAllocationDao().insertDonationAllocation(new DonationAllocation(Double.parseDouble(edAmountSpent.getText().toString()),cal.getTime(),db.projectDao().getProjectFromTitleAndNGOID(edProjectName.getText().toString(),CurrentAccount.getCurrentAccID()).getProjectID(),edDescription.getText().toString()));
                    Toast.makeText(DonationAllocationFormActivity.this, "DonationAllocation Added", Toast.LENGTH_SHORT).show();
                   finish();
                }
            }
        });

        
    }

    private void initViews() {
        edAmountSpent=findViewById(R.id.edDonationAllocFormAmountSpent);
        edProjectName=findViewById(R.id.edDonationAllocFormProjectName);
        edDescription=findViewById(R.id.edDonationAllocFormDesc);
        dpExpenditureDate=findViewById(R.id.datePickerDonationAlloc);
        submit=findViewById(R.id.btnSubmitFormDonationALloc);
        db=CCDatabase.getInstance(this);
        
    }
}