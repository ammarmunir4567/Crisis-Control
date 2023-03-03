package com.example.testing.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.classes.CCDatabase;
import com.example.testing.classes.Donation;

import java.util.ArrayList;

public class DonationPendingRVAdapter  extends RecyclerView.Adapter<DonationPendingRVAdapter.ViewHolder>{
    private ArrayList<Donation> donations;
    private CCDatabase db;
    private Context mContext;

    public DonationPendingRVAdapter(Context mContext) {
        this.donations = new ArrayList<>();
        db=CCDatabase.getInstance(mContext);
        this.mContext = mContext;
    }

    public void setDonations(ArrayList<Donation> donations) {
        this.donations = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_pending_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.amount.setText(String.valueOf(donations.get(holder.getAdapterPosition()).getAmount()));
    holder.bankName.setText(db.NGODao().getNGOByProjID(donations.get(holder.getAdapterPosition()).getProjectID()).getBankName());
    holder.accNum.setText(db.NGODao().getNGOByProjID(donations.get(holder.getAdapterPosition()).getProjectID()).getBankAccountNum());
    holder.completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to mark as Complete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Donation d = new Donation(donations.get(holder.getAdapterPosition()));
                        d.setStatus(Donation.STATUS_COMPLETED);
                        db.donationDao().updateDonation(d);
                        donations.remove(holder.getAdapterPosition());
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

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView bankName, accNum, amount;
    private CheckBox completed;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        bankName = itemView.findViewById(R.id.txtBankName);
        accNum = itemView.findViewById(R.id.txtAccNum);
        amount = itemView.findViewById(R.id.txtAmountPending);
        completed = itemView.findViewById(R.id.cbCompleted);
    }
}
}
