package com.mobile.hulklee01.musicallife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionPageAdapter extends FragmentStatePagerAdapter {
    private Fragment mFeederFragment = new FeederFragment();
    private Fragment mCalendarFragment = new CalendarFragment();

    public CollectionPageAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int i) {
        return (i == 0) ? mFeederFragment : mCalendarFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "뮤지컬 정보";
        } else {
            return "달력";
        }
    }
}