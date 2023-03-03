package com.example.testing.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Project;
import com.example.testing.classes.User;
import com.example.testing.classes.AppliedVolunteer;

import java.util.Calendar;

public class VolunteerPostActivity extends AppCompatActivity {
    private TextView NGOName,title,amount,description,applied;
    private ProgressBar volunteeerProgress;
    private Button volunteerButton,btnNGORequestVolunteers,btnNGORequestDonations,btnNGOUpdateAccount,btnVolunteerList;
    private EditText edCNIC,edPhoneNum;
    private RelativeLayout NgoOptions;
    private Project project;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_post);
        initViews();
        Intent intent=getIntent();
       Project p =intent.getParcelableExtra("Project");
       project=db.projectDao().getProjectByID(p.getProjectID());
        NGOName.setText(db.NGODao().getNGOByID(project.getNgoID()).getName());
        description.setText(project.getDescription());
        title.setText(project.getTitle());
        amount.setText(String.valueOf(project.getVolunteersNeeded()));
        applied.setText(String.valueOf(db.volunteersDao().getCountProjectVolunteers(project.getProjectID())));
        volunteeerProgress.setMax((int)project.getVolunteersNeeded());
        volunteeerProgress.setProgress(db.volunteersDao().getCountProjectVolunteers(project.getProjectID()));
        if(CurrentAccount.getAccType()== User.USER_TYPE_VOLUNTEER){
            volunteerButton.setVisibility(View.VISIBLE);
            volunteerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (db.volunteersDao().checkAlreadyApplied(CurrentAccount.getCurrentAccID(), project.getProjectID()) == null) {
                        if (edCNIC.getText().toString().equals("") || edPhoneNum.getText().toString().equals("")) {
                            Toast.makeText(VolunteerPostActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else if (edCNIC.getText().toString().length() != 13) {
                            Toast.makeText(VolunteerPostActivity.this, "Please enter valid CNIC Number", Toast.LENGTH_SHORT).show();
                        } else if (edPhoneNum.getText().toString().length() != 11 || !edPhoneNum.getText().toString().substring(0, 2).equals("03")) {
                            Toast.makeText(VolunteerPostActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        } else if (db.userDao().getUserByID(CurrentAccount.getCurrentAccID()).getDOB().getTime() < 18L * 365 * 24 * 60 * 60 * 1000) {
                            Toast.makeText(VolunteerPostActivity.this, "You have to be atleast 18 years to Volunteer", Toast.LENGTH_SHORT).show();
                         } else {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(Calendar.getInstance().getTime().getTime() - (db.userDao().getUserByID(CurrentAccount.getCurrentAccID()).getDOB().getTime()));
                        db.volunteersDao().insertVolunteer(new AppliedVolunteer(CurrentAccount.getCurrentAccID(), project.getProjectID(), edCNIC.getText().toString(), edPhoneNum.getText().toString(), c.get(Calendar.YEAR) - 1970));
                        Toast.makeText(VolunteerPostActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                    else{
                        Toast.makeText(VolunteerPostActivity.this, "You have already applied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            volunteerButton.setVisibility(View.GONE);
        }




        if(CurrentAccount.getAccType()==CurrentAccount.NGO_TYPE){
            NgoOptions.setVisibility(View.VISIBLE);
            edPhoneNum.setVisibility(View.GONE);
            edCNIC.setVisibility(View.GONE);
            btnVolunteerList.setVisibility(View.VISIBLE);

            btnVolunteerList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1= new Intent(VolunteerPostActivity.this,AppliedVolunteerListActivity.class);
                    intent1.putExtra("ProjID",project.getProjectID());
                    startActivity(intent1);
                }
            });

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                if(db.projectDao().getNGOIDFromProjectTitle(edProjTitle.getText().toString())==CurrentAccount.getCurrentAccID()){
                                    Toast.makeText(VolunteerPostActivity.this, "A Project With This title Already Exists", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!edVolunteers.getText().toString().equals("")){
                                if(Integer.parseInt(edVolunteers.getText().toString())<db.volunteersDao().getCountProjectVolunteers(project.getProjectID())){
                                    Toast.makeText(VolunteerPostActivity.this, "You can not set volunteers needed to less than applied volunteers", Toast.LENGTH_SHORT).show();
                                }
                                else if(Integer.parseInt(edVolunteers.getText().toString())<10){
                                    Toast.makeText(VolunteerPostActivity.this, "You can not request for less than 10 volunteers", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerPostActivity.this);
                    builder.setView(dialoglayout);
                    builder.setTitle("Project Details");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!edDonations.getText().toString().equals("")){
                                if(Double.parseDouble(edDonations.getText().toString())<db.donationDao().getProjectTotalDonatedAmount(project.getProjectID())){
                                    Toast.makeText(VolunteerPostActivity.this, "You can not set donations need to less then donated amount", Toast.LENGTH_SHORT).show();
                                }
                                else if(Double.parseDouble(edDonations.getText().toString())<100){
                                    Toast.makeText(VolunteerPostActivity.this, "You can not set donations for less than 100 -/Rs", Toast.LENGTH_SHORT).show();
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

    private void initViews() {
        NGOName=findViewById(R.id.VolunteerPostNgoName);
        title=findViewById(R.id.VolunteerPostTitle);
        amount=findViewById(R.id.VolunteerPostAmount);
        description=findViewById(R.id.VolunteerPostDescription);
        volunteeerProgress=findViewById(R.id.VolunteerPostProgressBar);
        volunteerButton=findViewById(R.id.volunteerPostButton);
        NgoOptions=findViewById(R.id.NGOAccountOptionLayoutVol);
        btnNGORequestVolunteers=findViewById(R.id.btnRequestVolunteersVol);
        btnNGOUpdateAccount=findViewById(R.id.btnUpdateProjectVol);
        btnNGORequestDonations=findViewById(R.id.btnRequestDonationsVol);
        edCNIC=findViewById(R.id.volunteerPostCNIC);
        edPhoneNum=findViewById(R.id.volunteerPostPhoneNumber);
        applied=findViewById(R.id.volunteeredAmount);
        btnVolunteerList=findViewById(R.id.viewVolunteerList);
        db=CCDatabase.getInstance(this);
    }


}