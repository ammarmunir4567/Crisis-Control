package com.example.testing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.classes.AppliedVolunteer;
import com.example.testing.classes.CCDatabase;

import java.util.ArrayList;

public class AppliedVolunteerRVAdapter extends RecyclerView.Adapter<AppliedVolunteerRVAdapter.ViewHolder> {

    private Context mContext;
    private CCDatabase db;
    private ArrayList<AppliedVolunteer> volunteers;

    public AppliedVolunteerRVAdapter(Context mContext) {
        this.mContext = mContext;
        volunteers=new ArrayList<>();
        db=CCDatabase.getInstance(mContext);
    }

    public void setVolunteers(ArrayList<AppliedVolunteer> volunteers) {
        this.volunteers = volunteers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applied_volunteer_list_item, parent, false);
        return new AppliedVolunteerRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtVolunteerName.setText(db.userDao().getUserByID(volunteers.get(holder.getAdapterPosition()).getUserID()).getName());
        holder.txtVolunteerCNIC.setText(volunteers.get(holder.getAdapterPosition()).getCNIC());
        holder.txtVolunteerPhone.setText(volunteers.get(holder.getAdapterPosition()).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtVolunteerName,txtVolunteerCNIC,txtVolunteerPhone;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txtVolunteerCNIC=itemView.findViewById(R.id.txtVolunteerCNIC);
           txtVolunteerName=itemView.findViewById(R.id.txtVolunteerName);
           txtVolunteerPhone=itemView.findViewById(R.id.txtVolunteerPhoneNum);
        }
    }
}
