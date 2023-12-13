package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemate.adapters.TransactionViewAdapter;
import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserTransaction;
import com.example.expensemate.model.UserTransactionModel;

import java.util.Date;


public class HomePageFragment extends Fragment {

    public HomePageFragment() {
        // Required empty public constructor
    }

    // DECLARING COMPONENTS
    TextView txtVGreeting, txtVFullName, txtVTotalBalance, txtVTotalIncome, txtVTotalExpense;
    ImageView imgVAddTransaction;
    RecyclerView rvTransactionHistory;

    // DECLARING VARIABLES
    UserTransactionModel transactionModel;
    User user;
    TransactionViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Get user from intent
        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra(Constants.USER_TAG);

        // Creating user model
        transactionModel = new UserTransactionModel(getActivity().getApplication(), user.getId());


        // Creating transaction view adapter
        adapter = new TransactionViewAdapter();


        // INITIALIZING COMPONENTS
        imgVAddTransaction = view.findViewById(R.id.imgVAddTransaction);
        txtVGreeting = view.findViewById(R.id.txtVGreeting);
        txtVFullName = view.findViewById(R.id.txtVFullName);
        txtVTotalBalance = view.findViewById(R.id.txtVTotalBalance);
        txtVTotalIncome = view.findViewById(R.id.txtVTotalIncome);
        txtVTotalExpense = view.findViewById(R.id.txtVTotalExpense);
        rvTransactionHistory = view.findViewById(R.id.rvTransactionHistory);

        // setting layout manager to our adapter class.
        rvTransactionHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransactionHistory.setHasFixedSize(true);

        // SETTING VALUES TO COMPONENTS

        // Setting user name
        txtVFullName.setText(user.getFullName());

        // Setting user balance and income and expense
        transactionModel.getUserBalance().observe(getViewLifecycleOwner(), integer -> {
            int totalBalance = (integer == null) ? 0 : integer;
            txtVTotalBalance.setText(totalBalance + "€");
        });
        transactionModel.getUserIncome().observe(getViewLifecycleOwner(), integer -> {
            int totalIncome = (integer == null) ? 0 : integer;
            txtVTotalIncome.setText(totalIncome + "€");
        });
        transactionModel.getUserExpense().observe(getViewLifecycleOwner(), integer -> {
            int totalExpense = (integer == null) ? 0 : integer;
            txtVTotalExpense.setText(totalExpense + "€");
        });

        // Setting greeting
        if (new Date().getHours() < 12)
            txtVGreeting.setText("Good morning");
        else if (new Date().getHours() < 18)
            txtVGreeting.setText("Good afternoon");
        else
            txtVGreeting.setText("Good evening");

        rvTransactionHistory.setAdapter(adapter);
        transactionModel.getTodaysUserTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.submitList(transactions);
        });
        // Setting on item click listener to update transactions
        adapter.setOnItemClickListener(transaction -> {
            // Open Transaction Activity
            Intent intentTransactionActivity = new Intent(getActivity(), TransactionActivity.class);
            intentTransactionActivity.putExtra(Constants.TRANSACTION_ID_TAG, transaction.getId());
            intentTransactionActivity.putExtra(Constants.TRANSACTION_DESCRIPTION_TAG, transaction.getDescription());
            intentTransactionActivity.putExtra(Constants.TRANSACTION_AMOUNT_TAG, transaction.getAmount());
            intentTransactionActivity.putExtra(Constants.TRANSACTION_DATE_TAG, transaction.getDate());
            intentTransactionActivity.putExtra(Constants.TRANSACTION_IMAGE_TAG, transaction.getImage());

            // Start activity for result
            startActivityForResult(intentTransactionActivity, Constants.UPDATE_TRANSACTION_REQUEST);
        });


        // SETTING ON CLICK LISTENERS AND THREADS
        imgVAddTransaction.setOnClickListener(v -> {
            // Open Transaction Activity
            Intent intentTransactionActivity = new Intent(getActivity(), TransactionActivity.class);
            startActivityForResult(intentTransactionActivity, Constants.ADD_TRANSACTION_REQUEST);
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_TRANSACTION_REQUEST && resultCode == getActivity().RESULT_OK) {
            // Getting data of new transaction from intent
            String description = data.getStringExtra(Constants.TRANSACTION_DESCRIPTION_TAG);
            double amount = data.getDoubleExtra(Constants.TRANSACTION_AMOUNT_TAG, 0);
            Date date = (Date) data.getSerializableExtra(Constants.TRANSACTION_DATE_TAG);
            byte[] imageInByte = data.getByteArrayExtra(Constants.TRANSACTION_IMAGE_TAG);

            // Creating new transaction
            UserTransaction transaction = new UserTransaction(user.getId(), description, amount, date, imageInByte);

            // Inserting transaction to database
            transactionModel.insertTransaction(transaction);
            Toast.makeText(getActivity(), "Transaction inserted", Toast.LENGTH_SHORT).show();

        } else if (requestCode == Constants.UPDATE_TRANSACTION_REQUEST && resultCode == getActivity().RESULT_OK) {
            // Getting data of new transaction from intent
            int id = data.getIntExtra(Constants.TRANSACTION_ID_TAG, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Contact can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String description = data.getStringExtra(Constants.TRANSACTION_DESCRIPTION_TAG);
            double amount = data.getDoubleExtra(Constants.TRANSACTION_AMOUNT_TAG, 0);
            Date date = (Date) data.getSerializableExtra(Constants.TRANSACTION_DATE_TAG);
            byte[] imageInByte = data.getByteArrayExtra(Constants.TRANSACTION_IMAGE_TAG);

            // Creating new transaction
            UserTransaction transaction = new UserTransaction(user.getId(), description, amount, date, imageInByte);
            transaction.setId(id);

            // Updating transaction to database
            transactionModel.updateTransaction(transaction);
            Toast.makeText(getActivity(), "Transaction updated", Toast.LENGTH_SHORT).show();
        }
    }
}