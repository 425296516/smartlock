package com.anlida.smartlock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> tabs;

    public TabPagerAdapter(FragmentManager fm,List<Fragment> tabs) {
        super(fm);
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs == null ? 0 : tabs.size();
    }
}
