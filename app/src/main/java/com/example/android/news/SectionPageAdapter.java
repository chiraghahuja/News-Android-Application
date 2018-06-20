package com.example.android.news;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPageAdapter extends FragmentPagerAdapter {

    //static data global vars
    public static int NUMBER_OF_TABS = 4;
    public static String[] sectionNames;
    public static String[] sectionId;

    public SectionPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        sectionId = context.getResources().getStringArray(R.array.news_tabs_id);
        sectionNames= context.getResources().getStringArray(R.array.news_tabs_string);
    }

    @Override
    public Fragment getItem(int position) {
        NewsCategoryFragment newsCategoryFragment = NewsCategoryFragment.newInstance(position);
        return newsCategoryFragment;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sectionNames[position];
    }
}
