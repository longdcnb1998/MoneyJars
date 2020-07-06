package com.example.moneyjars.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneyjars.R;
import com.example.moneyjars.adapter.BottomNavAdapter;
import com.example.moneyjars.databinding.ActivityMainBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.User;
import com.example.moneyjars.fragment.NoteHistoryViewModel;
import com.example.moneyjars.viewmodel.JarViewModel;
import com.example.moneyjars.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LongDinh";
    private ActivityMainBinding binding;
    private BottomNavAdapter adapter;
    private UserViewModel viewModel;
    private SharedPreferences preferences;
    private JarViewModel jarViewModel;
    private ArrayList<Jar> jars;
    private ArrayList<User> listUser;
    public static long mLastClickTime = 0;
    private int REQUEST_CODE = 123;
    private NoteHistoryViewModel historyViewModel;
    private ArrayList<Jar> listJar = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("UserAccount", MODE_PRIVATE);

        String userName = preferences.getString("UserName", "");
        String userEmail = preferences.getString("Email", "");
        String firstLogin = preferences.getString("firstLogin", "");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        jarViewModel = ViewModelProviders.of(this).get(JarViewModel.class);

        jarViewModel.getListJar(1).observe(this, data -> {
            listJar = (ArrayList<Jar>) data;
        });
        listUser = new ArrayList<>();
        if (listJar.size() == 0) {
            if (userName.length() > 0 && firstLogin.length() > 0) {
                jarViewModel.insertJar(new Jar(1, "Thiết yếu", "0", 55, "0", "0", R.mipmap.jar_yellow));
                jarViewModel.insertJar(new Jar(1, "Giáo dục", "0", 10, "0", "0", R.mipmap.jar_green));
                jarViewModel.insertJar(new Jar(1, "Tiết kiệm", "0", 10, "0", "0", R.mipmap.jar_violet));
                jarViewModel.insertJar(new Jar(1, "Hưởng thụ", "0", 10, "0", "0", R.mipmap.jar_blue));
                jarViewModel.insertJar(new Jar(1, "Đầu tư", "0", 10, "0", "0", R.mipmap.jar_ping));
                jarViewModel.insertJar(new Jar(1, "Thiện tâm", "0", 5, "0", "0", R.mipmap.heart));

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("firstLogin", "");
                editor.apply();
            }
        }
        initUI();
    }


    private void initUI() {
        Intent intent = new Intent(MainActivity.this, DealActivity.class);
        intent.putExtra("type", 0);
        adapter = new BottomNavAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(adapter);
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        binding.viewpager.setCurrentItem(0);
                        break;
                    case R.id.nav_history:
                        binding.viewpager.setCurrentItem(1);
                        break;
                    case R.id.nav_chart:
                        binding.viewpager.setCurrentItem(2);
                        break;
                    case R.id.nav_other:
                        binding.viewpager.setCurrentItem(3);
                        break;
                    case R.id.nav_add:
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            break;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                }
                return false;
            }
        });

        binding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "vị trí  " + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_history).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_chart).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_other).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && requestCode == RESULT_OK && data != null) {

        }
    }
}
