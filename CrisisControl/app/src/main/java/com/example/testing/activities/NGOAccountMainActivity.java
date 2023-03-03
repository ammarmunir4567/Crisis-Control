package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;

public class NGOAccountMainActivity extends AppCompatActivity {
    private TextView NGOName;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngologin_profile);
        db=CCDatabase.getInstance(this);
        NGOName=findViewById(R.id.txtNGOMainName);

        NGOName.setText(db.NGODao().getNGOByID(CurrentAccount.getCurrentAccID()).getName());
        ImageButton logout = findViewById(R.id.NGOLogout);
        if((this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==Configuration.UI_MODE_NIGHT_YES){
            logout.setColorFilter( 0xffffffff, PorterDuff.Mode.MULTIPLY );
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOAccountMainActivity.this,LoginActivity.class);
                finishAffinity();
                startActivity(intent);
            }
        });

        Button relative1 = findViewById(R.id.rate_p);
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NGOAccountMainActivity.this , NGOProjectListActivity.class);
                intent.putExtra("NGOID", CurrentAccount.getCurrentAccID());
                startActivity(intent);


            }
        });

        Button relative2 = findViewById(R.id.addDonationAllocation);
        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOAccountMainActivity.this,DonationAllocationFormActivity.class);
                startActivity(intent);
            }
        });
        Button relative3 = findViewById(R.id.loc);
        relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NGOAccountMainActivity.this , NGOCampLocationMap.class);
                intent.putExtra("NGOID",CurrentAccount.getCurrentAccID());
                startActivity(intent);


            }
        });

        Button allMedReq = findViewById(R.id.btnAllMedicalHelp);
        allMedReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOAccountMainActivity.this,MedicalRequestListActivity.class);
                startActivity(intent);
            }
        });
        Button NGOMedReq = findViewById(R.id.btnAcceptedMedicalHelp);
        NGOMedReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOAccountMainActivity.this,MedicalRequestListActivity.class);
                intent.putExtra("NGOID",CurrentAccount.getCurrentAccID());
                startActivity(intent);
            }
        });




        Button btt=(Button)findViewById(R.id.v_profile);

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NGOAccountMainActivity.this, NGOProfileViewActivity.class));

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