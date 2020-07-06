package com.example.moneyjars;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moneyjars.activity.MainActivity;
import com.example.moneyjars.adapter.ViewPagerAdapter;
import com.example.moneyjars.util.DialogUtils;
import com.google.android.material.tabs.TabLayout;

public class DialogGuide extends DialogFragment {
    private Activity activity;
    private Dialog dialog;
    private View customView;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button btn_continue;
    private Button btn_skip;
    private ViewPagerAdapter adapter;


    private int currentPosition;
    private SharedPreferences preferences;

    @Override
    public void onAttach(@NonNull Activity activity) {
        this.activity = activity;
        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity, false,activity.getResources().getColor(R.color.statusBarColor));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_guide);
        customView = activity.getLayoutInflater().inflate(R.layout.dialog_guide, null);
        dialog.show();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return customView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
    }


    private void initUI() {
        viewPager = dialog.findViewById(R.id.viewPager);
        tabLayout = dialog.findViewById(R.id.tab_layout);
        btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_skip = dialog.findViewById(R.id.btn_skip);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    private void initData() {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < 2) {
                    viewPager.setCurrentItem(currentPosition + 1);
                }
                else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            }
        });
    }
}
