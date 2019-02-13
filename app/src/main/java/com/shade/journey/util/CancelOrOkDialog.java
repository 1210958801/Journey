package com.shade.journey.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shade.journey.R;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_4 picture_4:40 PM
 * @Description:取消或者确认类型的Dialog
 */

public class CancelOrOkDialog extends Dialog {

    public CancelOrOkDialog(Context context, String title) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.dialog_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);

        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                //取消
                cancel();
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                ok();
            }
        });
    }

    //确认
    public void ok() {
    }
}
