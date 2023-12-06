package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.model.UserModel;
import com.example.expensemate.model.UserTransactionModel;


public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }

    // DECLARE COMPONENTS
    TextView txtVFullName;
    TextView txtVUsername;
    Button btnDeleteAccount;
    Button btnDeleteTransactions;
    Button btnLogOut;


    // DECLARE VARIABLES
    User user;
    UserTransactionModel transactionModel;
    UserModel userModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Get user from intent
        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra(Constants.USER_TAG);
        // Creating user model and user transaction model
        userModel = new UserModel(getActivity().getApplication());
        transactionModel = new UserTransactionModel(getActivity().getApplication(), user.getId());


        // INITIALIZE COMPONENTS
        txtVFullName = view.findViewById(R.id.txtVFullName);
        txtVUsername = view.findViewById(R.id.txtVUsername);
        btnDeleteAccount = view.findViewById(R.id.btnDeleteAccount);
        btnDeleteTransactions = view.findViewById(R.id.btnDeleteTransactions);
        btnLogOut = view.findViewById(R.id.btnLogOut);


        // SETTING VALUES
        txtVFullName.setText(user.getFullName());
        txtVUsername.setText("@" + user.getUsername());


        // SETTING LISTENERS
        btnDeleteAccount.setOnClickListener(v -> {
            // Delete user, open login activity and clear back stack completely
            userModel.delete(user);
            Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
            getActivity().finish();
        });

        btnDeleteTransactions.setOnClickListener(v -> {
            // Delete all transactions
            transactionModel.deleteAllTransactions(user.getId());
            // Display toast message
            Toast.makeText(getActivity(), "All transactions deleted", Toast.LENGTH_SHORT).show();
        });

        btnLogOut.setOnClickListener(v -> {
            // Open login activity and clear back stack completely
            Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
            getActivity().finish();
        });


        return view;
    }
}