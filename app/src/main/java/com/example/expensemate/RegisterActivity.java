package com.example.expensemate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView txtVLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtVLogin = findViewById(R.id.txtVLogin);
        txtVLogin.setOnClickListener(view -> {
            // Open Login Activity
            finish();
        });
    }
}