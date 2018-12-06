package com.dicoding.lukman.catalogmovie.ui.pageradapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dicoding.lukman.catalogmovie.ui.fragment.FavoriteFrag;
import com.dicoding.lukman.catalogmovie.ui.fragment.NowPlayingFrag;
import com.dicoding.lukman.catalogmovie.ui.fragment.UpCommingFrag;

/**
 * Created by #PemimpinMuda on 11/10/2018.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter{

    private int mNoOfTab;
    public MyPagerAdapter(FragmentManager fm, int NumberOfTab) {
        super(fm);
        this.mNoOfTab = NumberOfTab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new NowPlayingFrag();
            case 1:
                return new UpCommingFrag();
            case 2:
               return new FavoriteFrag();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTab;
    }
}
