package com.example.testing.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.testing.R;
import com.example.testing.fragments.SignUp_Categories;

public class SignUpActivity extends AppCompatActivity {
public static final int SIGNUP_DONOR=0,SIGNUP_VOLUNTEER=1,SIGNUP_AFFECTEE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.SignUpFragmentContainer, SignUp_Categories.class,null)
                .setReorderingAllowed(true)
                .commit();
    }
}