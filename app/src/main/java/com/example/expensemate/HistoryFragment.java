package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemate.adapters.TransactionViewAdapter;
import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.model.UserTransactionModel;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    // DECLARE COMPONENTS
    RadioGroup radioGroup;
    RecyclerView rvTransactionHistory;

    AutoCompleteTextView actxtVTransactionType;

    // DECLARE VARIABLES
    UserTransactionModel transactionModel;
    User user;
    TransactionViewAdapter adapter;
    ArrayList<String> transactionTypes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // INITIALIZE COMPONENTS
        radioGroup = view.findViewById(R.id.radioGroup);
        rvTransactionHistory = view.findViewById(R.id.rvTransactionHistory);
        actxtVTransactionType = view.findViewById(R.id.actxtVTransactionType);

        // INITIALIZE VARIABLES
        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra(Constants.USER_TAG);
        transactionModel = new UserTransactionModel(getActivity().getApplication(), user.getId());

        // TODO: change this to a new adapter (when it is created)
        adapter = new TransactionViewAdapter();

        rvTransactionHistory.setAdapter(adapter);
        // setting layout manager to our adapter class.
        rvTransactionHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransactionHistory.setHasFixedSize(true);

        // Adding transaction types to the array list
        transactionTypes.add("All");
        transactionTypes.add("Income");
        transactionTypes.add("Expense");

        // Setting adapter to the autocomplete text view
        actxtVTransactionType.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, transactionTypes));
        actxtVTransactionType.setText("All", false);

        // Setting default value
        transactionModel.getTodaysUserTransactions().observe(getViewLifecycleOwner(), userTransactions -> {
            adapter.submitList(userTransactions);
        });

        // ITEM TOUCH HELPER
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                transactionModel.deleteTransaction(adapter.getTransactionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvTransactionHistory);


        // CLICK LISTENERS
        // Change the transactions based on the selected selected time period
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    String transactionType = actxtVTransactionType.getText().toString().trim();
                    setTransactions(transactionType);
                }
        );

        // Change the transaction type based on the selected transaction type
        actxtVTransactionType.setOnItemClickListener((parent, view1, position, id) -> {
            String transactionType = actxtVTransactionType.getText().toString().trim();
            setTransactions(transactionType);
        });

        return view;
    }

    private void setTransactions(String transactionType) {
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbDay) {
            transactionModel.getTodaysUserTransactions().observe(getViewLifecycleOwner(), userTransactions -> {
                // Filter the transactions by the transaction type
                userTransactions = userTransactions.stream().filter(userTransaction -> {
                    return filterTransactionType(transactionType, userTransaction.getAmount());
                }).collect(Collectors.toList());

                adapter.submitList(userTransactions);
            });
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbWeek) {
            transactionModel.getThisWeeksUserTransactions().observe(getViewLifecycleOwner(), userTransactions -> {
                // Filter the transactions by the transaction type
                userTransactions = userTransactions.stream().filter(userTransaction -> {
                    return filterTransactionType(transactionType, userTransaction.getAmount());
                }).collect(Collectors.toList());

                adapter.submitList(userTransactions);
            });
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbMonth) {
            transactionModel.getThisMonthsUserTransactions().observe(getViewLifecycleOwner(), userTransactions -> {
                // Filter the transactions by the transaction type
                userTransactions = userTransactions.stream().filter(userTransaction -> {
                    return filterTransactionType(transactionType, userTransaction.getAmount());
                }).collect(Collectors.toList());

                adapter.submitList(userTransactions);
            });
        } else {
            transactionModel.getUserTransactions().observe(getViewLifecycleOwner(), userTransactions -> {
                // Filter the transactions by the transaction type
                userTransactions = userTransactions.stream().filter(userTransaction -> {
                    return filterTransactionType(transactionType, userTransaction.getAmount());
                }).collect(Collectors.toList());

                adapter.submitList(userTransactions);
            });
        }
    }

    private boolean filterTransactionType(String transactionType, double amount) {
        if (transactionType.equals("All")) {
            return true;
        } else if (transactionType.equals("Income")) {
            return amount >= 0;
        } else {
            return amount < 0;
        }
    }
}