package com.shade.journey.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.shade.journey.R;
import com.shade.journey.adapter.TabFragmentAdapter;
import com.shade.journey.view.DoneTripFragment;
import com.shade.journey.view.PlatformMsgFragment;
import com.shade.journey.view.SystemMsgFragment;
import com.shade.journey.view.WaitingTripFragment;

import java.util.ArrayList;
import java.util.List;

public class MyTripActivity extends AppCompatActivity {

    private Context context;
    private QMUITabSegment tabSegment;
    private ViewPager myViewPager;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
        context = this;

        /**
         * 标题栏
         * */
        QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.myTripTitle);
        titleBar.setTitle("我的行程");
        QMUIAlphaImageButton leftBack =
                titleBar.addLeftImageButton(R.drawable.title_back, R.id.qmui_topbar_item_left_back);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * 实现横向滑动导航页
         * */
        tabSegment = (QMUITabSegment) findViewById(R.id.tripTab);
        myViewPager = findViewById(R.id.tripPager);

        fragments.add(new WaitingTripFragment());
        fragments.add(new DoneTripFragment());

        //将碎片中的数据通过FragmentPagerAdapter展示到viewPager控件中
        myViewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), fragments));
        myViewPager.setCurrentItem(0, false);

        tabSegment.addTab(new QMUITabSegment.Tab("待出行"));
        tabSegment.addTab(new QMUITabSegment.Tab("已完成"));
        tabSegment.setupWithViewPager(myViewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSegment.setIndicatorWidthAdjustContent(false);

        /**
         * Tab监听事件
         * */
        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {//mTabSegment选项被选中的监听
            /**
             * 当某个 Tab 被选中时会触发
             *
             * @param index 被选中的 Tab 下标
             */
            @Override
            public void onTabSelected(int index) {

            }

            /**
             * 当某个 Tab 被取消选中时会触发
             *
             * @param index 被取消选中的 Tab 下标
             */
            @Override
            public void onTabUnselected(int index) {

            }

            /**
             * 当某个 Tab 处于被选中状态下再次被点击时会触发
             *
             * @param index 被再次点击的 Tab 下标
             */
            @Override
            public void onTabReselected(int index) {
                tabSegment.hideSignCountView(index);
            }

            /**
             * 当某个 Tab 被双击时会触发
             *
             * @param index 被双击的 Tab 下标
             */
            @Override
            public void onDoubleTap(int index) {

            }
        });
    }
}