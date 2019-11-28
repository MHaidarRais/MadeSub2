package com.rais.haidar.madesub3.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rais.haidar.madesub3.Fragment.MovieFragment;
import com.rais.haidar.madesub3.Fragment.TVSeriesFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberTab;

    public PagerAdapter(FragmentManager fm,int numberTab) {
        super(fm);
        this.numberTab = numberTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                MovieFragment movieFragment = new MovieFragment();
                return movieFragment;
            case 1:
                TVSeriesFragment tvSeriesFragment = new TVSeriesFragment();
                return tvSeriesFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTab;
    }
}
