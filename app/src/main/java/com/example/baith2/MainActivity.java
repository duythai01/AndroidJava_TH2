package com.example.baith2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.baith2.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView nagitonView;
    private ViewPager viewPager;
    private FloatingActionButton floatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nagitonView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        floatButton = findViewById(R.id.floatBtn);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: nagitonView.getMenu().findItem(R.id.home).setChecked(true);
                    break;
                    case 1: nagitonView.getMenu().findItem(R.id.history).setChecked(true);
                        break;
                    case 2: nagitonView.getMenu().findItem(R.id.search).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    nagitonView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home: viewPager.setCurrentItem(0);
                break;
                case R.id.history: viewPager.setCurrentItem(1);
                    break;
                case R.id.search: viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        }
    });
    }
}