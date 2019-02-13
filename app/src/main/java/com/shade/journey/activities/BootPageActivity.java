package com.shade.journey.activities;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.shade.journey.R;
import com.shade.journey.adapter.VpAdapter;
import com.zhouwei.indicatorview.CircleIndicatorView;

import java.util.ArrayList;

public class BootPageActivity extends Activity implements ViewPager.OnPageChangeListener {

    //ViewPager图片载物
    private ViewPager mViewPager;
    //图片指示器对象
    private CircleIndicatorView mIndicatorView;
    //静态资源
    private static int[] imgs = {R.drawable.one, R.drawable.two, R.drawable.three};
    //用于包含引导页要显示的图片
    private ArrayList<ImageView> imageViews;
    //适配器对象
    private VpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_page);

        //开启BootStrap
        TypefaceProvider.registerDefaultIconSets();
        //获取显示图片的控件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initImages();
        //初始化适配器
        vpAdapter = new VpAdapter(imageViews);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOnPageChangeListener(this);
        //初始化引导页下方的圆点
        mIndicatorView = (CircleIndicatorView) findViewById(R.id.indicator_view);
        // 关联ViewPager
        mIndicatorView.setUpWithViewPager(mViewPager);
    }

    /**
     * 把引导页要显示的图片添加到集合中，以传递给适配器，用来显示图片。
     */
    private void initImages() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//设置每一张图片都填充窗口
        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            iv.setImageResource(imgs[i]);//为Imageview添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片拉伸效果
            imageViews.add(iv);
            if (i == imgs.length - 1)//为最后一张添加点击事件
            {
                iv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Intent intent = new Intent();
                        ComponentName componentName =
                                new ComponentName(BootPageActivity.this,
                                        "com.shade.journey.activities.HomePageActivity");
                        intent.setComponent(componentName);
                        startActivity(intent);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
