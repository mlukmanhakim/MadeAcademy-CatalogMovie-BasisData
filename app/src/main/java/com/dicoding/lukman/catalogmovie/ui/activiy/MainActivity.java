package com.dicoding.lukman.catalogmovie.ui.activiy;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.data.models.Result;
import com.dicoding.lukman.catalogmovie.data.notification.preference.AlarmPreference;
import com.dicoding.lukman.catalogmovie.data.notification.receiver.MovieDailyReceiver;
import com.dicoding.lukman.catalogmovie.data.notification.receiver.UpComingMovieReceiver;
import com.dicoding.lukman.catalogmovie.data.presenter.UpComingMoviePresenter;
import com.dicoding.lukman.catalogmovie.ui.pageradapter.MyPagerAdapter;
import com.dicoding.lukman.catalogmovie.ui.view.MovieView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener , MovieView{

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    private Menu menu;
    private static final String TAG = "MainActivity";
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.root)
    LinearLayout root;
    MovieDailyReceiver movieDailyReceiver;
    UpComingMovieReceiver movieUpcomingReceiver = new UpComingMovieReceiver();
    private AlarmPreference mPreferences;
    private Boolean isActive = false;

    List<Result> listWithDate = new ArrayList<>();
    UpComingMoviePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = new AlarmPreference(this);
        if (mPreferences.getFirst() != null){
            super.onStart();
        }else {
            Intent i = new Intent(this, SplashScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter = new UpComingMoviePresenter(this);
        movieDailyReceiver = new MovieDailyReceiver();
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
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        this.menu = menu;
        setStatus();
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
                break;
            case R.id.onofAlarm:
                if (!isActive){
                    mPreferences.setOnOfAlarm(true);
                    movieDailyReceiver.setAlarm(this);
                    setReleaseAlarm();
                    showSnackbar("Aktif");
                }
                else {
                    mPreferences.setOnOfAlarm(false);
                    movieDailyReceiver.cancelAlarm(this);
                    movieUpcomingReceiver.cancelAlarm(this);
                    showSnackbar("Tidak Aktif");
                }
                isActive = !isActive;
                setStatus();

        }
        return true;
    }

    private void setStatus(){
        isActive = mPreferences.getStatusAlarm();
        if (!isActive){
            menu.getItem(1).setIcon(R.drawable.ic_notifications_off);

        }else {
            menu.getItem(1).setIcon(R.drawable.ic_notifications_on);

        }
    }



    private void setReleaseAlarm(){
        presenter.getMovie("en-US");
    }

    @Override
    public void tampilMovie(List<Result> data) {
        listWithDate.clear();
        for (Result result : data){
            if (result.getReleaseDate().equals(getDate())){
                listWithDate.add(result);
                Log.d(TAG, "tampilMovie:  ceekSameDate" +listWithDate.size());
            }

        }
        movieUpcomingReceiver.setReleaseAlarm(this, listWithDate);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void hideError() {}

    @Override
    public void showLoading() {}

    @Override
    public void showError() {}

    @Override
    public void hideloading() {}

    public void showSnackbar(String message){
        Snackbar.make(root, "Status notifikasi adalah : " +message, Snackbar.LENGTH_LONG).show();
    }

    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
