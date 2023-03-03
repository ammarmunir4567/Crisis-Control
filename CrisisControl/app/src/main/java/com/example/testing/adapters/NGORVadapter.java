package com.example.testing.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.activities.NGOProfileActivity;
import com.example.testing.classes.NGO;

import java.util.ArrayList;

public class NGORVadapter extends RecyclerView.Adapter<NGORVadapter.ViewHolder>  {
    private ArrayList<NGO> ngoArrayList=new ArrayList<>();
    private Context mContext;

    public NGORVadapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<NGO> getNgoArrayList() {
        return ngoArrayList;
    }

    public void setNgoArrayList(ArrayList<NGO> ngoArrayList) {
        this.ngoArrayList = ngoArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngo_card_item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNGOName.setText(ngoArrayList.get(holder.getAdapterPosition()).getName());
        holder.txtNGODesc.setText(ngoArrayList.get(holder.getAdapterPosition()).getDesc());
        holder.txtNGOaddress.setText(ngoArrayList.get(holder.getAdapterPosition()).getAddress());
        holder.NGOratingBar.setRating((float)ngoArrayList.get(holder.getAdapterPosition()).getRatings());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(mContext, NGOProfileActivity.class);
               intent.putExtra("Profile",ngoArrayList.get(holder.getAdapterPosition()));
               mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ngoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNGOName,txtNGODesc,txtNGOaddress;
        private RatingBar NGOratingBar;
        private View parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNGOName = itemView.findViewById(R.id.NGOName);
            txtNGODesc = itemView.findViewById(R.id.NGO_desc);
            txtNGOaddress = itemView.findViewById(R.id.NGOAddress);
            NGOratingBar = itemView.findViewById(R.id.ratingBar);
            parent = itemView.findViewById(R.id.NGOparentCard);

        }

    }
}
