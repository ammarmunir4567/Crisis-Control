package com.example.testing.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;
import com.example.testing.activities.APIWebClient;
import com.example.testing.classes.LiveFeedData;

import java.util.ArrayList;

public class LiveFeedRVAdapter extends RecyclerView.Adapter<LiveFeedRVAdapter.ViewHolder> {
    private ArrayList<LiveFeedData> liveFeedArray = new ArrayList<>();
    private Context mContext;

    public LiveFeedRVAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLiveFeedArray(ArrayList<LiveFeedData> liveFeedArray) {
        this.liveFeedArray = liveFeedArray;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_feed_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.url.setText(liveFeedArray.get(position).getUrltxt());
    holder.info.setText(liveFeedArray.get(position).getInfo());

        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext, APIWebClient.class);
                intent.putExtra("url",liveFeedArray.get(holder.getAdapterPosition()).getUrl().toString());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return liveFeedArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView url;
        private TextView info;
        private Layout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            url= itemView.findViewById(R.id.txturl);
            info= itemView.findViewById(R.id.txtinfo);



        }
    }
}
