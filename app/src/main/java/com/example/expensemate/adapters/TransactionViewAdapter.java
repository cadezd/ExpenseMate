package com.example.expensemate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemate.R;
import com.example.expensemate.databse.entities.UserTransaction;

import java.text.SimpleDateFormat;

public class TransactionViewAdapter extends ListAdapter<UserTransaction, TransactionViewAdapter.TransactionViewHolder> {

    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // TODO: undestand the code below
    public TransactionViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<UserTransaction> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserTransaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserTransaction oldItem, @NonNull UserTransaction newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserTransaction oldItem, @NonNull UserTransaction newItem) {
            return oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getAmount() == newItem.getAmount() &&
                    oldItem.getDate().equals(newItem.getDate());
        }
    };

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
        return new TransactionViewHolder(item);
    }

    // creating a method to get course modal for a specific position.
    public UserTransaction getTransactionAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        UserTransaction transaction = getItem(position);

        // Displaying transaction description
        holder.txtVDescription.setText(transaction.getDescription());

        // Displaying transaction date in format dd.MM.yyyy
        SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy");
        String stringDate = DateFor.format(transaction.getDate());
        holder.txtVDate.setText(stringDate);

        // Displaying transaction amount and setting color based on the amount
        int color = (transaction.getAmount() > 0) ? R.color.green : R.color.red;
        holder.txtVAmount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), color));
        holder.txtVAmount.setText(String.valueOf(transaction.getAmount()) + "€");
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView txtVDescription, txtVDate, txtVAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVDescription = itemView.findViewById(R.id.txtVDescription);
            txtVDate = itemView.findViewById(R.id.txtVDate);
            txtVAmount = itemView.findViewById(R.id.txtVAmount);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getTransactionAt(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserTransaction model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
