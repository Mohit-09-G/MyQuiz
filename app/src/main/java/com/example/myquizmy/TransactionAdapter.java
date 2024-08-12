package com.example.myquizmy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_items, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.textViewType.setText(transaction.getType());
        holder.textViewAmount.setText(transaction.getAmount());
        holder.textViewDate.setText(transaction.getDate());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewType;
        TextView textViewAmount;
        TextView textViewDate;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.transaction_type);
            textViewAmount = itemView.findViewById(R.id.transaction_amount);
            textViewDate = itemView.findViewById(R.id.transaction_date);
        }
    }
}
