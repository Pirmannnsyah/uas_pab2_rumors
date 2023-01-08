package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.if5a.rumors.R;

public class InfoActivity extends AppCompatActivity {
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}