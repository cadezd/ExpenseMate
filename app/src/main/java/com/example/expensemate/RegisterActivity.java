package com.example.expensemate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    TextInputLayout textInputLayoutFullName, textInputLayoutUsername, textInputLayoutPassword, textInputLayoutConfirmPassword;
    EditText txtInFullName, txtInUsername, txtInPassword, txtInConfirmPassword;
    Button btnRegister;
    TextView txtVLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // INITIALIZING COMPONENTS
        textInputLayoutFullName = findViewById(R.id.textInputLayoutFullName);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        txtInFullName = findViewById(R.id.txtInFullName);
        txtInUsername = findViewById(R.id.txtInUsername);
        txtInPassword = findViewById(R.id.txtInPassword);
        txtInConfirmPassword = findViewById(R.id.txtInConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        txtVLogin = findViewById(R.id.txtVLogin);

        // SETTING ON FOCUS CHANGE LISTENER
        txtInFullName.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutFullName.setError(null);
        });
        txtInUsername.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutUsername.setError(null);
        });
        txtInPassword.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutPassword.setError(null);
        });
        txtInConfirmPassword.setOnFocusChangeListener((view, b) -> {
            if (b)
                textInputLayoutConfirmPassword.setError(null);
        });

        // SETTING ON CLICK LISTENER
        btnRegister.setOnClickListener(view -> {
            register();
        });


        txtVLogin.setOnClickListener(view -> {
            // Open Login Activity
            finish();
        });
    }


    private void register(){
        String fullName = txtInFullName.getText().toString().trim();
        String username = txtInUsername.getText().toString().trim();
        String password = txtInPassword.getText().toString().trim();
        String confirmPassword = txtInConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()){
            textInputLayoutFullName.setError("Please enter your full name");
            return;
        }

        if (username.isEmpty()){
            textInputLayoutUsername.setError("Please enter your username");
            return;
        }

        if (password.isEmpty()){
            textInputLayoutPassword.setError("Please enter your password");
            return;
        }

        if (confirmPassword.isEmpty()){
            textInputLayoutConfirmPassword.setError("Please confirm your password");
            return;
        }

        if (!password.equals(confirmPassword)){
            textInputLayoutConfirmPassword.setError("Password does not match");
            return;
        }


        // TODO: add user to database
    }
}