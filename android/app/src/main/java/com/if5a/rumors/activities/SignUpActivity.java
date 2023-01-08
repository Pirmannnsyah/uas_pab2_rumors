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
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.User;
import com.if5a.rumors.models.UserAPI;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.UtilityAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText etnama, etemail, etnohp, etpassword, etulangpassword;
    private TextView etmasuk;
    private Button btndaftar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();

        etnama = findViewById(R.id.et_nama);
        etemail = findViewById(R.id.et_email);
        etnohp = findViewById(R.id.et_email);
        etpassword = findViewById(R.id.et_password);
        etulangpassword = findViewById(R.id.et_ulangpassword);
        etmasuk = findViewById(R.id.tv_buatmasuk);
        btndaftar = findViewById(R.id.btn_daftar);

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etnama.getText().toString();
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String ulangPassword = etulangpassword.getText().toString();
                String noHandphone = etnohp.getText().toString();

                if(TextUtils.isEmpty(email)){
                    etemail.setError("Enter email address!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etpassword.setError("Enter your password!");
                    return;
                }
                if(TextUtils.isEmpty(ulangPassword)){
                    etulangpassword.setError("Enter your confirm password!");
                    return;
                }
                if(TextUtils.isEmpty(nama)){
                    etnama.setError("Enter your Full Name!");
                    return;
                }
                if(TextUtils.isEmpty(noHandphone)){
                    etnohp.setError("Enter your Phone Number!");
                    return;
                }
                if (password.length() <6){
                    etpassword.setError("Password to short!! Enter Mininum 6 Character");
                }
                if (!ulangPassword.equals(password)){
                    etulangpassword.setError("Password Doesn`t match!");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
//                                      Toast.makeText(SignUpActivity.this, "user_id:"+mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                                    UtilityAPI.getRetrofit().create(APIService.class).postUser(mAuth.getCurrentUser().getUid(),nama,nama).enqueue(new Callback<GetJson<UserAPI>>() {
                                        @Override
                                        public void onResponse(Call<GetJson<UserAPI>> call, Response<GetJson<UserAPI>> response) {
                                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                                            User user = new User(email, nama);
                                            String userId = task.getResult().getUser().getUid();
                                            mRef = mRoot.child("users").child(userId);
                                            mRef.setValue(user);
                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<GetJson<UserAPI>> call, Throwable t) {
                                            Toast.makeText(SignUpActivity.this, "FAiled!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    Toast.makeText(SignUpActivity.this, "Sign Up Fail!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });



            }
        });


        etmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}