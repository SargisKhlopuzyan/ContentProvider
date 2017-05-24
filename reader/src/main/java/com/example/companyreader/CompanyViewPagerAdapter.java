package com.example.companyreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by sargiskh on 4/28/2017.
 */

public class CompanyViewPagerAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    public CompanyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentSparseArray.put(0, new StuffFragment());
        fragmentSparseArray.put(1, new WorkersFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentSparseArray.get(position);
    }

    @Override
    public int getCount () {
        return fragmentSparseArray.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Stuff";
            case 1:
                return "Workers";
            default:
                return null;
        }
    }
}
