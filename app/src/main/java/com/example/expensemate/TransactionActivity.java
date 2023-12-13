package com.example.expensemate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemate.constants.Constants;
import com.example.expensemate.databse.converters.ImageConverter;
import com.example.expensemate.util.DecimalFilter;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    // DECLARING COMPONENTS
    EditText txtInDescription, txtInAmount, txtInDate;
    ImageView imgVPicture, imgVBack;

    Button btnSave, btnAddImage;

    // DECLARING VARIABLES
    int id = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        btnAddImage = findViewById(R.id.btnAddImage);
        btnSave = findViewById(R.id.btnSave);

        // Get Intent
        Intent intent = getIntent();
        // Set the values to the inputs tu update transaction
        if (intent.hasExtra(Constants.TRANSACTION_ID_TAG)) {
            // Get transaction data from intent
            id = intent.getIntExtra(Constants.TRANSACTION_ID_TAG, -1);
            String description = intent.getStringExtra(Constants.TRANSACTION_DESCRIPTION_TAG);
            double amount = intent.getDoubleExtra(Constants.TRANSACTION_AMOUNT_TAG, 0);
            Date date = (Date) intent.getSerializableExtra(Constants.TRANSACTION_DATE_TAG);
            byte[] imageInByte = intent.getByteArrayExtra(Constants.TRANSACTION_IMAGE_TAG);

            // Set transaction data to inputs
            txtInDescription.setText(description);
            txtInAmount.setText(String.valueOf(amount));
            txtInDate.setText(parseDateToString(date));

            if (imageInByte != null) {
                // SOURCE START: https://stackoverflow.com/questions/13854742/byte-array-of-image-into-imageview
                imgVPicture.setImageBitmap(ImageConverter.toBitmap(imageInByte));
                // SOURCE END: https://stackoverflow.com/questions/13854742/byte-array-of-image-into-imageview
                btnAddImage.setText("Remove Image");
            }
        }

        // SETTING THE FILTERS
        txtInAmount.setFilters(new InputFilter[]{new DecimalFilter(txtInAmount)});

        // SETTING ON CLICK LISTENERS
        imgVBack.setOnClickListener(v -> {
            // Go back to Home Page Fragment
            finish();
        });

        // SOURCE START: https://www.geeksforgeeks.org/datepicker-in-android/
        txtInDate.setKeyListener(null);
        txtInDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                            String date = selectedDayOfMonth + "." + (selectedMonth + 1) + "." + selectedYear;
                            txtInDate.setText(date);
                        }
                    },
                    year,
                    month,
                    dayOfMonth);

            datePickerDialog.show();
        });
        // SOURCE END: https://www.geeksforgeeks.org/datepicker-in-android/

        btnAddImage.setOnClickListener(v -> {
            // Add or remove image
            if (imgVPicture.getDrawable() == null) {
                ImagePicker.Companion.with(this)
                        .compress(512)
                        .maxResultSize(720, 720)
                        .start();
            } else {
                imgVPicture.setImageDrawable(null);
                btnAddImage.setText("Add Image");
            }
        });

        btnSave.setOnClickListener(v -> {
            try {
                // Check if all the fields are filled except image
                if (txtInDescription.getText().toString().trim().isEmpty() || txtInAmount.getText().toString().trim().isEmpty() || txtInDate.getText().toString().trim().isEmpty())
                    throw new Exception("Please fill all the fields.");

                // Collect data from inputs and send it to the main activity
                String description = txtInDescription.getText().toString().trim();
                double amount = Double.parseDouble(txtInAmount.getText().toString().trim());
                Date date = parseStringToDate(txtInDate.getText().toString().trim());
                Bitmap bitmap = null;
                if (imgVPicture.getDrawable() != null)
                    bitmap = ((BitmapDrawable) imgVPicture.getDrawable()).getBitmap();

                // Send transaction data to main activity
                Intent data = new Intent();
                data.putExtra(Constants.TRANSACTION_ID_TAG, id);
                data.putExtra(Constants.TRANSACTION_DESCRIPTION_TAG, description);
                data.putExtra(Constants.TRANSACTION_AMOUNT_TAG, amount);
                data.putExtra(Constants.TRANSACTION_DATE_TAG, date);
                data.putExtra(Constants.TRANSACTION_IMAGE_TAG, ImageConverter.fromBitmap(bitmap));

                // Set result and finish activity
                setResult(RESULT_OK, data);
                finish();
            } catch (Exception e) {
                displayErrorMessage("Error", "Error: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // This method is called when image is selected from gallery or camera
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        try {
            Uri uri = data.getData();
            imgVPicture.setImageURI(uri);
            btnAddImage.setText("Remove Image");
        } catch (Exception e) {
            displayErrorMessage("Error", "Something went wrong. Try again.\nError: " + e.getMessage());
            btnAddImage.setText("Add Image");
        }
    }

    private void displayErrorMessage(String title, String message) {
        // Display an error with alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Date parseStringToDate(String date) {
        try {
            // Convert string to date
            // SOURCE START: https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            // SOURCE END: https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
        } catch (Exception e) {
            Log.e("Error", "Error: " + e.getMessage());
            return null;
        }
    }

    private String parseDateToString(Date date) {
        try {
            // Convert date to string
            SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy");
            return dateFormat.format(date);
        } catch (Exception e) {
            Log.e("Error", "Error: " + e.getMessage());
            return null;
        }
    }
}