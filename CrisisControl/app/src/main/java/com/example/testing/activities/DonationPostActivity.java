package com.example.testing.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Donation;
import com.example.testing.classes.Project;
import com.example.testing.classes.User;

import java.util.Calendar;

public class DonationPostActivity extends AppCompatActivity {

    private TextView NGOName,title,amount,description,donatedAmount;
    private EditText userAmount;
    private ProgressBar donationProgress;
    private SeekBar userAmountSeek;
    private Button donateButton,btnNGORequestVolunteers,btnNGORequestDonations,btnNGOUpdateAccount;
    private Project project;
    private RelativeLayout NgoOptions;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_post);
        intiViews();
        Intent intent=getIntent();
        Project p=intent.getParcelableExtra("Project");
        project=db.projectDao().getProjectByID(p.getProjectID());
        NGOName.setText(db.NGODao().getNGOByID(project.getNgoID()).getName());
        description.setText(project.getDescription());
        title.setText(project.getTitle());
        amount.setText(String.valueOf(project.getDonationAsked()));
        donatedAmount.setText(String.valueOf(db.donationDao().getProjectTotalDonatedAmount(project.getProjectID())));
        donationProgress.setMax((int)project.getDonationAsked());
        donationProgress.setProgress(db.donationDao().getProjectTotalDonatedAmount(project.getProjectID()));
        userAmountSeek.setMax((int)project.getDonationAsked() - db.donationDao().getProjectTotalDonatedAmount(project.getProjectID()));
        userAmountSeek.incrementProgressBy(100);
        userAmountSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                userAmount.setText(String.valueOf(seekBar.getProgress()));
                userAmount.setSelection(String.valueOf(i).length());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = seekBar.getProgress();
                i=i/100;
                i=i*100;
                seekBar.setProgress(i);
                userAmount.setText(String.valueOf(seekBar.getProgress()));
                userAmount.setSelection(String.valueOf(seekBar.getProgress()).length());

            }
        });

        userAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("") && editable.toString().length()<10) {
                    userAmountSeek.setProgress(Integer.parseInt(editable.toString()));

                }
                else if(editable.toString().length()==10){
                    Toast.makeText(DonationPostActivity.this, "Amount invalid", Toast.LENGTH_SHORT).show();
                }else{
                    userAmountSeek.setProgress(0);
                }
            }
        });

        if(CurrentAccount.getAccType()== User.USER_TYPE_DONOR) {
            donateButton.setVisibility(View.VISIBLE);
            donateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(userAmount.getText().toString())<100 || userAmountSeek.getProgress()<100){
                        Toast.makeText(DonationPostActivity.this, "Minimum amount for donation is 100/- Rs", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent1 = new Intent(DonationPostActivity.this, DonorPaymentActivity.class);
                        intent1.putExtra("Amount", userAmount.getText().toString());
                        startActivityResultLauncher.launch(intent1);
                    }

                }
            });
        }
        else{
           donateButton.setVisibility(View.GONE);


        }

        if(CurrentAccount.getAccType()==CurrentAccount.NGO_TYPE){
            NgoOptions.setVisibility(View.VISIBLE);
            userAmount.setVisibility(View.GONE);
            userAmountSeek.setVisibility(View.GONE);
            btnNGOUpdateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.dialog_add_projects, null);
                    EditText edProjTitle,edProjDesc,edProjVolunteers,edProjDonations;
                    edProjDonations=dialoglayout.findViewById(R.id.edAddProjectDonation);
                    edProjTitle=dialoglayout.findViewById(R.id.edAddProjectTitle);
                    edProjDesc=dialoglayout.findViewById(R.id.edAddProjectDescription);
                    edProjVolunteers=dialoglayout.findViewById(R.id.edAddProjectVolunteer);
                    edProjDonations.setVisibility(View.GONE);
                    edProjVolunteers.setVisibility(View.GONE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(DonationPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(db.projectDao().getNGOIDFromProjectTitle(edProjTitle.getText().toString())==CurrentAccount.getCurrentAccID()){
                                Toast.makeText(DonationPostActivity.this, "A Project With This title Already Exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Project p = new Project(project);
                                if(!edProjTitle.getText().toString().equals("")) {
                                    p.setTitle(edProjTitle.getText().toString());
                                }
                                if(!edProjDesc.getText().toString().equals("")) {
                                    p.setDescription(edProjDesc.getText().toString());
                                }
                                db.projectDao().updateProject(p);
                                finish();
                                startActivity(getIntent());
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
            });


            btnNGORequestVolunteers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.dialog_request_more_help, null);
                   EditText edVolunteers;
                   edVolunteers=dialoglayout.findViewById(R.id.requestHelp);
                   edVolunteers.setHint("Change number of Volunteers needed");
                    AlertDialog.Builder builder = new AlertDialog.Builder(DonationPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           if(!edVolunteers.getText().toString().equals("")){
                               if(Integer.parseInt(edVolunteers.getText().toString())<db.volunteersDao().getCountProjectVolunteers(project.getProjectID())){
                                   Toast.makeText(DonationPostActivity.this, "You can not set volunteers needed to less than applied volunteers", Toast.LENGTH_SHORT).show();
                               }  else if(Integer.parseInt(edVolunteers.getText().toString())<10){
                                   Toast.makeText(DonationPostActivity.this, "You can not request for less than 10 volunteers", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Project p = new Project(project);
                                   p.setVolunteersNeeded(Integer.parseInt(edVolunteers.getText().toString()));
                                   db.projectDao().updateProject(p);
                                   finish();
                                   startActivity(getIntent());
                               }
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
            });

            btnNGORequestDonations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.dialog_request_more_help, null);
                    EditText edDonations;
                    edDonations=dialoglayout.findViewById(R.id.requestHelp);
                    edDonations.setHint("Change amount of Donation needed");
                    AlertDialog.Builder builder = new AlertDialog.Builder(DonationPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!edDonations.getText().toString().equals("")){
                                if(Double.valueOf(edDonations.getText().toString())<db.donationDao().getProjectTotalDonatedAmount(project.getProjectID())){
                                    Toast.makeText(DonationPostActivity.this, "You can not set donations need to less then donated amount", Toast.LENGTH_SHORT).show();
                                }
                                else if(Double.parseDouble(edDonations.getText().toString())<100){
                                    Toast.makeText(DonationPostActivity.this, "You can not set donations for less than 100 -/Rs", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Project p = new Project(project);
                                    p.setDonationAsked(Double.parseDouble(edDonations.getText().toString()));
                                    db.projectDao().updateProject(p);
                                    finish();
                                    startActivity(getIntent());
                                }
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
            });

        }
        else{
            NgoOptions.setVisibility(View.GONE);
        }



    }

    private void intiViews() {
        NGOName=findViewById(R.id.donationPostNgoName);
        title=findViewById(R.id.donationPostTitle);
        amount=findViewById(R.id.donatePostAmount);
        description=findViewById(R.id.donationPostDescription);
        userAmount=findViewById(R.id.donationPostEditText);
        donationProgress=findViewById(R.id.donationPostProgressBar);
        userAmountSeek=findViewById(R.id.donationPostSeekBar);
        donateButton=findViewById(R.id.donationPostButton);
        NgoOptions=findViewById(R.id.NGOAccountOptionLayout);
        btnNGORequestVolunteers=findViewById(R.id.btnRequestVolunteers);
        btnNGOUpdateAccount=findViewById(R.id.btnUpdateProject);
        btnNGORequestDonations=findViewById(R.id.btnRequestDonations);
        donatedAmount=findViewById(R.id.donatedPostAmount);
        db=CCDatabase.getInstance(this);



    }


    ActivityResultLauncher<Intent> startActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String ResponseCode = data.getStringExtra("pp_ResponseCode");
                        System.out.println("DateFn: ResponseCode:" + ResponseCode);
                        if(!ResponseCode.equals("000")) {
                            Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                        db.donationDao().insertDonation(new Donation(CurrentAccount.getCurrentAccID(),project.getProjectID(),Calendar.getInstance().getTime(),Double.parseDouble(userAmount.getText().toString()),Donation.STATUS_PENDING));
                        finish();
                        startActivity(getIntent());
                    }
                }
            });


}