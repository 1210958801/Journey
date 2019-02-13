package com.shade.journey.activities;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.shade.journey.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicSetUpActivity extends AppCompatActivity {

    @BindView(R.id.sysSetUp)
    QMUIGroupListView mGroupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_set_up);

        //初始化ButterKnife
        ButterKnife.bind(this);

        /**
         * 设置标题栏
         * */
        QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.sysSetUpTitle);
        titleBar.setTitle("设置");
        QMUIAlphaImageButton leftBack =
                titleBar.addLeftImageButton(R.drawable.title_back, R.id.qmui_topbar_item_left_back);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * 开始设置ListItemView(个人设置)
         * */
        mGroupListView.newSection(this)
                .setTitle("个人设置")
                .addItemView(setItemStyle(0), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了个人设置_账号和绑定设置", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(setItemStyle(1), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了个人设置_一程平安", Toast.LENGTH_SHORT).show();
                    }
                })
                // 设置分隔线的样式
                .setUseDefaultTitleIfNone(false)
                .setUseTitleViewForSectionSpace(false)
                .addTo(mGroupListView);
        /**
         * 开始设置ListItemView(系统设置)
         * */
        mGroupListView.newSection(this)
                .setTitle("系统设置")
                .addItemView(setItemStyle(2), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了系统设置_意见反馈", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(setItemStyle(3), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了系统设置_检查更新", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(setItemStyle(4), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了系统设置_清理缓存", Toast.LENGTH_SHORT).show();
                    }
                })
                .addItemView(setItemStyle(5), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BasicSetUpActivity.this, "点击了系统设置_关于一程", Toast.LENGTH_SHORT).show();
                    }
                })
                // 设置分隔线的样式
                .setUseDefaultTitleIfNone(false)
                .setUseTitleViewForSectionSpace(false)
                .addTo(mGroupListView);

    }

    /**
     * @param index(统一通过下标获取Item的样式)
     * @return QMUICommonListItemView
     * @Description 为各个Item统一设置样式
     */
    public QMUICommonListItemView setItemStyle(int index) {
        switch (index) {
            case 0:
                QMUICommonListItemView accountNumBind = setUpItem(getResources().getDrawable(R.drawable.bangding),
                        "账号和绑定设置", null, 2, 2);
                return accountNumBind;
            case 1:
                QMUICommonListItemView safe = setUpItem(getResources().getDrawable(R.drawable.anquan),
                        "一程平安", "让最爱的人放心", 2, 2);
                return safe;
            case 2:
                QMUICommonListItemView opinion = setUpItem(getResources().getDrawable(R.drawable.fankui),
                        "意见反馈", "一程用心您费心", 2, 2);
                return opinion;
            case 3:
                QMUICommonListItemView checkUpdate = setUpItem(getResources().getDrawable(R.drawable.gengxin),
                        "检查更新", "最新的功能更好的体验", 2, 2);
                return checkUpdate;
            case 4:
                QMUICommonListItemView clearCache = setUpItem(getResources().getDrawable(R.drawable.clear),
                        "清理缓存", "你我点点滴滴", 2, 3);
                return clearCache;
            case 5:
                QMUICommonListItemView aboutJourney = setUpItem(getResources().getDrawable(R.drawable.women),
                        "关于一程", "小手一点增进了解", 2, 2);
                return aboutJourney;
        }
        return null;
    }

    /**
     * @param leftImage(左侧图片)title(当前Item的Title)detail(文字详情可为null)
     * @param detailOrientation(文字详情的展示方向)rightStyle(Item右边展示的样式)
     * @return QMUICommonListItemView
     * @Description 如果detailOrientation的顺序代码为：1、2相对应的位置是：下右 如果没有则是2
     * @Description 如果rightStyle的样式代码为:1、2、3相对应的样式为:不显示任何样式、箭头、开关 如果没有则是null(不推荐)
     */
    public QMUICommonListItemView setUpItem(Drawable leftImage, String title,
                                            String detail, int detailOrientation,
                                            int rightStyle) {
        if (detailOrientation == 1) {
            detailOrientation = QMUICommonListItemView.VERTICAL;
        } else {
            detailOrientation = QMUICommonListItemView.HORIZONTAL;
        }
        if (rightStyle == 1) {
            rightStyle = QMUICommonListItemView.ACCESSORY_TYPE_NONE;
        } else if (rightStyle == 2) {
            rightStyle = QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON;
        } else {
            rightStyle = QMUICommonListItemView.ACCESSORY_TYPE_SWITCH;
        }
        /**
         * 创建Item，并且创建样式
         * */
        QMUICommonListItemView itemView =
                mGroupListView.createItemView(leftImage, title, detail,
                        detailOrientation, rightStyle, 190);
        return itemView;
    }

}