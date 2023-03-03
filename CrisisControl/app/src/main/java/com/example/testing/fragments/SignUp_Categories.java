package com.example.testing.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testing.R;
import com.example.testing.activities.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUp_Categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp_Categories extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignUp_Categories() {
        // Required empty public constructor
    }



    public static SignUp_Categories newInstance(String param1, String param2) {
        SignUp_Categories fragment = new SignUp_Categories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up__categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnVolunteer = view.findViewById(R.id.btnSignUpVolunteer);
        Button btnDonor = view.findViewById(R.id.btnSignUpDonor);
        Button btnNGO = view.findViewById(R.id.btnSignUpNGO);
        Button btnAffectee = view.findViewById(R.id.btnSignUpAffectee);
        FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
        btnDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = SignUpUserDetails.newInstance(SignUpActivity.SIGNUP_DONOR);
                fragmentManager1.beginTransaction().replace(R.id.SignUpFragmentContainer,f)
                        .setReorderingAllowed(true)
                        .addToBackStack("SignUpBackStack")
                        .commit();
            }
        });
        btnVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = SignUpUserDetails.newInstance(SignUpActivity.SIGNUP_VOLUNTEER);
                fragmentManager1.beginTransaction().replace(R.id.SignUpFragmentContainer,f)
                        .setReorderingAllowed(true)
                        .addToBackStack("SignUpBackStack")
                        .commit();
            }
        });

        btnAffectee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = SignUpUserDetails.newInstance(SignUpActivity.SIGNUP_AFFECTEE);
                fragmentManager1.beginTransaction().replace(R.id.SignUpFragmentContainer,f)
                        .setReorderingAllowed(true)
                        .addToBackStack("SignUpBackStack")
                        .commit();
            }
        });

        btnNGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager1.beginTransaction().replace(R.id.SignUpFragmentContainer,SignUpNGODetails.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("SignUpBackStack")
                        .commit();
            }
        });





    }
}