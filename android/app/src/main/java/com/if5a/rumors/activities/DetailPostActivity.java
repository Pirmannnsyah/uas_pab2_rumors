package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.if5a.rumors.R;
import com.if5a.rumors.databinding.ActivityDetailPostBinding;
import com.if5a.rumors.models.GetJson;
import com.if5a.rumors.models.PostAPI;
import com.if5a.rumors.models.PostAPiIndex;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.utilities.UtilityAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ActivityDetailPostBinding binding;
    private PostAPiIndex postAPiIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        postAPiIndex = getIntent().getParcelableExtra("data");
        binding.tvDetailUsernamePost.setText(postAPiIndex.getUsername());
        binding.tvDetailDatePost.setText(postAPiIndex.getDate());
        binding.tvDetailJudulPost.setText(postAPiIndex.getJudul());
        binding.tvDetailDeskripsiPost.setText(postAPiIndex.getContent());
        binding.ivDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityAPI.getRetrofit().create(APIService.class).deletePost(postAPiIndex.getId()).enqueue(new Callback<GetJson<PostAPI>>() {
                    @Override
                    public void onResponse(Call<GetJson<PostAPI>> call, Response<GetJson<PostAPI>> response) {
                        Toast.makeText(DetailPostActivity.this, "berhasil menghapus post", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetJson<PostAPI>> call, Throwable t) {
                        Toast.makeText(DetailPostActivity.this, "gagal menghapus post", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailPostActivity.this, EditPostActivity.class);
                intent.putExtra("data",postAPiIndex);
                startActivity(intent);
                finish();
            }
        });

    }
}