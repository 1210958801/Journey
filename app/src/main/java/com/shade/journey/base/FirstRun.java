package com.shade.journey.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.shade.journey.activities.BootPageActivity;
import com.shade.journey.activities.SplashActivity;

public class FirstRun extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断用户是否第一次登陆
        SharedPreferences firstLog = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = firstLog.getBoolean("isFirstRun", true);
        Editor editor = firstLog.edit();
        if (isFirstRun) {
            Log.e("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(FirstRun.this, BootPageActivity.class);
            startActivity(intent);
        } else {
            Log.e("debug", "不是第一次运行");
            Intent intent = new Intent();
            intent.setClass(FirstRun.this, SplashActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
