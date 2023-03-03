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
import com.example.testing.activities.SignUpActivity;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.User;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpUserDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpUserDetails extends Fragment {



    private static final int user_type=-1;


    private int mUser_type;
    private int cUser_type;


    public SignUpUserDetails() {
        // Required empty public constructor
    }

  //TODO:Factory method being implemented here
    public static SignUpUserDetails newInstance(int user_type) {
        SignUpUserDetails fragment = new SignUpUserDetails();
        Bundle args = new Bundle();
       args.putInt("User_Type",user_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser_type = getArguments().getInt("User_Type",user_type);
            cUser_type=0;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sign_up_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etUserName,etPassword,etPassword2,etName,etAddress;
        TextView txtAccTpye;
        DatePicker DOB;
        Button btnSubmit;
        CCDatabase db=CCDatabase.getInstance(getActivity());
        etUserName = view.findViewById(R.id.SignUpUser_UserName);
        etPassword = view.findViewById(R.id.SignUpUser_Password);
        etPassword2 = view.findViewById(R.id.SignUpUser_Password2);
        etName = view.findViewById(R.id.SignUpUser_Name);
        etAddress = view.findViewById(R.id.SignUpUser_Address);
        txtAccTpye = view.findViewById(R.id.txtSignUpUserAccType);
        DOB = view.findViewById(R.id.SignUpUser_DOB);
        btnSubmit=view.findViewById(R.id.btnSignUpUserSubmit);

        if(mUser_type== SignUpActivity.SIGNUP_DONOR){
            txtAccTpye.setText("Create Your Donor Account!");
            cUser_type=User.USER_TYPE_DONOR;
        }
        else if(mUser_type==SignUpActivity.SIGNUP_VOLUNTEER){
            txtAccTpye.setText("Create Your Volunteer Account!");
            cUser_type=User.USER_TYPE_VOLUNTEER;
        }
        else if (mUser_type==SignUpActivity.SIGNUP_AFFECTEE){
            txtAccTpye.setText("Create Your Affectee Account");
            cUser_type=User.USER_TYPE_AFFECTEE;
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUserName.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals("") || etName.getText().toString().equals("") || etAddress.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar cal = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal.set(DOB.getYear(),DOB.getMonth(),DOB.getDayOfMonth());
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etUserName.getText().toString()).matches()){
                            Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                        } else if (db.accountDao().getAccByUserName(etUserName.getText().toString()) != null && cUser_type==db.userDao().getUserByID(db.accountDao().getAccByUserName(etUserName.getText().toString()).getAccID()).getUserType()){
                            Toast.makeText(getActivity(), "Email already in use", Toast.LENGTH_SHORT).show();
                        } else if (etPassword.getText().toString().length()<8){
                            Toast.makeText(getActivity(),"Password should have atleast 8 characters", Toast.LENGTH_SHORT).show();
                        } else if (!etPassword.getText().toString().equals(etPassword2.getText().toString())) {
                            Toast.makeText(getActivity(), "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        } else if(db.userDao().getUserPass(etUserName.getText().toString()).contains(etPassword.getText().toString())){
                            Toast.makeText(getActivity(), "Can't have same email & password for different types of accounts", Toast.LENGTH_SHORT).show();
                        } else if(cal.getTimeInMillis()>Calendar.getInstance().getTimeInMillis()){
                            Toast.makeText(getActivity(), "Please enter a valid Date", Toast.LENGTH_SHORT).show();
                        }
                        else if (cal2.get(Calendar.YEAR)-DOB.getYear()<17){
                            Toast.makeText(getActivity(), "You must be atleast 18 old to signup", Toast.LENGTH_SHORT).show();
                        }
                        else{
                             if(mUser_type== SignUpActivity.SIGNUP_DONOR){
                                User u = new User(db.accountDao().getNewAccountID(),etUserName.getText().toString(),etPassword.getText().toString(),etName.getText().toString(),cal.getTime(),etAddress.getText().toString(),User.USER_TYPE_DONOR);
                                db.userDao().insertUserAccount(u,u);
                            }
                            else if(mUser_type==SignUpActivity.SIGNUP_VOLUNTEER){
                                User u = new User(db.accountDao().getNewAccountID(),etUserName.getText().toString(),etPassword.getText().toString(),etName.getText().toString(),cal.getTime(),etAddress.getText().toString(),User.USER_TYPE_VOLUNTEER);
                                db.userDao().insertUserAccount(u,u);
                            }
                            else if(mUser_type==SignUpActivity.SIGNUP_AFFECTEE){
                                User u = new User(db.accountDao().getNewAccountID(),etUserName.getText().toString(),etPassword.getText().toString(),etName.getText().toString(),cal.getTime(),etAddress.getText().toString(),User.USER_TYPE_AFFECTEE);
                                db.userDao().insertUserAccount(u,u);
                            }
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Toast.makeText(getActivity(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }
                    }
                }
        });
    }
}