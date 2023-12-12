package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.UserTransaction;
import com.example.expensemate.util.DecimalFilter;

import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    EditText txtInDescription, txtInAmount, txtInDate;
    ImageView imgVPicture, imgVBack;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // INITIALIZING COMPONENTS
        txtInDescription = findViewById(R.id.txtInDescription);
        txtInAmount = findViewById(R.id.txtInAmount);
        txtInDate = findViewById(R.id.txtInDate);

        imgVPicture = findViewById(R.id.imgVPicture);
        imgVBack = findViewById(R.id.imgVBack);

        btnSave = findViewById(R.id.btnSave);

        // Get Intent
        Intent intent = getIntent();
        if (!intent.hasExtra(Constants.NEW_TRANSACTION_TAG)) {
            // TODO: set the data to the inputs
        }

        // SETTING THE FILTERS
        DecimalFilter decimalFilter = new DecimalFilter(txtInAmount);
        txtInAmount.setFilters(new InputFilter[]{decimalFilter});

        // SETTING ON CLICK LISTENERS
        imgVBack.setOnClickListener(v -> {
            // Go back to Home Page Fragment
            finish();
        });

        btnSave.setOnClickListener(v -> {
            Log.d("TEST", "gathering data");
            // Collect data from inputs and send it to the main activity
            String description = txtInDescription.getText().toString().trim();
            double amount = Double.parseDouble(txtInAmount.getText().toString().trim());
            Date date = null;
            byte[] imageInByte = null;

            // TODO: get image from image view and date from date picker


            // Create new transaction
            UserTransaction transaction = new UserTransaction(1, description, amount, date, imageInByte);

            // Send transaction to main activity
            Log.d("TEST", "sending data to main activity");
            Intent data = new Intent();
            data.putExtra(Constants.TRANSACTION_TAG, transaction);
            setResult(RESULT_OK, data);
            finish();
        });


    }
}