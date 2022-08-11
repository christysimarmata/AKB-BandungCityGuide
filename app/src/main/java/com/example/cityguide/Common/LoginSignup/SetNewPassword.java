package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.cityguide.Database.CheckInternet;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPassword, confirmPassword;
    Button okBtn;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        newPassword = findViewById(R.id.snp_new_password);
        confirmPassword = findViewById(R.id.snp_confirm_password);
        progressBar = findViewById(R.id.snp_progress_bar);

    }

    public void setNewPassword(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validatePassword()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNumber");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class));
        finish();

    }

    private Boolean validatePassword() {
        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _confirmPassword = confirmPassword.getEditText().getText().toString().trim();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +             //at least 1 digit
                //"(?=.*[a-z])" +             //at least 1 lower case character
                //"(?=.*[A-Z])" +             //at least 1 upper case character
                "(?=.*[a-zA-Z])" +          //any letter
                "(?=.*[@#$%^&+=])" +        //at least 1 special character
                //"(?=\\S+$])" +              //no white spaces
                ".{4,}" +                   // at least 4 characters
                "$";

        if (_newPassword.isEmpty()) {
            newPassword.setError("Field cannot be empty");
            newPassword.requestFocus();
            return false;
        } else if (_confirmPassword.isEmpty()) {
            confirmPassword.setError("Field cannot be empty");
            confirmPassword.requestFocus();
            return false;
        } else if (!_newPassword.matches(passwordVal)) {
            newPassword.setError("Password is too weak!");
            newPassword.requestFocus();
            return false;
        }else if (!_confirmPassword.matches(passwordVal)) {
            confirmPassword.setError("Password is too weak!");
            confirmPassword.requestFocus();
            return false;
        } else if (!_newPassword.equals(_confirmPassword)) {
            confirmPassword.setError("Both password must be the same");
            return false;
        }else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }
    }


    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}