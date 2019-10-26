package com.example.clutter.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumTabs;

    public PagerAdapter(FragmentManager fm, int mNumTabs) {
        super(fm);
        this.mNumTabs = mNumTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
//                FoFragment followerFragment = new FollowerFragment();
//                return followerFragment;

            case 1:
//                FollowingFragment followingFragment = new FollowingFragment();
//                return followingFragment;

            default:
                return null;

        }
//        return null;
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}
