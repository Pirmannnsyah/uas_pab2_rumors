package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.if5a.rumors.R;
import com.if5a.rumors.adapters.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageView ivLogout;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {

            setContentView(R.layout.activity_main);

//            bottomNavigationView = findViewById(R.id.bottomNavigationView);
//            bottomNavigationView.setItemBackground(null);
////        bottomNavigationView.getMenu().getItem(2).
            tabLayout = findViewById(R.id.tb_main);
            viewPager2 = findViewById(R.id.view_pager);
            myViewPagerAdapter = new MyViewPagerAdapter(this);
            viewPager2.setAdapter(myViewPagerAdapter);
            ivLogout = findViewById(R.id.iv_logout);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position).select();
                }
            });

            ivLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}