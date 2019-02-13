package com.shade.journey.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.shade.journey.R;
import com.shade.journey.adapter.QDRecyclerViewAdapter;

public class IndividualShowActivity extends AppCompatActivity {

    private static final String TAG = "CollapsingTopBarLayout";

    QDRecyclerViewAdapter mRecyclerViewAdapter;
    LinearLayoutManager mPagerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_show);

        /**
         * 设置标题
         * */
        QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.topbar);
        QMUIAlphaImageButton leftBack =
                titleBar.addLeftImageButton(R.drawable.title_back, R.id.qmui_topbar_item_left_back);
        QMUIAlphaImageButton right =
                titleBar.addRightImageButton(R.drawable.user, R.id.qmui_topbar_item_left_back);
        //标题点击事件
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入修改用户信息界面
                Intent intent =
                        new Intent(IndividualShowActivity.this, IndividualDataActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 下方展示图片
         * */
        QMUICollapsingTopBarLayout mCollapsingTopBarLayout = findViewById(R.id.collapsing_topbar_layout);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "shade/journey/picture/aaa.jpg";
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bm);
        mCollapsingTopBarLayout.setTitle("用户昵称");

        /**
         * 数据展示区
         * */
        mPagerLayoutManager = new LinearLayoutManager(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mPagerLayoutManager);
        mRecyclerViewAdapter = new QDRecyclerViewAdapter();
        mRecyclerViewAdapter.setItemCount(1);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mCollapsingTopBarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "scrim: " + animation.getAnimatedValue());
            }
        });
    }

}
