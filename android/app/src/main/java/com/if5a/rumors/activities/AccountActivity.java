package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.if5a.rumors.R;
import com.if5a.rumors.databinding.ActivityAccountBinding;
import com.if5a.rumors.databinding.ActivityChatBinding;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.User;
import com.if5a.rumors.models.UserAPI;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.UtilityAPI;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRoot, mRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRoot = mDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                binding.etAccUsername.setText(user.getFullName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = binding.etAccEditUsername.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    binding.etAccUsername.setError("Enter your new name!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("fullName", name);

                FirebaseDatabase.getInstance().getReference().child("users").child(userId).updateChildren(map, (error, ref) -> {

                    UtilityAPI.getRetrofit().create(APIService.class).updateUser(userId,name).enqueue(new Callback<GetJson<UserAPI>>() {
                        @Override
                        public void onResponse(Call<GetJson<UserAPI>> call, Response<GetJson<UserAPI>> response) {
                            Toast.makeText(AccountActivity.this, "Berhasil di update", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<GetJson<UserAPI>> call, Throwable t) {
                            Toast.makeText(AccountActivity.this, "Gagal di update", Toast.LENGTH_SHORT).show();

                        }
                    });

                });

            }
        });
    }
}