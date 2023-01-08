package com.if5a.rumors.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.if5a.rumors.R;
import com.if5a.rumors.adapters.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageView ivLogout, ivmessage, ivAccount, ivInfo;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
//    BottomNavigationView bottomNavigationView;
//    HomeFragment homeFragment = new HomeFragment();
//    ChatFragment chatFragment = new ChatFragment();
//    AccountFragment accountFragment = new AccountFragment();
//    InfoFragment infoFragment = new InfoFragment();

    private FirebaseAuth mAuth;
    private static final String TAG = MainActivity.class.getSimpleName();

    private String mUsername;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "";

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

//            getSupportFragmentManager().beginTransaction().replace(R.id.bottomAppBar,HomeFragment).commit();
//
//            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.miHome:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,HomeFragment).commit();
//                            return true;
//                        case R.id.miMessage:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,ChatFragment).commit();
//                            return true;
//                        case R.id.miAccount:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,AccountFragment).commit();
//                            return true;
//                        case R.id.miInfo:
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,InfoFragment).commit();
//                            return true;
//                    }
//                    return false;
//                }
//            });



            tabLayout = findViewById(R.id.tb_main);
            viewPager2 = findViewById(R.id.view_pager);
            myViewPagerAdapter = new MyViewPagerAdapter(this);
            viewPager2.setAdapter(myViewPagerAdapter);
            ivLogout = findViewById(R.id.iv_logout);
            ivmessage = findViewById(R.id.iv_messege_receiver);
            ivAccount = findViewById(R.id.iv_person);
            ivInfo = findViewById(R.id.iv_info);

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

            ivmessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            ivAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            ivInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        }
    }
}