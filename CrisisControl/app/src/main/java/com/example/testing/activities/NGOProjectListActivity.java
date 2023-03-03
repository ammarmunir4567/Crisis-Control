package com.example.testing.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.adapters.ProjectRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Project;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;


public class NGOProjectListActivity extends AppCompatActivity {
    private RecyclerView ngoProjRV;
    private ProjectRVAdapter adapter;
    private ArrayList<Project> projects;
    private Button btnAdd;
    private int NGOID;
    private CCDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        Intent intent = getIntent();
        NGOID= intent.getIntExtra("NGOID",-1);
        db= CCDatabase.getInstance(this);
        initNGOProjectRV();
    }

   private void initNGOProjectRV(){

        if(NGOID!=-1) {
            btnAdd=findViewById(R.id.btnAddProject);
            projects = (ArrayList<Project>) db.projectDao().getNGOProjectsByNGOID(NGOID);
            if(projects.size()!=0) {
                ngoProjRV = findViewById(R.id.donationRV);
                adapter = new ProjectRVAdapter(this);
                adapter.setNGOProjects(projects);
                ngoProjRV.setAdapter(adapter);
                ngoProjRV.setLayoutManager(new LinearLayoutManager(this));
            }
            else{
                if(CurrentAccount.getAccType()!=CurrentAccount.NGO_TYPE){
                Toast.makeText(this, "This NGO does not have any projects", Toast.LENGTH_LONG).show();
                finish();
                }
                else{
                    Toast.makeText(this, "You don't have any Projects. Click below to add now", Toast.LENGTH_SHORT).show();
                }
            }
            if(CurrentAccount.getAccType()==CurrentAccount.NGO_TYPE){
                btnAdd.setVisibility(View.VISIBLE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = getLayoutInflater();
                        View dialoglayout = inflater.inflate(R.layout.dialog_add_projects, null);
                        EditText edProjTitle,edProjDesc,edProjVolunteers,edProjDonations;
                        edProjDonations=dialoglayout.findViewById(R.id.edAddProjectDonation);
                        edProjTitle=dialoglayout.findViewById(R.id.edAddProjectTitle);
                        edProjDesc=dialoglayout.findViewById(R.id.edAddProjectDescription);
                        edProjVolunteers=dialoglayout.findViewById(R.id.edAddProjectVolunteer);
                        AlertDialog.Builder builder = new AlertDialog.Builder(NGOProjectListActivity.this);
                        builder.setView(dialoglayout);
                        builder.setTitle("Project Details");
                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(edProjDesc.getText().toString().equals("")||edProjTitle.getText().toString().equals("")||(edProjDonations.getText().toString().equals("")&& edProjVolunteers.getText().toString().equals(""))){
                                            Toast.makeText(NGOProjectListActivity.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            if(db.projectDao().getNGOIDFromProjectTitle(edProjTitle.getText().toString())==CurrentAccount.getCurrentAccID()){
                                                Toast.makeText(NGOProjectListActivity.this, "A Project With This title Already Exists", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(!edProjVolunteers.getText().toString().equals("") &&Integer.parseInt(edProjVolunteers.getText().toString())!=0 && Integer.parseInt(edProjVolunteers.getText().toString())<10){
                                                Toast.makeText(NGOProjectListActivity.this, "You can not request for less than 10 volunteers", Toast.LENGTH_SHORT).show();
                                            }else if (!edProjDonations.getText().toString().equals("")&& Double.parseDouble(edProjDonations.getText().toString())!=0 && Double.parseDouble(edProjDonations.getText().toString())<100) {
                                                Toast.makeText(NGOProjectListActivity.this, "You can not set donations for less than 100 -/Rs", Toast.LENGTH_SHORT).show();

                                            }
                                            else{
                                                int vol=0;
                                                double don=0;
                                                if(!edProjDonations.getText().toString().equals("")){
                                                    don=Double.parseDouble(edProjDonations.getText().toString());
                                                }
                                                if(!edProjVolunteers.getText().toString().equals("")){
                                                    vol=Integer.parseInt(edProjVolunteers.getText().toString());
                                                }

                                                db.projectDao().insertProject(new Project(edProjTitle.getText().toString(),edProjDesc.getText().toString(),CurrentAccount.getCurrentAccID(),don,vol, Calendar.getInstance().getTime()));
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

                btnAdd.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onRestart() {
        projects = (ArrayList<Project>) db.projectDao().getNGOProjectsByNGOID(NGOID);
        if(projects.size()!=0) {
            ngoProjRV = findViewById(R.id.donationRV);
            adapter = new ProjectRVAdapter(this);
            adapter.setNGOProjects(projects);
            ngoProjRV.setAdapter(adapter);
            ngoProjRV.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            if(CurrentAccount.getAccType()!=CurrentAccount.NGO_TYPE){
                Toast.makeText(this, "This NGO does not have any projects", Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                Toast.makeText(this, "You don't have any Projects. Click below to add now", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRestart();
    }
}