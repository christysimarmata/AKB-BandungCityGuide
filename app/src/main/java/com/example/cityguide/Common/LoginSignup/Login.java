package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cityguide.Database.CheckInternet;
import com.example.cityguide.Database.SessionManager;
import com.example.cityguide.LocationOwner.RetailerDashboard;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputLayout phoneNumber, password;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progress_bar);

    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void userLogin(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone_number").equalTo(_phoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_phoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = snapshot.child(_phoneNumber).child("fullname").getValue(String.class);
                        String _username = snapshot.child(_phoneNumber).child("username").getValue(String.class);
                        String _email = snapshot.child(_phoneNumber).child("email").getValue(String.class);
                        String _phone_number = snapshot.child(_phoneNumber).child("phone_number").getValue(String.class);
                        String _password = snapshot.child(_phoneNumber).child("password").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(Login.this);
                        sessionManager.createLoginSession(_fullname, _username, _email, _phone_number, _password);
                        startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "No such user exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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


    public void callForgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }
}