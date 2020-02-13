package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView  homeRegNumber,homeName, homeRole;
    DatabaseHelp databaseHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        homeRegNumber = (TextView) findViewById(R.id.home_registration_numb);
        homeName = (TextView) findViewById(R.id.home_name);
        homeRole = (TextView) findViewById(R.id.home_role);

        Bundle bundle = getIntent().getExtras();
        String bundleRegNumb = bundle.getString("regNumb");
        String password = bundle.getString("password");
        String fname = bundle.getString("bFname");
        String lname = bundle.getString("bLname");
        String role = bundle.getString("role").toUpperCase();

        homeRole.setText(role);
        homeRegNumber.setText("Registration number: " +bundleRegNumb);
        homeName.setText("Name: " +fname + " " + lname);



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
