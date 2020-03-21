package com.wahidhidayat.latihanapi;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wahidhidayat.latihanapi.Activity.FavoriteActivity;
import com.wahidhidayat.latihanapi.Activity.MovieSearchActivity;
import com.wahidhidayat.latihanapi.Activity.ReminderActivity;
import com.wahidhidayat.latihanapi.Activity.TvSearchActivity;
import com.wahidhidayat.latihanapi.Adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    pos = 0;
                } else if (position == 1) {
                    pos = 1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_favorite_menu) {
            Intent mIntent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_search) {
            if (pos == 0) {
                Intent mIntent = new Intent(MainActivity.this, MovieSearchActivity.class);
                startActivity(mIntent);
            } else if (pos == 1) {
                Intent mIntent = new Intent(MainActivity.this, TvSearchActivity.class);
                startActivity(mIntent);
            }
        } else if (item.getItemId() == R.id.notification_btn) {
            Intent mIntent = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
