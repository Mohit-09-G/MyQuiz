package com.example.myquizmy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeQuizAdapter extends RecyclerView.Adapter<HomeQuizAdapter.HomeQuizViewHolder> {

    private List<HomeItems> quizList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class HomeQuizViewHolder extends RecyclerView.ViewHolder {
        public ImageView liveImage;
        public TextView txt1, dateTxt, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9;
        public Button viewMoreButton;

        public HomeQuizViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            liveImage = itemView.findViewById(R.id.liveimage);
            txt1 = itemView.findViewById(R.id.txt1);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
            txt5 = itemView.findViewById(R.id.txt5);
            txt6 = itemView.findViewById(R.id.txt6);
            txt7 = itemView.findViewById(R.id.txt7);
            txt8 = itemView.findViewById(R.id.txt8);
            txt9 = itemView.findViewById(R.id.txt9);
            viewMoreButton = itemView.findViewById(R.id.viewMoreButton);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            };

            itemView.setOnClickListener(clickListener);
            viewMoreButton.setOnClickListener(clickListener);
        }
    }

    public HomeQuizAdapter(List<HomeItems> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public HomeQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
        return new HomeQuizViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeQuizViewHolder holder, int position) {
        HomeItems currentItem = quizList.get(position);
        holder.liveImage.setImageResource(currentItem.getImageResource());
        holder.txt1.setText(currentItem.getQuizTitle());
        holder.dateTxt.setText(currentItem.getDateTime());
        holder.txt2.setText("Joining fees");
        holder.txt3.setText("Rs." + currentItem.getJoiningFee() + "/-");
        holder.txt4.setText("Max Entry");
        holder.txt5.setText(currentItem.getMaxEntry() + " People");
        holder.txt6.setText("Available Spots");
        holder.txt7.setText(String.valueOf(currentItem.getAvailableSpots()));
        holder.txt8.setText("Prize Pool");
        holder.txt9.setText("Rs." + currentItem.getPrizePool() + "/-");
    }

    @Override
    public int getItemCount() {
        return quizList != null ? quizList.size() : 0;
    }
}
