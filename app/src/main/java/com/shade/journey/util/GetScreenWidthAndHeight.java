package com.shade.journey.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/11/29 7:31 PM
 * @Description:获取屏幕的宽度和高度
 */
public class GetScreenWidthAndHeight {

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
