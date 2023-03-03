package com.example.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.NGO;
import com.example.testing.classes.NavigationDrawerIntent;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class NGOListActivity extends AppCompatActivity {
    private RecyclerView NGORV;
    private NGORVadapter adapter;
    private ArrayList<NGO> NGOs;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_list_w_drawer);
        db=CCDatabase.getInstance(this);
        NGORV = findViewById(R.id.NGORV);
        initNav();
        NGOs=new ArrayList<>();
        initNGORV();
    }



    private void initNGORV(){
        NGOs = (ArrayList<NGO>) db.NGODao().getAllNGOs();
        adapter = new NGORVadapter(this);
        adapter.setNgoArrayList(NGOs);
        NGORV.setAdapter(adapter);
        NGORV.setLayoutManager(new LinearLayoutManager(this));


    }

    private void initNav() {

            MaterialToolbar toolbar = findViewById(R.id.navToolBar);
            DrawerLayout drawer = findViewById(R.id.navDrawer);
            NavigationView navigationView = findViewById(R.id.navView);
            setSupportActionBar(toolbar);
             if(CurrentAccount.getAccType()!=CurrentAccount.ADMIN_TYPE) {
                 ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
                 drawer.addDrawerListener(toggle);
            toggle.syncState();


            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    NavigationDrawerIntent navIntent = new NavigationDrawerIntent();
                    Intent intent = navIntent.getIntent(NGOListActivity.this, item);
                    if (intent != null) {
                        int loggedOut = intent.getIntExtra("Logout", 0);
                        if (loggedOut == 0) {
                            drawer.closeDrawers();
                            startActivity(intent);
                        } else {
                            drawer.closeDrawers();
                            finishAffinity();
                            startActivity(intent);
                        }
                        return true;
                    } else
                        return false;

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= new MenuInflater(this);
        inflater.inflate(R.menu.search_action,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem ratingSort = menu.findItem(R.id.Sort_Ratings);
        ratingSort.setVisible(true);
        MenuItem ratingSortAsc = menu.findItem(R.id.Sort_Ratings_asc);
        ratingSortAsc.setVisible(true);
        if(CurrentAccount.getAccType()== CurrentAccount.ADMIN_TYPE){
            ratingSortAsc.setVisible(false);
            ratingSort.setVisible(false);
            MenuItem blocked = menu.findItem(R.id.Sort_Blocked);
            blocked.setVisible(true);
            MenuItem active = menu.findItem(R.id.Sort_Active);
            active.setVisible(true);

            blocked.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    NGOs = (ArrayList<NGO>) db.NGODao().getBlockedNGOs();
                    adapter.setNgoArrayList(NGOs);
                    NGORV.setAdapter(adapter);
                    NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));

                    return true;
                }
            });

            active.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    NGOs = (ArrayList<NGO>) db.NGODao().getAllNGOs();
                    adapter.setNgoArrayList(NGOs);
                    NGORV.setAdapter(adapter);
                    NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));

                    return true;
                }
            });
        }

        ratingSort.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               NGOs = (ArrayList<NGO>) db.NGODao().NGOSortByRatings();
                adapter.setNgoArrayList(NGOs);
                NGORV.setAdapter(adapter);
                NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));

                return true;
            }
        });
        ratingSortAsc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                NGOs = (ArrayList<NGO>) db.NGODao().NGOSortByRatings_asc();
                adapter.setNgoArrayList(NGOs);
                NGORV.setAdapter(adapter);
                NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));
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
                if(CurrentAccount.getAccType()==CurrentAccount.ADMIN_TYPE){
                    LiveData<List<NGO>> allLiveNGOs=db.NGODao().getActiveAndBlockedNGOS();
                    allLiveNGOs.observe(NGOListActivity.this, new Observer<List<NGO>>() {
                        @Override
                        public void onChanged(List<NGO> ngos) {
                            adapter = new NGORVadapter(NGOListActivity.this);
                            adapter.setNgoArrayList((ArrayList<NGO>) ngos);
                            NGORV.setAdapter(adapter);
                            NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));

                        }
                    });

                }
                else {
                    LiveData<List<NGO>> liveNGOs = db.NGODao().SearchNGO(newText);
                    liveNGOs.observe(NGOListActivity.this, new Observer<List<NGO>>() {
                        @Override
                        public void onChanged(List<NGO> ngos) {
                            Log.d("SEARCH", "onQueryTextChange: GOT LIVEDATA FROM DB ");
                            adapter = new NGORVadapter(NGOListActivity.this);
                            adapter.setNgoArrayList((ArrayList<NGO>) ngos);
                            NGORV.setAdapter(adapter);
                            NGORV.setLayoutManager(new LinearLayoutManager(NGOListActivity.this));

                        }
                    });

                }
                return false;
            }

        });
        return true;
    }

    @Override
    protected void onRestart() {

        NGOs = (ArrayList<NGO>) db.NGODao().getAllNGOs();
        adapter = new NGORVadapter(this);
        adapter.setNgoArrayList(NGOs);
        NGORV.setAdapter(adapter);
        NGORV.setLayoutManager(new LinearLayoutManager(this));


        super.onRestart();
    }
}
