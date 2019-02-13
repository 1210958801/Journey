package com.shade.journey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_4 picture_4:40 PM
 * @Description:用来将读取到的碎片中的数据、通过适配器展示在ViewPager中
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
