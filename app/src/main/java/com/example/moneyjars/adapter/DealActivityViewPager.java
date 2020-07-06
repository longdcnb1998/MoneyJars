package com.example.moneyjars.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneyjars.fragment.FragmentAdd;
import com.example.moneyjars.fragment.FragmentMove;
import com.example.moneyjars.fragment.FragmentSub;

public class DealActivityViewPager extends FragmentStatePagerAdapter {
    public DealActivityViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FragmentAdd.getInstance();
            case 1:
                return FragmentSub.getInstance();
            case 2:
                return  FragmentMove.getInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Thu nhập";
            case 1:
                return "Chi tiêu";
            case 2:
                return "Chuyển hũ";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
