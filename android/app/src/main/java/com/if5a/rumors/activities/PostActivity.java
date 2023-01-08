package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.if5a.rumors.R;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.PostAPI;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.UtilityAPI;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private ImageView ivBack;
    private FirebaseAuth mAuth;
    private EditText etJudul, etContent;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mAuth = FirebaseAuth.getInstance();

        RadioGroup radioGroup= findViewById(R.id.rg_kategori);

        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        etJudul = findViewById(R.id.et_post_judul);
        etContent = findViewById(R.id.et_post_content);
        btnPost = findViewById(R.id.btn_post_rumor);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = etJudul.getText().toString();
                String content = etContent.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton)findViewById(selectedId);
                String selectedRadioButtonText = radioButton.getText().toString().toLowerCase();

                UtilityAPI.getRetrofit().create(APIService.class).postPost(mAuth.getCurrentUser().getUid(),selectedRadioButtonText,judul,content).enqueue(new Callback<GetJson<PostAPI>>() {
                    @Override
                    public void onResponse(Call<GetJson<PostAPI>> call, Response<GetJson<PostAPI>> response) {
                        Toast.makeText(PostActivity.this, "Berhasil Post", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetJson<PostAPI>> call, Throwable t) {
                        Toast.makeText(PostActivity.this, "Gagal Post", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}