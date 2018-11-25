package com.dicoding.lukman.catalogmovie.activiy;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.pageradapter.MyPagerAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    Toolbar toolbar;
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        tabLayout =  findViewById(R.id.tablayout);
        setUpTab();
    }

    private void setUpTab() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sekarang_diputar));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.segera_hadir));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.favorite_movie));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_nowplaying);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_upcoming);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager =  findViewById(R.id.pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Intent i = new Intent(this, ActivitySearch.class);
                startActivity(i);
                break;
            case R.id.bahasa:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
