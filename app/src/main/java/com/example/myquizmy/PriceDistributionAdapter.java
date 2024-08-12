package com.example.myquizmy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PriceDistributionAdapter extends RecyclerView.Adapter<PriceDistributionAdapter.ViewHolder> {

    private ArrayList<ModelRank> modelRankList;

    public PriceDistributionAdapter(ArrayList<ModelRank> modelRankList) {
        this.modelRankList = modelRankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.max_fill, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRank modelRank = modelRankList.get(position);
        holder.rankTextView.setText(modelRank.getRank());
        holder.priceTextView.setText(modelRank.getPrice());
    }

    @Override
    public int getItemCount() {
        return modelRankList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.RankName);
            priceTextView = itemView.findViewById(R.id.Price);
        }
    }
}
