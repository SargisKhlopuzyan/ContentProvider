package com.example.sargiskh.contentproviderapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.example.sargiskh.contentproviderapplication.fragments.ReadWorkersByCursorLoaderFragment;
import com.example.sargiskh.contentproviderapplication.fragments.WriteStuffByDbQueryFragment;
import com.example.sargiskh.contentproviderapplication.fragments.WriteWorkerByContentProviderFragment;
import com.example.sargiskh.contentproviderapplication.fragments.ReadStuffByDbQueryFragment;
import com.example.sargiskh.contentproviderapplication.fragments.ReadWorkersByContentProviderFragment;

/**
 * Created by sargiskh on 4/28/2017.
 */

/*
You would like to load and destroy Fragments as the user reads.
In this case you will use FragmentStatePagerAdapter.
If you are just displaying 3 "tabs" that do not contain a lot of heavy data (like Bitmaps), then FragmentPagerAdapter might suit you well.
Also, keep in mind that ViewPager by default will load 3 fragments into memory.

FragmentPagerAdapter might destroy View hierarchy and re load it when needed,
FragmentStatePagerAdapter only saves the state of the Fragment and completely destroys it,
if the user then comes back to that page, the state is retrieved.
*/
public class MyViewPagerAdapter extends FragmentPagerAdapter { // FragmentPagerAdapter FragmentStatePagerAdapter

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentSparseArray.put(0, new ReadStuffByDbQueryFragment());
        fragmentSparseArray.put(1, new ReadWorkersByContentProviderFragment());
        fragmentSparseArray.put(2, new ReadWorkersByCursorLoaderFragment());
        fragmentSparseArray.put(3, new WriteStuffByDbQueryFragment());
        fragmentSparseArray.put(4, new WriteWorkerByContentProviderFragment());
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
                return "Stuff (DbQuery)";
            case 1:
                return "Workers (CP)";
            case 2:
                return "Workers (CL)";
            case 3:
                return "Add Stuff (DbQuery)";
            case 4:
                return "Add Worker (CP)";
            default:
                return null;
        }
    }
}
