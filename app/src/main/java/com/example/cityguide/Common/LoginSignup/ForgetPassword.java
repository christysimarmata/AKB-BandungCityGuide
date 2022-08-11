package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cityguide.Database.CheckInternet;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNumber;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);


        phoneNumber = findViewById(R.id.fp_phone_number);
        progressBar = findViewById(R.id.fp_progress_bar);
    }

    public void verifyPhoneNumber(View view) {
        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if(!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone_number").equalTo(_phoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNumber", _phoneNumber);
                    intent.putExtra("whatToDo", "updateData");
                    startActivity(intent);
                    finish();

                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    phoneNumber.setError("No such user exist");
                    phoneNumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
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
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}