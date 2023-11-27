package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView txtVFullName, txtVTotalBalance, txtVTotalIncome, txtVTotalExpense;
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

        transactionModel.insertTransaction(new UserTransaction(user.getId(), "Test", 120, new Date(), null));
        transactionModel.insertTransaction(new UserTransaction(user.getId(), "Test", -40, new Date(), null));

        // Creating transaction view adapter
        adapter = new TransactionViewAdapter();


        // INITIALIZING COMPONENTS
        imgVAddTransaction = view.findViewById(R.id.imgVAddTransaction);
        txtVFullName = view.findViewById(R.id.txtVFullName);
        txtVTotalBalance = view.findViewById(R.id.txtVTotalBalance);
        txtVTotalIncome = view.findViewById(R.id.txtVTotalIncome);
        txtVTotalExpense = view.findViewById(R.id.txtVTotalExpense);
        rvTransactionHistory = view.findViewById(R.id.rvTransactionHistory);

        // setting layout manager to our adapter class.
        rvTransactionHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransactionHistory.setHasFixedSize(true);

        // SETTING VALUES TO COMPONENTS
        txtVFullName.setText(user.getFullName());
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

        rvTransactionHistory.setAdapter(adapter);
        transactionModel.getTodaysUserTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.submitList(transactions);
        });

        // SETTING ON CLICK LISTENERS
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

        Log.d("TEST", "onActivityResult");
        if (requestCode == Constants.ADD_TRANSACTION_REQUEST && resultCode == getActivity().RESULT_OK) {
            Log.d("TEST", "result success");
            UserTransaction transaction = (UserTransaction) data.getSerializableExtra(Constants.TRANSACTION_TAG);
            Log.d("TEST", transaction.toString());

            // TODO: add transaction to the database https://stackoverflow.com/questions/67203765/how-to-insert-entities-with-a-one-to-many-relationship-in-room
        }

    }
}