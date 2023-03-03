package com.example.testing.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.Admin;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.Project;

import java.util.Calendar;

public class AdminLoginProfile extends AppCompatActivity {
    private TextView txtAdminName;
    private Button btnAddAdminAccount,btnManageReports,btnGenerateBalanceSheet,btnPending,btnBlock;
    private ImageButton logout;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_profile);
    initViews();
    txtAdminName.setText(db.adminDao().getAdminByID(CurrentAccount.getCurrentAccID()).getName());
        if((this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==Configuration.UI_MODE_NIGHT_YES){
            logout.setColorFilter( 0xffffffff, PorterDuff.Mode.MULTIPLY );
        }
    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AdminLoginProfile.this,LoginActivity.class);
            finishAffinity();
            startActivity(intent);
        }
    });
        btnAddAdminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_add_admin, null);
                EditText edCurrentCNIC,edNewCNIC,edNewPassword,edNewPassword2,edNewAdminName;
                edCurrentCNIC=dialoglayout.findViewById(R.id.edCurrentAdminCNIC);
                edNewCNIC=dialoglayout.findViewById(R.id.edNewAdminCNIC);
                edNewPassword=dialoglayout.findViewById(R.id.edNewAdminPassword);
                edNewPassword2=dialoglayout.findViewById(R.id.edNewAdminPasswordRecheck);
                edNewAdminName=dialoglayout.findViewById(R.id.edNewAdminName);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminLoginProfile.this);
                builder.setView(dialoglayout);
                builder.setTitle("Add New Admin");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(edNewAdminName.getText().toString().equals("") || edNewCNIC.getText().toString().equals("") || edCurrentCNIC.getText().toString().equals("")|| edNewPassword2.getText().toString().equals("") || edNewPassword.getText().toString().equals("")){
                            Toast.makeText(AdminLoginProfile.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        }else {
                            if (!edCurrentCNIC.getText().toString().equals(db.adminDao().getAdminByID(CurrentAccount.getCurrentAccID()).getCNIC())) {
                                Toast.makeText(AdminLoginProfile.this, "CNIC incorrect", Toast.LENGTH_SHORT).show();
                            } else {
                                if (db.adminDao().getAdminIDfromCNIC(edNewCNIC.getText().toString()) != 0) {
                                    Toast.makeText(AdminLoginProfile.this, "CNIC already has an Account", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!edNewPassword.getText().toString().equals(edNewPassword2.getText().toString())) {
                                        Toast.makeText(AdminLoginProfile.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminLoginProfile.this, "AdminAdded.", Toast.LENGTH_SHORT).show();
                                        Admin n = new Admin(db.accountDao().getNewAccountID(), edNewCNIC.getText().toString(), edNewPassword.getText().toString(), edNewCNIC.getText().toString(),edNewAdminName.getText().toString());
                                        db.adminDao().insertAdmin(n,n);
                                    }
                                }
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



        btnGenerateBalanceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(AdminLoginProfile.this,BalanceSheetActivity.class);
               startActivity(intent);
            }
        });


        btnManageReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(AdminLoginProfile.this,ReportListActivity.class);
               startActivity(intent);
            }
        });

        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginProfile.this,PendingDonationsActivity.class);
                startActivity(intent);
            }
        });

        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginProfile.this,NGOListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        txtAdminName=findViewById(R.id.txtAdminName);
        btnAddAdminAccount=findViewById(R.id.btnAdminAddAcount);
        btnGenerateBalanceSheet=findViewById(R.id.btnGenerateBalanceSheet);
        btnManageReports=findViewById(R.id.btnManageReports);
        btnPending=findViewById(R.id.btnPendingDonations);
        btnBlock=findViewById(R.id.btnAdminBlockNGO);
        logout=findViewById(R.id.btnAdminLogout);
        db=CCDatabase.getInstance(this);
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