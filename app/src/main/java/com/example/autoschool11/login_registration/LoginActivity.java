package com.example.autoschool11.login_registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.databinding.ActivityLoginBinding;
import com.example.autoschool11.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        binding.buttonLogin.setOnClickListener(view1 -> {

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null){
            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }
}