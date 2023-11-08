package com.example.expensemate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.expensemate.model.UserModel;
import com.example.expensemate.util.Util;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    TextInputLayout textInputLayoutUsername, textInputLayoutPassword;
    EditText txtInUsername, txtInPassword;
    Button btnLogin;
    TextView txtVRegister;

    // DECLARING DATABASE
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // INITIALIZING DATABASE
        userModel = new UserModel(getApplication());

        // INITIALIZING COMPONENTS
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        txtInUsername = findViewById(R.id.txtInUsername);
        txtInPassword = findViewById(R.id.txtInPassword);

        // TODO: remove test data
        txtInUsername.setText("test1");
        txtInPassword.setText("Test123_");

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
        String username = txtInUsername.getText().toString().trim();
        String password = txtInPassword.getText().toString().trim();

        if (username.isEmpty()) {
            textInputLayoutUsername.setError("Username cannot be empty");
            return;
        }
        if (password.isEmpty()) {
            textInputLayoutPassword.setError("Password cannot be empty");
            return;
        }

        userModel
                .findUserByUsernameAndPassword(username, Util.getSHA512SecurePassword(password, username))
                .observe(this, user -> {
                    if (user == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Invalid username or password");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                        return;
                    }

                    // Open Home Activity
                    // TODO: pass user and transactions to home activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                });
    }
}