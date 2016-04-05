package com.tongban.im.activity.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tb.api.UserCenterApi;
import com.tb.api.model.BaseEvent;
import com.tb.api.utils.TransferCenter;
import com.tongban.corelib.widget.view.transformer.ZoomInPageTransformer;
import com.tongban.im.R;
import com.tongban.im.activity.base.CommonImageResultActivity;
import com.tongban.im.adapter.MyMissionAdapter;
import com.tongban.im.fragment.mission.BaseMyMission;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 *
 */
public class MyMissionActivity extends CommonImageResultActivity {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_mission;
    }

    @Override
    protected void initData() {
        setTitle("myMIssion");
        Fragment ingFragment = new BaseMyMission();
        Fragment achiveFragment = new BaseMyMission();
        fragments.add(ingFragment);
        fragments.add(achiveFragment);
        FragmentPagerAdapter myAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "进行中";
                } else {
                    return "已完成";
                }
            }
        };

        mViewPager.setAdapter(myAdapter);
        //切换动画
        mViewPager.setPageTransformer(true, new ZoomInPageTransformer());
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
