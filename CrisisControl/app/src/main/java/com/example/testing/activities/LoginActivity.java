package com.example.testing.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private EditText loginUser,loginPass;
    private Button btnLogin,btnGuest;
    private TextView txtLoginSignUp;
    private CCDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = "Channel1";
            String channelName = "Fcm notifications";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult();

                        //Toast.makeText(MainActivity.this, ""+token, Toast.LENGTH_SHORT).show();
                    }
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int aID = db.accountDao().getAccIDByLogin(loginUser.getText().toString(), loginPass.getText().toString());
                if (aID != 0) {
                    FirebaseMessaging.getInstance().subscribeToTopic("PushNotifications");
                    if (db.userDao().getUserByID(aID) != null) {

                        CurrentAccount.initInstance(aID, db.userDao().getUserByID(aID).getUserType());
                        Intent intent = new Intent(LoginActivity.this, LiveFeedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else if (db.NGODao().getNGOByIDLogin(aID) != null) {
                        if(!db.NGODao().getNGOByIDLogin(aID).isStatusActive()){
                            Toast.makeText(LoginActivity.this, "This account has been Blocked", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            CurrentAccount.initInstance(aID, CurrentAccount.NGO_TYPE);
                            Intent intent = new Intent(LoginActivity.this, NGOAccountMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } else if(db.adminDao().getAdminByID(aID)!=null){
                        CurrentAccount.initInstance(aID, CurrentAccount.ADMIN_TYPE);
                        Intent intent = new Intent(LoginActivity.this, AdminLoginProfile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                } else{
                    Toast.makeText(LoginActivity.this, "User/Password incorrect. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LiveFeedActivity.class);
                CurrentAccount.initInstance(-1,CurrentAccount.GUEST_TYPE);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        loginUser=findViewById(R.id.loginUserName);
        loginPass=findViewById(R.id.loginPassword);
        btnLogin=findViewById(R.id.btnLogin);
        txtLoginSignUp=findViewById(R.id.txtLoginSignUp);
        btnGuest=findViewById(R.id.btnGuest);
        db=CCDatabase.getInstance(this);

    }
}