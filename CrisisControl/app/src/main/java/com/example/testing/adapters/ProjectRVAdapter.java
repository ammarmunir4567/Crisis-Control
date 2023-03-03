package com.example.testing.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.activities.DonationPostActivity;
import com.example.testing.activities.VolunteerPostActivity;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.NGO;
import com.example.testing.classes.Project;

import java.util.ArrayList;

public class ProjectRVAdapter extends RecyclerView.Adapter<ProjectRVAdapter.ViewHolder> {
    private ArrayList<Project> projects = new ArrayList<>();
    private int type=-1;
    private Context mContext;
    private CCDatabase db;

    public ProjectRVAdapter(Context mContext) {
            this.mContext = mContext;
        db=CCDatabase.getInstance(mContext);
        }

        public void setDonationProjects(ArrayList<Project> projectList) {
            type=0;
            projects = new ArrayList<>();
           for (int i=0;i< projectList.size();i++){
               if(projectList.get(i).getDonationAsked()!=0 &&  db.NGODao().getNGOByID(projectList.get(i).getNgoID())!=null){

                   if(projectList.get(i).getDonationAsked()-db.donationDao().getProjectTotalDonatedAmount(projectList.get(i).getProjectID())>100) {
                       this.projects.add(projectList.get(i));
                   }
               }
           }

            notifyDataSetChanged();

        }

    public void setVolunteerProjects(ArrayList<Project> projectList) {
        type=1;
        projects = new ArrayList<>();
        for (int i=0;i< projectList.size();i++){
            if(projectList.get(i).getVolunteersNeeded()!=0 && db.NGODao().getNGOByID(projectList.get(i).getNgoID())!=null){
                if(projectList.get(i).getVolunteersNeeded()-db.volunteersDao().getCountProjectVolunteers(projectList.get(i).getProjectID())!=0) {
                    this.projects.add(projectList.get(i));
                }
            }
        }

        notifyDataSetChanged();

    }

