package com.example.testing.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.CurrentAccount;
import com.example.testing.classes.MedicalRequest;

import java.util.ArrayList;

public class MedicalRequestRVAdapter extends RecyclerView.Adapter<MedicalRequestRVAdapter.ViewHolder> {
    private ArrayList<MedicalRequest> medicalRequests;
    private CCDatabase db;
    private Context mContext;
    private int type;

    public MedicalRequestRVAdapter(Context mContext) {
        this.mContext = mContext;
        medicalRequests=new ArrayList<>();
        db=CCDatabase.getInstance(mContext);
    }

    public void setAllMedicalRequests(ArrayList<MedicalRequest> medicalRequests) {
        this.medicalRequests = medicalRequests;
        type=0;
    }

    public void setAcceptedMedicalRequests(ArrayList<MedicalRequest> medicalRequests) {
        this.medicalRequests = medicalRequests;
        type=1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_request_list_item, parent, false);
        return new MedicalRequestRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.city.setText(medicalRequests.get(holder.getAdapterPosition()).getCity());
        holder.address.setText(medicalRequests.get(holder.getAdapterPosition()).getAddress());
        holder.username.setText(db.userDao().getUserByID(medicalRequests.get(holder.getAdapterPosition()).getRequestedUserID()).getName());
        holder.desc.setText(medicalRequests.get(holder.getAdapterPosition()).getDescription());
        holder.injured.setText(String.valueOf(medicalRequests.get(holder.getAdapterPosition()).getInjured()));
        holder.requestAccepted.setChecked(medicalRequests.get(holder.getAdapterPosition()).isAccepted());
        if(type==0){
            holder.requestAccepted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    MedicalRequest mr = new MedicalRequest(medicalRequests.get(holder.getAdapterPosition()));
                    mr.setAccepted(true);
                    mr.setAcceptedNGOID(CurrentAccount.getCurrentAccID());
                    db.medicalRequestDao().updateMedicalRequest(mr);
                    medicalRequests.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
        else {
            holder.requestHandled.setVisibility(View.VISIBLE);
            holder.requestAccepted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to mark request as unaccepted?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MedicalRequest mr = new MedicalRequest(medicalRequests.get(holder.getAdapterPosition()));
                            mr.setAccepted(false);
                            mr.setAcceptedNGOID(0);
                            db.medicalRequestDao().updateMedicalRequest(mr);
                            medicalRequests.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            compoundButton.setChecked(true);
                        }
                    });
                    builder.show();
                }
                }
            });

            holder.requestHandled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to marked as handled? Once marked, it can't be changed.");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MedicalRequest mr = new MedicalRequest(medicalRequests.get(holder.getAdapterPosition()));
                            db.medicalRequestDao().deleteMedicalRequest(mr);
                            medicalRequests.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            compoundButton.setChecked(false);

                        }
                    });
                    builder.show();
                }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return medicalRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city,username,address,injured,desc;
        private CheckBox requestAccepted,requestHandled;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city=itemView.findViewById(R.id.mrCity);
            username=itemView.findViewById(R.id.mrAffeccteeName);
            address=itemView.findViewById(R.id.mrAddress);
            injured=itemView.findViewById(R.id.mrInjured);
            desc=itemView.findViewById(R.id.mrDescription);
            requestAccepted=itemView.findViewById(R.id.mrAccepted);
            requestHandled=itemView.findViewById(R.id.mrHandled);

        }
    }

}
