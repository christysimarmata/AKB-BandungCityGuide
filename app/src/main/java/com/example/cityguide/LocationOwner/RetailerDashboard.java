package com.example.cityguide.LocationOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cityguide.Database.SessionManager;
import com.example.cityguide.R;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);

        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetail = sessionManager.getUserDetailFromSession();

        String fullname = userDetail.get(sessionManager.KEY_FULLNAME);
        String phoneNumber = userDetail.get(sessionManager.KEY_PHONENUMBER);

        textView.setText(fullname + "\n" + phoneNumber);
    }
}