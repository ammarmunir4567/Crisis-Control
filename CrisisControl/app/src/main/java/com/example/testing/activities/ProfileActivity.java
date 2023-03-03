package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.User;
import com.example.testing.classes.CurrentAccount;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private EditText profileName,profileDOB,profileAddress;
    private Button btnMyDonations,btnUpdateAcc;
    private int btnpressed;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        CurrentAccount cu = CurrentAccount.getInstance();
        User u = db.userDao().getUserByID(cu.getCurrentAccID());
        profileName.setText(u.getName());
        profileAddress.setText(u.getAddress());
        profileAddress.setFocusable(false);
        profileName.setFocusable(false);
        profileDOB.setFocusable(false);

//        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
        try {
            profileDOB.setText(formatter1.format(formatter.parse(u.getDOB().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnUpdateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnpressed==0){
                    profileAddress.setFocusableInTouchMode(true);
                    profileAddress.setFocusable(true);
                    profileName.setFocusableInTouchMode(true);
                    profileName.setFocusable(true);
                    btnUpdateAcc.setText("Submit");
                    btnpressed++;
                }
                else {
                    if(profileName.getText().toString().equals("") || profileAddress.getText().toString().equals("")){
                        Toast.makeText(ProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }else {
                        btnpressed = 0;
                        profileAddress.setFocusable(false);
                        profileName.setFocusable(false);
                        profileDOB.setFocusable(false);
                        btnUpdateAcc.setText("Update Account");

                        User u1 = new User(db.userDao().getUserByID(cu.getCurrentAccID()));
                        u1.setAddress(profileAddress.getText().toString());
                        u1.setName(profileName.getText().toString());
                        // Date d1= new Date();
                        Date d1 = null;
                        try {
                            d1 = formatter1.parse(profileDOB.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        u1.setDOB(d1);
                        db.userDao().updateUser(u1, u1);
                    }

                }

            }
        });
        btnMyDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ProfileActivity.this,DonationHistoryTableActivity.class);
               startActivity(intent);
            }
        });

    }

    private void initViews() {
        profileName=findViewById(R.id.ProfileName);
        profileDOB=findViewById(R.id.ProfileDOB);
        profileAddress=findViewById(R.id.ProfileAddress);
        btnMyDonations=findViewById(R.id.btnProfileGetDonationHistory);
        btnUpdateAcc=findViewById(R.id.btnProfileUpdateAccount);
        db=CCDatabase.getInstance(this);
        btnpressed=0;
    }
}