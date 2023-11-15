package com.example.expensemate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.model.UserModel;
import com.example.expensemate.util.Util;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

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

        // GETTING USER FROM DATABASE
        User user = null;
        try {
            user = userModel.findUserByUsernameAndPassword(
                    username,
                    Util.getSHA512SecurePassword(password, username)
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            showErrorMessage("Something went wrong, please try again.\n" + e.getMessage());
        }

        // Check if user exists
        if (user == null) {
            showErrorMessage("Invalid username or password");
            return;
        }

        // Open Home MainActivity and pass user
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(Constants.USER_TAG, user);
        startActivity(intent);
    }

    private void showErrorMessage(String message) {
        // show error dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}