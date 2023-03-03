package com.example.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.adapters.ProjectRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.NavigationDrawerIntent;
import com.example.testing.classes.Project;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class VolunteerListActivity extends AppCompatActivity {
    private RecyclerView volunteerRV;
    private ProjectRVAdapter adapter;
    private ArrayList<Project> projects;
    private CCDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list_w_drawer);
        db= CCDatabase.getInstance(this);
        initVolunteerRV();

        initNav();

    }

    private void initVolunteerRV() {
        Button button = findViewById(R.id.btnAddProject);
        button.setVisibility(View.GONE);
        projects = (ArrayList<Project>) db.projectDao().getAllProjects();
        volunteerRV = findViewById(R.id.donationRV);
        adapter = new ProjectRVAdapter(this);
        adapter.setVolunteerProjects(projects);
        volunteerRV.setAdapter(adapter);
        volunteerRV.setLayoutManager(new LinearLayoutManager(this));




    }



    private void initNav() {

        MaterialToolbar toolbar = findViewById(R.id.navToolBar);
        toolbar.setTitle("Volunteer Work");
        DrawerLayout drawer = findViewById(R.id.navDrawer);
        NavigationView navigationView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationDrawerIntent navIntent=new NavigationDrawerIntent();
                Intent intent = navIntent.getIntent(VolunteerListActivity.this,item);
                if(intent!=null){
                    int loggedOut = intent.getIntExtra("Logout",0);
                    if(loggedOut==0) {
                        drawer.closeDrawers();
                        startActivity(intent);
                    }
                    else {
                        drawer.closeDrawers();
                        finishAffinity();
                        startActivity(intent);
                    }
                    return true;
                }
                else
                    return false;

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= new MenuInflater(this);
        inflater.inflate(R.menu.search_action,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem volunteerSort = menu.findItem(R.id.Sort_Volunteers);
        volunteerSort.setVisible(true);
        MenuItem volunteerSort_Asc = menu.findItem(R.id.Sort_Volunteers_asc);
        volunteerSort_Asc.setVisible(true);
        volunteerSort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                projects = (ArrayList<Project>) db.projectDao().getProjectsSortedByVolunteers_Desc();
                adapter.setVolunteerProjects(projects);
                volunteerRV.setAdapter(adapter);
                volunteerRV.setLayoutManager(new LinearLayoutManager(VolunteerListActivity.this));

                return true;
            }
        });
        volunteerSort_Asc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                projects = (ArrayList<Project>) db.projectDao().getProjectsSortedByVolunteers_Asc();
                adapter.setVolunteerProjects(projects);
                volunteerRV.setAdapter(adapter);
                volunteerRV.setLayoutManager(new LinearLayoutManager(VolunteerListActivity.this));

                return true;
            }
        });
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                LiveData<List<Project>> liveProjects = db.projectDao().searchProject(newText);
                liveProjects.observe(VolunteerListActivity.this, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        adapter = new ProjectRVAdapter(VolunteerListActivity.this);
                        adapter.setVolunteerProjects((ArrayList<Project>) projects);
                        volunteerRV.setAdapter(adapter);
                        volunteerRV.setLayoutManager(new LinearLayoutManager(VolunteerListActivity.this));

                    }
                });


                return false;
            }

        });
        return true;
    }

    @Override
    protected void onRestart() {
        adapter.setVolunteerProjects(projects);
        volunteerRV.setAdapter(adapter);
        volunteerRV.setLayoutManager(new LinearLayoutManager(VolunteerListActivity.this));

        super.onRestart();
    }
}
