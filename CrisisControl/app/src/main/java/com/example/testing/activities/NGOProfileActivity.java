package com.example.testing.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.number.Precision;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.NGO;
import com.example.testing.classes.Ratings;
import com.example.testing.classes.Report;

import java.util.zip.Inflater;

public class NGOProfileActivity extends AppCompatActivity {
 private TextView txtProfileName,txtProfileDesc,txtProfileAddress,txtReportBlockText;
 private RatingBar rbProfileRating,userRatings;
 private Button btnDonationHistory,btnProjectList,btnCampLocation;
 private NGO ngo;
 private ImageButton imgReport;
 CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoprofile);
        initViews();
        Intent intent=getIntent();
        ngo=intent.getParcelableExtra("Profile");
        txtProfileName.setText(ngo.getName());
        txtProfileAddress.setText(ngo.getAddress());
        txtProfileDesc.setText(ngo.getDesc());
        rbProfileRating.setRating((float)ngo.getRatings());
        if(CurrentAccount.getAccType()==CurrentAccount.ADMIN_TYPE){
            txtReportBlockText.setText("Block");
        }
        else{
            txtReportBlockText.setText("Report");
        }

        btnDonationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ngo.isStatusActive()) {
                    Toast.makeText(NGOProfileActivity.this, "This NGO has been Blocked", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(NGOProfileActivity.this, DonationHistoryTableActivity.class);
                    intent1.putExtra("NGOID", ngo.getAccID());
                    startActivity(intent1);
                }
            }
        });
        btnCampLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ngo.isStatusActive()){
                    Toast.makeText(NGOProfileActivity.this, "This NGO has been Blocked", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1 = new Intent(NGOProfileActivity.this, NGOCampLocationMap.class);
                    intent1.putExtra("NGOID", ngo.getAccID());
                    startActivity(intent1);
                }
            }
        });
    btnProjectList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!ngo.isStatusActive()){
                Toast.makeText(NGOProfileActivity.this, "This NGO has been Blocked", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent1 = new Intent(NGOProfileActivity.this, NGOProjectListActivity.class);
                intent1.putExtra("NGOID", ngo.getAccID());
                startActivity(intent1);
            }
        }
    });
    if(db.ratingsDao().checkAlreadyRated(CurrentAccount.getCurrentAccID(),ngo.getAccID())!=null){
        userRatings.setRating((float) db.ratingsDao().checkAlreadyRated(CurrentAccount.getCurrentAccID(),ngo.getAccID()).getRating());
        userRatings.setIsIndicator(true);
    }
    else if(CurrentAccount.getAccType()==CurrentAccount.ADMIN_TYPE){
        userRatings.setIsIndicator(true);
    }
    else {
        userRatings.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NGOProfileActivity.this);
                builder.setMessage("Are you sure you want to rate this NGO : " + String.valueOf(v) + " stars? Once you rate you cannot change it.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double currentRating = ngo.getRatings();
                        int totalRatings = ngo.getTotalRatings();
                        int newTotalRating=totalRatings+1;
                        double newRating = ((currentRating*totalRatings) + v) / (double)newTotalRating;
                        String ratingS = String.valueOf(newRating);
                        newRating=Double.parseDouble(ratingS.substring(0,3));
                        ngo.setRatings(newRating);
                        ngo.setTotalRatings(newTotalRating);
                        db.NGODao().updateNGO(ngo);
                        db.ratingsDao().insertRating(new Ratings(CurrentAccount.getCurrentAccID(),ngo.getAccID(),v));
                        finish();
                        startActivity(getIntent());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }
    imgReport.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (CurrentAccount.getAccType() != CurrentAccount.ADMIN_TYPE) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_report_description, null);
                EditText edDescription = dialoglayout.findViewById(R.id.userReportDescription);
                AlertDialog.Builder builder = new AlertDialog.Builder(NGOProfileActivity.this);
                builder.setView(dialoglayout);
                builder.setTitle("Report NGO");
                builder.setPositiveButton("Report", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (edDescription.getText().toString().equals("")) {
                            Toast.makeText(NGOProfileActivity.this, "Please give a Description", Toast.LENGTH_SHORT).show();
                        } else {
                            Report r = new Report(ngo.getAccID(), CurrentAccount.getCurrentAccID(), edDescription.getText().toString(), false);
                            db.reportDao().insertReport(r);
                            Toast.makeText(NGOProfileActivity.this, "NGO Reported", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                });
                builder.show();
            }
            else{
               NGO ngo1 = new NGO(ngo);
                AlertDialog.Builder builder = new AlertDialog.Builder(NGOProfileActivity.this);
                if(ngo.isStatusActive()){
                    builder.setMessage("Are you sure you want to BLOCK this NGO account?");
                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          ngo1.setStatusActive(false);
                          db.NGODao().updateNGO(ngo1);
                      }
                  });
                  builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {

                      }
                  });
                    builder.show();
                }
              else{
                    builder.setMessage("Are you sure you want to UNBLOCK this NGO account?");
                  builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          ngo1.setStatusActive(true);
                          db.NGODao().updateNGO(ngo1);
                      }
                  });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }

            }
        }

    });



    }

    private void initViews() {
        txtProfileName=findViewById(R.id.NGOProfileName);
        txtProfileDesc=findViewById(R.id.NGOProfileDescription);
        txtProfileAddress=findViewById(R.id.NGOProfileAddress);
        rbProfileRating=findViewById(R.id.NGOProfileRating);
        userRatings=findViewById(R.id.NGOProfileUserRatingBar);
        btnDonationHistory=findViewById(R.id.NgoProfileDonationHistoryButton);
        btnProjectList=findViewById(R.id.NGOProfileProjectListButton);
        btnCampLocation=findViewById(R.id.NGOProfileCampLocationButton);
        imgReport=(ImageButton) findViewById(R.id.imgReportButton);
        txtReportBlockText = findViewById(R.id.txtStaticReport);
        db=CCDatabase.getInstance(this);
    }

}