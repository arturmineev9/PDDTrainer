package com.example.autoschool11.login_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.databinding.ActivityLoginBinding;
import com.example.autoschool11.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        binding.buttonRegistration.setOnClickListener(view1 -> {
            Log.d("click", "clicked");
            if (!TextUtils.isEmpty(binding.usernameInput.getText().toString()) && !TextUtils.isEmpty(binding.pass.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(binding.usernameInput.getText().toString(), binding.pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Пользователь зарегистрирован успешно!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}