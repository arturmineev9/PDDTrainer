package com.example.autoschool11.login_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.autoschool11.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
    }
}