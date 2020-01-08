package com.xinhai.xinhaisemenanalysis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentChangeAdapter extends FragmentPagerAdapter {
    private List<Fragment> flist;//添加的Fragment就在这里

    public FragmentChangeAdapter(FragmentManager fm, List<Fragment> flist) {
        super(fm);
        this.flist = flist;
    }

    @Override
    public Fragment getItem(int position) {
        return flist.get(position);
    }

    @Override
    public int getCount() {
        return flist.size();
    }
}