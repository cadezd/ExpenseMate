package com.example.expensemate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.expensemate.databse.connection.MyDatabase;
import com.example.expensemate.databse.entities.User;
import com.example.expensemate.databse.entities.UserWithTransactions;
import com.example.expensemate.databse.repository.UserRepository;
import com.example.expensemate.model.UserModel;
import com.example.expensemate.util.Util;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    TextInputLayout textInputLayoutFullName, textInputLayoutUsername, textInputLayoutPassword, textInputLayoutConfirmPassword;
    EditText txtInFullName, txtInUsername, txtInPassword, txtInConfirmPassword;
    Button btnRegister;
    TextView txtVLogin;

    // DECLARING DATABASE
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // INITIALIZING DATABASE
        userModel = new UserModel(getApplication());

        // INITIALIZING COMPONENTS
        textInputLayoutFullName = findViewById(R.id.textInputLayoutFullName);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        txtInFullName = findViewById(R.id.txtInFullName);
        txtInUsername = findViewById(R.id.txtInUsername);
        txtInPassword = findViewById(R.id.txtInPassword);
        txtInConfirmPassword = findViewById(R.id.txtInConfirmPassword);

        // TODO: remove test data
        txtInFullName.setText("Test User");
        txtInUsername.setText("test1");
        txtInPassword.setText("Test123_");
        txtInConfirmPassword.setText("Test123_");

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


    private void register() {
        String fullName = txtInFullName.getText().toString().trim();
        String username = txtInUsername.getText().toString().trim();
        String password = txtInPassword.getText().toString().trim();
        String confirmPassword = txtInConfirmPassword.getText().toString().trim();

        // check if full name is empty
        if (fullName.isEmpty()) {
            textInputLayoutFullName.setError("Please enter your full name");
            return;
        }

        // check if username is empty
        if (username.isEmpty()) {
            textInputLayoutUsername.setError("Please enter your username");
            return;
        }

        // check if password is empty
        if (password.isEmpty()) {
            textInputLayoutPassword.setError("Please enter your password");
            return;
        }

        // check if confirm password is empty
        if (confirmPassword.isEmpty()) {
            textInputLayoutConfirmPassword.setError("Please confirm your password");
            return;
        }

        // check if password is at least 8 characters
        if (password.length() < 8) {
            textInputLayoutPassword.setError("Password must be at least 8 characters");
            return;
        }

        // check is password contains at least one digit
        boolean containsDigit = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                containsDigit = true;
                break;
            }
        }
        if (!containsDigit) {
            textInputLayoutPassword.setError("Password must contain at least one digit");
            return;
        }

        // check if password contains at least one uppercase letter
        boolean containsUppercase = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                containsUppercase = true;
                break;
            }
        }
        if (!containsUppercase) {
            textInputLayoutPassword.setError("Password must contain at least one uppercase letter");
            return;
        }

        // check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            textInputLayoutConfirmPassword.setError("Password does not match");
            return;
        }

        // check if username already exists
        if (userModel.isUsernameTaken(username)) {
            textInputLayoutUsername.setError("Username already exists");
            return;
        }

        // create user and insert it into database
        User user = new User(fullName, username, Util.getSHA512SecurePassword(password, username), new Date());
        userModel.insert(user);
    }
}