package com.example.myquizmy.Wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizmy.R;

import java.util.ArrayList;

public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.AmountViewHolder> {

    private ArrayList<AmountItem> amountItems;
    private OnAmountClickListener onAmountClickListener;
    Context context;

    public interface OnAmountClickListener {
        void onAmountClick(String amount);
    }

    public AmountAdapter(Context context,ArrayList<AmountItem> amountItems, OnAmountClickListener onAmountClickListener) {
        this.context=context;
        this.amountItems = amountItems;
        this.onAmountClickListener = onAmountClickListener;
    }

    @NonNull
    @Override
    public AmountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amount, parent, false);
        return new AmountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmountViewHolder holder, int position) {
        AmountItem amountItem = amountItems.get(position);
        holder.bind(amountItem, onAmountClickListener);
    }

    @Override
    public int getItemCount() {
        return amountItems.size();
    }

    public static class AmountViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAmount;

        public AmountViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
        }

        public void bind(final AmountItem amountItem, final OnAmountClickListener onAmountClickListener) {
            textViewAmount.setText("RS."+String.valueOf(amountItem.getAmount()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAmountClickListener.onAmountClick(String.valueOf(amountItem.getAmount()));
                }
            });
        }
    }
}