    public void setNGOProjects(ArrayList<Project> projectList) {
        type=2;
       projects=projectList;
        notifyDataSetChanged();
    }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_card_item_duplicate, parent, false);
            return new ViewHolder(view);

        }



       @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type==0) {
            holder.ParentVolunteer.setVisibility(View.GONE);
            holder.txtDonationDesc.setText(projects.get(holder.getAdapterPosition()).getDescription());
            holder.txtDonationNGO.setText(db.NGODao().getNGOByID(projects.get(holder.getAdapterPosition()).getNgoID()).getName());

            holder.txtDonationAmount.setText(String.valueOf(projects.get(holder.getAdapterPosition()).getDonationAsked()) + "/-");
            holder.txtDonationTitle.setText(projects.get(holder.getAdapterPosition()).getTitle());
            holder.ParentDonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, DonationPostActivity.class);
                    intent.putExtra("Project", projects.get(holder.getAdapterPosition()));
                    mContext.startActivity(intent);

                }
            });
        }
        else if (type==1){
            holder.ParentVolunteer.setVisibility(View.GONE);
            holder.txtDonationDesc.setText(projects.get(holder.getAdapterPosition()).getDescription());
            holder.txtDonationNGO.setText(db.NGODao().getNGOByID(projects.get(holder.getAdapterPosition()).getNgoID()).getName());
            holder.txtDonationAmount.setText(String.valueOf(projects.get(holder.getAdapterPosition()).getVolunteersNeeded()));
            holder.txtDonationTitle.setText(projects.get(holder.getAdapterPosition()).getTitle());
            holder.ParentDonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, VolunteerPostActivity.class);
                    intent.putExtra("Project",projects.get(holder.getAdapterPosition()));
                    mContext.startActivity(intent);

                }
            });

        }
        else if(type==2){
            holder.ParentVolunteer.setVisibility(View.GONE);
            holder.ParentDonor.setVisibility(View.GONE);
            if(projects.get(holder.getAdapterPosition()).getDonationAsked()!=0 &&projects.get(holder.getAdapterPosition()).getDonationAsked()-db.donationDao().getProjectTotalDonatedAmount(projects.get(holder.getAdapterPosition()).getProjectID())>100){
                holder.ParentDonor.setVisibility(View.VISIBLE);
                if(projects.get(holder.getAdapterPosition()).getVolunteersNeeded()!=0 && (projects.get(holder.getAdapterPosition()).getVolunteersNeeded()-db.volunteersDao().getCountProjectVolunteers(projects.get(holder.getAdapterPosition()).getProjectID()))!=0 ){
                    holder.ParentVolunteer.setVisibility(View.VISIBLE);
                    holder.txtVolunteerDesc.setText(projects.get(holder.getAdapterPosition()).getDescription());
                    holder.txtVolunteerNGO.setText(db.NGODao().getNGOByID(projects.get(holder.getAdapterPosition()).getNgoID()).getName());

                    holder.txtVolunteerAmount.setText(String.valueOf(projects.get(holder.getAdapterPosition()).getVolunteersNeeded()));
                    holder.txtVolunteerTitle.setText(projects.get(holder.getAdapterPosition()).getTitle());
                    holder.ParentVolunteer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(mContext, VolunteerPostActivity.class);
                            intent.putExtra("Project", projects.get(holder.getAdapterPosition()));
                            mContext.startActivity(intent);

                        }
                    });


                }

                holder.txtDonationDesc.setText(projects.get(holder.getAdapterPosition()).getDescription());
                holder.txtDonationNGO.setText(db.NGODao().getNGOByID(projects.get(holder.getAdapterPosition()).getNgoID()).getName());

                holder.txtDonationAmount.setText(String.valueOf(projects.get(holder.getAdapterPosition()).getDonationAsked()) + "/-");
                holder.txtDonationTitle.setText(projects.get(holder.getAdapterPosition()).getTitle());
                holder.ParentDonor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, DonationPostActivity.class);
                        intent.putExtra("Project", projects.get(holder.getAdapterPosition()));
                        mContext.startActivity(intent);

                    }
                });
            }
            else if(projects.get(holder.getAdapterPosition()).getVolunteersNeeded()!=0 && (projects.get(holder.getAdapterPosition()).getVolunteersNeeded()-db.volunteersDao().getCountProjectVolunteers(projects.get(holder.getAdapterPosition()).getProjectID()))!=0 ){
                holder.ParentVolunteer.setVisibility(View.VISIBLE);
                holder.txtVolunteerDesc.setText(projects.get(holder.getAdapterPosition()).getDescription());
                holder.txtVolunteerNGO.setText(db.NGODao().getNGOByID(projects.get(holder.getAdapterPosition()).getNgoID()).getName());
                holder.txtVolunteerAmount.setText(String.valueOf(projects.get(holder.getAdapterPosition()).getVolunteersNeeded()));
                holder.txtVolunteerTitle.setText(projects.get(holder.getAdapterPosition()).getTitle());
                holder.ParentVolunteer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, VolunteerPostActivity.class);
                        intent.putExtra("Project",projects.get(holder.getAdapterPosition()));
                        mContext.startActivity(intent);

                    }
                });

            }
        }

        }

        @Override
        public int getItemCount() {
            return projects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtDonationAmount,txtDonationTitle,txtDonationNGO,txtDonationDesc;
            TextView txtVolunteerAmount,txtVolunteerTitle,txtVolunteerNGO,txtVolunteerDesc;
            View ParentDonor,ParentVolunteer;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDonationTitle= itemView.findViewById(R.id.donation_title_dup);
                txtDonationAmount= itemView.findViewById(R.id.donation_amount_dup);
                txtDonationNGO= itemView.findViewById(R.id.donation_ngo_dup);
                txtDonationDesc= itemView.findViewById(R.id.donation_desc_dup);
                txtVolunteerTitle= itemView.findViewById(R.id.volunteer_title_dup);
                txtVolunteerAmount= itemView.findViewById(R.id.volunteer_amount_dup);
                txtVolunteerNGO= itemView.findViewById(R.id.volunteer_ngo_dup);
                txtVolunteerDesc= itemView.findViewById(R.id.volunteer_desc_dup);
                ParentDonor=itemView.findViewById(R.id.DonorDupParentCard);
                ParentVolunteer=itemView.findViewById(R.id.VolunteerDupParentCard);
            }
        }


}
