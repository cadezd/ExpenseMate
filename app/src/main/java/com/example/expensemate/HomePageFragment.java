package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.Transaction;


public class HomePageFragment extends Fragment {

    public HomePageFragment() {
        // Required empty public constructor
    }

    // DECLARING COMPONENTS
    ImageView imgVAddTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        // INITIALIZING COMPONENTS
        imgVAddTransaction = view.findViewById(R.id.imgVAddTransaction);

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
            Transaction transaction = (Transaction) data.getSerializableExtra(Constants.TRANSACTION_TAG);
            Log.d("TEST", transaction.toString());

            // TODO: add transaction to the database https://stackoverflow.com/questions/67203765/how-to-insert-entities-with-a-one-to-many-relationship-in-room
        }

    }
}