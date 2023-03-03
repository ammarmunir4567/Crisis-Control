package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.NGO;

public class NGOProfileViewActivity extends AppCompatActivity {
    private EditText edName,edAddress,edDescription;
    private TextView txtRatings,txtTotalRatings;
    private int btnPressed=0;
    private Button btnUpdate;
    private CCDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoprofile_view);
        initViews();
        NGO n=db.NGODao().getNGOByID(CurrentAccount.getCurrentAccID());
        edName.setText(n.getName());
        edAddress.setText(n.getAddress());
        edDescription.setText(n.getDesc());
        edName.setFocusable(false);
        edAddress.setFocusable(false);
        edDescription.setFocusable(false);
        txtRatings.setText(String.valueOf(n.getRatings()));
        txtTotalRatings.setText(String.valueOf(n.getTotalRatings()));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnPressed==0){
                    edName.setFocusableInTouchMode(true);
                    edName.setFocusable(true);
                    edAddress.setFocusableInTouchMode(true);
                    edAddress.setFocusable(true);
                    edDescription.setFocusableInTouchMode(true);
                    edDescription.setFocusable(true);
                    btnUpdate.setText("Submit");
                    btnPressed++;
                }else if (btnPressed==1){
                    if(edName.getText().toString().equals("") || edAddress.getText().toString().equals("") || edDescription.getText().toString().equals("")){
                        Toast.makeText(NGOProfileViewActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }else {
                        edName.setFocusable(false);
                        edAddress.setFocusable(false);
                        edDescription.setFocusable(false);
                        btnUpdate.setText("Update Account");
                        btnPressed = 0;
                        NGO ngo = new NGO(db.NGODao().getNGOByID(CurrentAccount.getCurrentAccID()));
                        ngo.setName(edName.getText().toString());
                        ngo.setAddress(edAddress.getText().toString());
                        ngo.setDesc(edDescription.getText().toString());
                        db.NGODao().updateNGO(ngo);
                    }
                }
            }
        });

    }

    private void initViews() {
        edName = findViewById(R.id.edNgoAccountName);
        edAddress = findViewById(R.id.edNgoAccountAddress);
        edDescription = findViewById(R.id.edNgoAccountDescription);
        txtRatings = findViewById(R.id.txtNGOAccountRatings);
        txtTotalRatings = findViewById(R.id.txtNGOAccountTotalRatings);
        btnUpdate = findViewById(R.id.btnUpdateNGOAccountDetails);
        db=CCDatabase.getInstance(this);
    }
}