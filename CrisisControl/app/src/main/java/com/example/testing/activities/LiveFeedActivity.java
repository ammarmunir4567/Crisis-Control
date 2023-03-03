package com.example.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.adapters.LiveFeedRVAdapter;
import com.example.testing.classes.APIRawData;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.LiveFeedData;
import com.example.testing.classes.NavigationDrawerIntent;
import com.example.testing.classes.User;
import com.example.testing.interfaces.APIEndPoint;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiveFeedActivity extends AppCompatActivity {
    private RecyclerView liveFeedRV;
    private LiveFeedRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_w_drawer);
        Intent intent = getIntent();
        int NavByNotif = intent.getIntExtra("NavByNotification",-1);
        if(NavByNotif == 1){
            CurrentAccount.initInstance(-1,CurrentAccount.GUEST_TYPE);
        }
       TransformData();
       initNav();

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
                Intent intent = navIntent.getIntent(LiveFeedActivity.this,item);
                if(intent!=null){
                    int loggedOut = intent.getIntExtra("Logout",0);
                    drawer.closeDrawers();
                    if(loggedOut==0) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                    else {
                        finishAffinity();
                    }
                    startActivity(intent);
                    return true;
                }
                else
                    return false;

            }
        });

    }

    private void TransformData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.reliefweb.int/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIEndPoint endPoint=retrofit.create(APIEndPoint.class);
        Call<APIRawData> m= endPoint.getRawData();
        m.enqueue(new Callback<APIRawData>() {
            @Override
            public void onResponse(Call<APIRawData> call, Response<APIRawData> response) {
                if (response.isSuccessful()) {
                    APIRawData RData = (APIRawData) response.body();
                    ArrayList<LiveFeedData> data = new ArrayList<>();
                   for(int i=0; i<Integer.valueOf( RData.getCount().toString());i++) {
                       data.add(i, new LiveFeedData(1,RData.getData().get(i).getFields().getUrl().toString(), RData.getData().get(i).getFields().getSource().get(0).getName(),RData.getData().get(i).getFields().getTitle().toString()));
                   }
                    liveFeedRV = findViewById(R.id.livefeedRV);
                    adapter = new LiveFeedRVAdapter(LiveFeedActivity.this);
                    adapter.setLiveFeedArray(data);
                    liveFeedRV.setAdapter(adapter);
                    liveFeedRV.setLayoutManager(new LinearLayoutManager(LiveFeedActivity.this));
                }

            }
                @Override
                public void onFailure (Call < APIRawData > call, Throwable t){
//
                    t.printStackTrace();
                }

        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}