package com.if5a.rumors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.if5a.rumors.R;

public class SignInActivity extends AppCompatActivity {
    private EditText etemail, etpassword;
    private Button btnmasuk;
    private TextView tvlupapassword, tvbuatdaftar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();

        etemail = findViewById(R.id.et_email);
        etpassword = findViewById(R.id.et_password);
        btnmasuk = findViewById(R.id.btn_masuk);
        tvlupapassword = findViewById(R.id.tv_lupapassword);
        tvbuatdaftar = findViewById(R.id.tv_buatdaftar);

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    etemail.setError("Enter your email address!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etpassword.setError("Enter your password!");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvbuatdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvlupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}