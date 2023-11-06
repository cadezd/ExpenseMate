package com.example.expensemate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    TextInputLayout textInputLayoutUsername, textInputLayoutPassword;
    EditText txtInUsername, txtInPassword;
    Button btnLogin;
    TextView txtVRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // INITIALIZING COMPONENTS
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        txtInUsername = findViewById(R.id.txtInUsername);
        txtInPassword = findViewById(R.id.txtInPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtVRegister = findViewById(R.id.txtVRegister);

        // SETTING ON FOCUS CHANGE LISTENER
        txtInUsername.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutUsername.setError(null);
        });
        txtInPassword.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutPassword.setError(null);
        });

        // SETTING ON CLICK LISTENER
        btnLogin.setOnClickListener(view -> {
            login();
        });

        txtVRegister.setOnClickListener(view -> {
            // Open Register Activity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        // VALIDATING INPUTS
        if (txtInUsername.getText().toString().isEmpty()) {
            textInputLayoutUsername.setError("Username cannot be empty");
            return;
        }
        if (txtInPassword.getText().toString().isEmpty()) {
            textInputLayoutPassword.setError("Password cannot be empty");
            return;
        }

        // TODO: Authenticate user

        // Open Home Activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}