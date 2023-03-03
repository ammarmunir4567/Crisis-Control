package com.example.testing.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.activities.LoginActivity;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.NGO;

public class SignUpNGODetails extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SignUpNGODetails() {
        // Required empty public constructor
    }


    public static SignUpNGODetails newInstance(String param1, String param2) {
        SignUpNGODetails fragment = new SignUpNGODetails();
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
        return inflater.inflate(R.layout.fragment_sign_up_n_g_o_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etUserName, etPassword, etPassword2, etName, etAddress, etDescription,edBankNum,edBankName;
        TextView txtAccTpye;
        Button btnSubmit;
        CCDatabase db = CCDatabase.getInstance(getActivity());
        etUserName = view.findViewById(R.id.SignUpNGO_UserName);
        etPassword = view.findViewById(R.id.SignUpNGO_Password);
        etPassword2 = view.findViewById(R.id.SignUpNGO_Password2);
        etName = view.findViewById(R.id.SignUpNGO_Name);
        etDescription = view.findViewById(R.id.SignUpNGO_Desc);
        etAddress = view.findViewById(R.id.SignUpNGO_Address);
        txtAccTpye = view.findViewById(R.id.txtSignUpUserAccType);
        btnSubmit = view.findViewById(R.id.btnSignUpNGOSubmit);
        edBankName=view.findViewById(R.id.edBankName);
        edBankNum=view.findViewById(R.id.edBankAccountNumber);

        txtAccTpye.setText("Create an Account for your NGO!");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUserName.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals("") || etName.getText().toString().equals("") || etAddress.getText().toString().equals("") || edBankName.getText().toString().equals("") ||edBankNum.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.accountDao().getAccByUserName(etUserName.getText().toString()) != null) {
                        Toast.makeText(getActivity(), "UserName already taken", Toast.LENGTH_SHORT).show();
                    } else {
                        if (db.NGODao().getNGOByName(etName.getText().toString()) != null) {

                            Toast.makeText(getActivity(), "An NGO with this name already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            if(etPassword.getText().toString().length()<8){
                                Toast.makeText(getActivity(), "Password should have atleast 8 characters", Toast.LENGTH_SHORT).show();
                            }
                            else if (!etPassword.getText().toString().equals(etPassword2.getText().toString())) {
                                Toast.makeText(getActivity(), "Passwords do not Match", Toast.LENGTH_SHORT).show();
                            }
                            else if(edBankNum.getText().toString().length()>19 || edBankNum.getText().toString().length()<7){
                                Toast.makeText(getActivity(), "Please enter a valid bank account number", Toast.LENGTH_SHORT).show();
                            } else {
                                NGO n = new NGO(db.accountDao().getNewAccountID(), etUserName.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etDescription.getText().toString(), etAddress.getText().toString(), true, true, 0, 0,edBankNum.getText().toString(),edBankName.getText().toString());
                                db.NGODao().insertNGOAccount(n, n);
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(getActivity(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }

                    }

                }
            }
        });

    }
}