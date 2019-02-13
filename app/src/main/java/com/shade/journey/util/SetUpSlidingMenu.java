package com.shade.journey.util;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shade.journey.R;
import com.shade.journey.activities.HomePageActivity;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/11/24 1:06 AM
 * @Description:用来设置SlidingMenu的属性的。
 */
public class SetUpSlidingMenu extends HomePageActivity {

    private SlidingMenu slidingMenu;

    public void setUp(HomePageActivity homePageActivity) {

        slidingMenu = new SlidingMenu(homePageActivity);
        //设置从左弹出/滑出SlidingMenu
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置占满屏幕
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //绑定到哪一个Activity对象
        slidingMenu.attachToActivity(homePageActivity, SlidingMenu.SLIDING_CONTENT);
        //设置弹出的SlidingMenu的布局文件
        slidingMenu.setMenu(R.layout.about_me_menu);
        //设置SlidingMenu所占的偏移
        slidingMenu.setBehindWidthRes(R.dimen.behind_width);
        //设置滑动时菜单的是否淡入淡出
        slidingMenu.setFadeEnabled(true);
        //设置淡入淡出的比例
        slidingMenu.setFadeDegree(0.4f);
        //设置滑动时拖拽效果
        slidingMenu.setBehindScrollScale(0);
        //SlidingMenu滑动时的渐变程度
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setFadeEnabled(true);
    }

    public SlidingMenu getSlidingMenu() {
        return slidingMenu;
    }

    public void setSlidingMenu(SlidingMenu slidingMenu) {
        this.slidingMenu = slidingMenu;
    }
}
