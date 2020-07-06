package com.example.moneyjars.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moneyjars.R;
import com.example.moneyjars.UIController;
import com.example.moneyjars.adapter.DealActivityViewPager;
import com.example.moneyjars.databinding.ActivityDealBinding;
import com.example.moneyjars.fragment.FragmentAdd;
import com.squareup.picasso.Picasso;

public class DealActivity extends AppCompatActivity {

    private ActivityDealBinding binding;
    private DealActivityViewPager adapter;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, DealActivity.class);
        intent.putExtra("type",type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type",-1);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_deal);
        binding.tvTitle.setText("Thêm giao dịch");

        Picasso.with(this)
                .load(R.mipmap.ic_back)
                .into(binding.ivLeft);

        adapter = new DealActivityViewPager(getSupportFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        switch (type){
            case 0:
                binding.viewPager.setCurrentItem(0);
                break;
            case 1:
                binding.viewPager.setCurrentItem(1);
                break;
            case 2:
                binding.viewPager.setCurrentItem(2);
                break;
            default:
        }

        binding.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}