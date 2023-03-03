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
import com.example.testing.classes.Report;
import com.example.testing.classes.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReportRVAdapter extends RecyclerView.Adapter<ReportRVAdapter.ViewHolder> {
    private ArrayList<Report> reports;
    private CCDatabase db;
    private Context mContext;


public ReportRVAdapter(Context mContext) {
        reports=new ArrayList<>();
        this.mContext = mContext;
        db=CCDatabase.getInstance(mContext);
}


    public void setReports(ArrayList<Report> r){
        for(int i=0;i<r.size();i++){
            if(db.NGODao().getNGOByID(r.get(i).getNGOID())!=null){
                reports.add(r.get(i));
            }
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_report_list_item, parent, false);
        return new ReportRVAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    if(db.NGODao().getNGOByID(reports.get(holder.getAdapterPosition()).getNGOID()).isStatusActive()) {
        String info = "Name : " + db.NGODao().getNGOByID(reports.get(holder.getAdapterPosition()).getNGOID()).getName();
        holder.reportNGOName.setText(info);
        info = "ID : " + String.valueOf(reports.get(holder.getAdapterPosition()).getNGOID());
        holder.reportNGOID.setText(info);
        info = "Ratings : " + String.valueOf(db.NGODao().getNGOByID(reports.get(holder.getAdapterPosition()).getNGOID()).getRatings());
        holder.reportNGORatings.setText(info);
        info = "Name : " + db.userDao().getUserByID(reports.get(holder.getAdapterPosition()).getUserID()).getName();
        holder.reportUserName.setText(info);
        info = "ID : " + String.valueOf(reports.get(holder.getAdapterPosition()).getUserID());
        holder.reportUserID.setText(info);
        info = "Type : ";
        int userType = db.userDao().getUserByID(reports.get(holder.getAdapterPosition()).getUserID()).getUserType();
        if (userType == User.USER_TYPE_DONOR) {
            info += "Donor";
        } else if (userType == User.USER_TYPE_VOLUNTEER) {
            info += "Volunteer";
        } else if (userType == User.USER_TYPE_AFFECTEE) {
            info += "Affectee";
        }
        holder.reportUserAccType.setText(info);
        holder.reportDesc.setText(reports.get(holder.getAdapterPosition()).getDescription());
        holder.reportManaged.setChecked(reports.get(holder.getAdapterPosition()).isReportManaged());
        holder.reportManaged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                if (b) {//reports.get(holder.getAdapterPosition()).isReportManaged()
                    builder.setTitle("Are you sure you want to mark as Managed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Report r = db.reportDao().getReportByID(reports.get(holder.getAdapterPosition()).getReportID());
                            r.setReportManaged(true);
                            db.reportDao().updateReport(r);
                           reports.remove(holder.getAdapterPosition());
                           notifyItemRemoved(holder.getAdapterPosition());



                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                } else {
                    builder.setTitle("Are you sure you want to unmark as Managed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Report r = new Report(db.reportDao().getReportByID(reports.get(holder.getAdapterPosition()).getReportID()));
                            r.setReportManaged(false);
                            db.reportDao().updateReport(r);
                            reports.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }
                builder.show();
            }
        });
    }
}

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reportNGOName,reportNGOID,reportNGORatings;
        private TextView reportUserID,reportUserName,reportUserAccType;
        private TextView reportDesc;
        private CheckBox reportManaged;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reportNGOName=itemView.findViewById(R.id.ReportNGOName);
            reportNGOID=itemView.findViewById(R.id.ReportNGOID);
            reportNGORatings=itemView.findViewById(R.id.ReportNGORatings);
            reportUserID=itemView.findViewById(R.id.ReportUserID);
            reportUserName=itemView.findViewById(R.id.ReportUserName);
            reportUserAccType=itemView.findViewById(R.id.ReportAccountType);
            reportManaged=itemView.findViewById(R.id.ReportManagedCheckBox);
            reportDesc=itemView.findViewById(R.id.reportDescription);
        }
    }


}
