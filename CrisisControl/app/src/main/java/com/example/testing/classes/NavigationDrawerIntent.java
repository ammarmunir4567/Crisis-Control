package com.example.testing.classes;





import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testing.R;
import com.example.testing.activities.BalanceSheetActivity;
import com.example.testing.activities.DonationListActivity;
import com.example.testing.activities.LiveFeedActivity;
import com.example.testing.activities.LoginActivity;
import com.example.testing.activities.MedicalRequestSubmissionFormActivity;
import com.example.testing.activities.NGOCampLocationMap;
import com.example.testing.activities.NGOListActivity;
import com.example.testing.activities.ProfileActivity;
import com.example.testing.activities.VolunteerListActivity;

public class NavigationDrawerIntent {
    public NavigationDrawerIntent() {
    }

    public Intent getIntent(Context context, MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {

            case R.id.liveFeedNav:
                intent = new Intent(context, LiveFeedActivity.class);
               return intent;

            case R.id.donationsNav:
                if (CurrentAccount.getAccType() == User.USER_TYPE_DONOR) {
                    intent = new Intent(context, DonationListActivity.class);
                    return intent;
                } else {
                    Toast.makeText(context, "You have to login into a Donor Account", Toast.LENGTH_SHORT).show();
                }break;
            case R.id.volunteerNav:
                if (CurrentAccount.getAccType() == User.USER_TYPE_VOLUNTEER) {
                    intent = new Intent(context, VolunteerListActivity.class);
                    return intent;
                } else {
                    Toast.makeText(context, "You have to login into a Volunteer Account", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.NGOsNav:
                if(CurrentAccount.getAccType()!=CurrentAccount.GUEST_TYPE) {
                    intent = new Intent(context, NGOListActivity.class);
                    return intent;
                }
                else{
                    Toast.makeText(context, "You have to be Logged in", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.reliefCampNav:
                if(CurrentAccount.getAccType()!=CurrentAccount.GUEST_TYPE) {
                    intent = new Intent(context, NGOCampLocationMap.class);
                    return intent;
                }
                else{
                    Toast.makeText(context, "You have to be Logged in", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.accountSettings:
                if(CurrentAccount.getAccType()!=CurrentAccount.GUEST_TYPE) {
                    intent = new Intent(context, ProfileActivity.class);
                    return intent;
                }
                else{
                    Toast.makeText(context, "You have to be Logged in", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logOut:
                if(CurrentAccount.getAccType()!=CurrentAccount.GUEST_TYPE) {
                    intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("Logout",1);
                    return intent;
                }
                else{
                    Toast.makeText(context, "You have to be Logged in", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.viewBalanceSheet:
                if(CurrentAccount.getAccType()==User.USER_TYPE_DONOR){
                    intent = new Intent(context, BalanceSheetActivity.class);
                    return intent;
                }
                else{
                    Toast.makeText(context, "You have to login into a Donor Account", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.requestMedicalHelp:
                if(CurrentAccount.getAccType()==User.USER_TYPE_AFFECTEE){
                    intent = new Intent(context, MedicalRequestSubmissionFormActivity.class);
                    return intent;
                }else{
                    Toast.makeText(context, "You have to login to an Affectee Account", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return null;

        }
        return null;
    }
}





