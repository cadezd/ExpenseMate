package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


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
            startActivity(intentTransactionActivity);
        });


        return view;
    }
}