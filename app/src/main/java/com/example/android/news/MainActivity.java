package com.example.android.news;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager categoryFragmentViewPager;
    private TableLayout categoryTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryFragmentViewPager = findViewById(R.id.categoryfragment_viewpager);
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager(),getApplicationContext());
        categoryFragmentViewPager.setAdapter(sectionPageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.categorysliding_tabs);
        tabLayout.setupWithViewPager(categoryFragmentViewPager);
    }
}
