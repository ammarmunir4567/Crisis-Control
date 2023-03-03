package com.example.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.example.testing.adapters.NGORVadapter;
import com.example.testing.adapters.ProjectRVAdapter;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.NGO;
import com.example.testing.classes.NavigationDrawerIntent;
import com.example.testing.classes.Project;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DonationListActivity extends AppCompatActivity {
    private RecyclerView donationRV;
    private ProjectRVAdapter adapter;
    private ArrayList<Project> projects;
    private CCDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list_w_drawer);
        db=CCDatabase.getInstance(this);

        initDonationRV();

    initNav();

    }

    private void initDonationRV() {
        Button button = findViewById(R.id.btnAddProject);
        button.setVisibility(View.GONE);
        projects=(ArrayList<Project>) db.projectDao().getAllProjects();
        donationRV = findViewById(R.id.donationRV);
        adapter = new ProjectRVAdapter(this);
        adapter.setDonationProjects(projects);
        donationRV.setAdapter(adapter);
        donationRV.setLayoutManager(new LinearLayoutManager(this));



    }

    private void initNav() {

        MaterialToolbar toolbar = findViewById(R.id.navToolBar);
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
                Intent intent = navIntent.getIntent(DonationListActivity.this,item);
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
        MenuItem donationSort = menu.findItem(R.id.Sort_Amount);
        donationSort.setVisible(true);
        MenuItem donationSortAsc = menu.findItem(R.id.Sort_Amount_asc);
        donationSortAsc.setVisible(true);
        donationSort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                projects = (ArrayList<Project>) db.projectDao().getProjectsSortedByDonations_Desc();
                adapter.setDonationProjects(projects);
                donationRV.setAdapter(adapter);
                donationRV.setLayoutManager(new LinearLayoutManager(DonationListActivity.this));

                return true;
            }
        });
        donationSortAsc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                projects = new ArrayList<>();
                projects = (ArrayList<Project>) db.projectDao().getProjectsSortedByDonations_Asc();
                adapter.setDonationProjects(projects);
                donationRV.setAdapter(adapter);
                donationRV.setLayoutManager(new LinearLayoutManager(DonationListActivity.this));
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
                liveProjects.observe(DonationListActivity.this, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        adapter = new ProjectRVAdapter(DonationListActivity.this);
                        adapter.setDonationProjects((ArrayList<Project>) projects);
                        donationRV.setAdapter(adapter);
                        donationRV.setLayoutManager(new LinearLayoutManager(DonationListActivity.this));

                    }
                });


                return false;
            }

        });
        return true;
    }

    @Override
    protected void onRestart() {
        adapter.setDonationProjects(projects);
        donationRV.setAdapter(adapter);
        donationRV.setLayoutManager(new LinearLayoutManager(DonationListActivity.this));

        super.onRestart();
    }
}