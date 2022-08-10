package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;

    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    Button regBtn, regToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);

        regName = findViewById(R.id.fullname);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone_number);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.signup_btn);
        regToLoginBtn = findViewById(R.id.to_login_btn);
        backBtn = findViewById(R.id.signup_back_button);
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username is too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("No space allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address!");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = regPhone.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhone.setError("Field cannot be empty");
            return false;
        } else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +             //at least 1 digit
                //"(?=.*[a-z])" +             //at least 1 lower case character
                //"(?=.*[A-Z])" +             //at least 1 upper case character
                "(?=.*[a-zA-Z])" +          //any letter
                "(?=.*[@#$%^&+=])" +        //at least 1 special character
                //"(?=\\S+$])" +              //no white spaces
                ".{4,}" +                   // at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak!");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void callVerifyOTPScreen(View view) {

        if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNumber() | !validatePassword()) {
            return;
        }
        String _fullname = regName.getEditText().getText().toString();
        String _username = regUsername.getEditText().getText().toString();
        String _email = regEmail.getEditText().getText().toString();
        String _phoneNumber = regPhone.getEditText().getText().toString();
        String _password = regPassword.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("fullname", _fullname);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("phoneNumber", _phoneNumber);
        intent.putExtra("password", _password);
        startActivity(intent);

    }

}