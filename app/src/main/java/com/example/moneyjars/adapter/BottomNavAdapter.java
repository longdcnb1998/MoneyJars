package com.example.moneyjars.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneyjars.fragment.FragmentAdd;
import com.example.moneyjars.fragment.FragmentChart;
import com.example.moneyjars.fragment.FragmentHome;
import com.example.moneyjars.fragment.FragmentHistory;
import com.example.moneyjars.fragment.FragmentOther;

public class BottomNavAdapter extends FragmentStatePagerAdapter {
    public BottomNavAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;
       switch (position){
           case 0:
               fragment = new FragmentHome();
               break;
           case 1:
               fragment = new FragmentHistory();
               break;
           case 2:
               fragment = new FragmentChart();
               break;
           case 3:
               fragment = new FragmentOther();
               break;
       }
       return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
