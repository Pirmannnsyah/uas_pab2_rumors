package com.if5a.rumors.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.if5a.rumors.fragments.FoodFragment;
import com.if5a.rumors.fragments.GamesFragment;
import com.if5a.rumors.fragments.HomeFragment;
import com.if5a.rumors.fragments.NewsFragment;
import com.if5a.rumors.fragments.SportFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new NewsFragment();
            case 2:
                return new GamesFragment();
            case 3:
                return new SportFragment();
            case 4:
                return new FoodFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
