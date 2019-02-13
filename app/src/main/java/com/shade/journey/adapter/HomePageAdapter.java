package com.shade.journey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shade.journey.R;
import com.shade.journey.entity.TestBean;
import com.shade.journey.util.MakePicUtil;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2019/1/16 picture_5:56 PM
 * @Description:
 */
public class HomePageAdapter extends HelperRecyclerViewAdapter<TestBean> {

    //构造 布局直接通过在构造方法中设置（推荐使用方式），数据集合通过setListAll设置
    public HomePageAdapter(Context context) {
        super(context, R.layout.home_page_item);
    }

    //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, TestBean item) {
        final TestBean testBean = getData(position);

        /****2.view赋值*****/
        //方式一：采用链式的设计的书写方式，一点到尾。（方式一）
        viewHolder.setText(R.id.text, testBean.getName())
                .setImageResource(R.id.image, MakePicUtil.makePic(position))
                /* .setVisible(R.id.text,true);//设置某个view是否可见*/
                .setOnClickListener(R.id.image, new View.OnClickListener() {//点击事件
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "我是子控件" + testBean.getName() + "请看我如何处理View点击事件的", Toast.LENGTH_LONG).show();
                    }
                });

        TextView textView = viewHolder.getView(R.id.text2);
        textView.setText(testBean.getAge());
        if (isEmpty()) {
            Log.d("Debug", "暂无数据!!!");
        }
    }
}
