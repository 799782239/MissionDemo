package com.example.yanqijs.missiondemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import adapter.ViewAdapter;

public class MyMissionActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewAdapter mViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initViewPager();
    }

    private void initViewPager() {
        mViewAdapter = new ViewAdapter(getSupportFragmentManager());
        Fragment ingFragment = new IngFragment();
        Fragment achiveFragment = new AchiveFragment();
        mViewAdapter.addFragment(ingFragment);
        mViewAdapter.addFragment(achiveFragment);
        mViewPager.setAdapter(mViewAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mymission_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.mymission_viewpager);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_mission;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
