package com.example.secondhandfurnitures;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.secondhandfurnitures.fragments.FirstFragment;
import com.example.secondhandfurnitures.fragments.FourthFragment;
import com.example.secondhandfurnitures.fragments.SecondFragment;
import com.example.secondhandfurnitures.fragments.ThirdFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new FirstFragment();
            case 1: return new SecondFragment();
            case 2: return new ThirdFragment();
            case 3: return new FourthFragment();
            default: return new FirstFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
