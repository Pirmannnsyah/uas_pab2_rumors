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
import com.google.firebase.auth.FirebaseAuth;
import com.if5a.rumors.R;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etEmail;
    private TextView tvMasuk;
    private Button btnResetPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.et_email);
        tvMasuk = findViewById(R.id.tv_buatmasukt);
        btnResetPassword = findViewById(R.id.btn_resetpassowrd);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Enter Your Email Address!");
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetPasswordActivity.this, "Intruksi reset password telah dikirimkan", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(ResetPasswordActivity.this, "Gagal Mengirim Intruksi Reset Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}