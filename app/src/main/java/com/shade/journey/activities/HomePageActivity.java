package com.shade.journey.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.shade.journey.R;
import com.shade.journey.adapter.HomePageAdapter;
import com.shade.journey.base.LocationCallBack;
import com.shade.journey.base.MapLocationHelper;
import com.shade.journey.entity.TestBean;
import com.shade.journey.util.GetScreenWidthAndHeight;
import com.shade.journey.util.OkHttpUtil;
import com.shade.journey.util.SetUpSlidingMenu;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends AppCompatActivity implements LocationCallBack {

    //侧划
    private SetUpSlidingMenu set;
    //点击头像
    //用户昵称
    private TextView userNameTextView = null;
    //用户昵称变量
    private String userName = null;
    //三个功能按钮
    private Button msg = null;
    private Button myTrip = null;
    private Button sysSetup = null;
    //记录上次点击的时间
    private long lastTime = 0;
    //定义LOG  tag
    private static final String TAG = "FloatWindow";
    //屏幕X/Y
    private int screenHeight;
    private int screenWidth;
    //圆形头像对象
    private CircleImageView circleImageView;
    //圆形头像的Header
    private Handler circleHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            circleImageView.setImageBitmap(bitmap);//将图片的流转换成图片
        }
    };
    //EasyRecycleView
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private int times = 0;
    //图片地址
    String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "shade/journey/picture/userImage.jpg";
    //实现悬浮球
    private BoomMenuButton boomMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        obtainPermission();

        /**
         * 获取屏幕宽高
         * */
        screenHeight = GetScreenWidthAndHeight.getScreenHeight(this);
        screenWidth = GetScreenWidthAndHeight.getScreenWidth(this);

        /**
         * 实现悬浮球
         * */
        boomMenuButton = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    Intent intent =
                                            new Intent(HomePageActivity.this, DynamicsActivity.class);
                                    startActivity(intent);
                                default:
                                    break;
                            }
                        }
                    })
                    .normalImageRes(getImageResource())
                    .normalText(getext());
            boomMenuButton.addBuilder(builder);
        }

        /**
         * 实现展示定位信息
         * */
        MapLocationHelper helper = new MapLocationHelper(this, this);
        helper.startMapLocation();

        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);

        /**
         * 下拉刷新
         * */
        mRecyclerView.setLoadingListener(new com.zhouyou.recyclerview.XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e("test", "=======isRefreshing=======" + mRecyclerView.isRefreshing());
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        List<TestBean> list = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            String name = "刷新 姓名 张三" + i;
                            String age = "刷新 年龄：" + i;
                            TestBean testBean = new TestBean(name, age);
                            list.add(testBean);
                        }
                        /****讲解*****/
                        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                        //2.如果是addAll()追加
                        mAdapter.setListAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
//                        mRecyclerView.setLoadingMoreEnabled(true);
                    }

                }, 2000);            //refresh data here
            }

            /**
             * 上拉加载
             * */
            @Override
            public void onLoadMore() {
                Log.e("test", "=======onLoadMore=======" + mRecyclerView.isLoadingMore());
                if (times < 3) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //  mRecyclerView.loadMoreComplete();
                            List<TestBean> list = new ArrayList<TestBean>();
                            int size = mAdapter.getItemCount();//适配器中已有的数据
                            for (int i = 0; i < 10; i++) {
                                String name = "更多 姓名：张三" + (i + size);
                                String age = "更多 年龄：" + (i + size);
                                TestBean testBean = new TestBean(name, age);
                                list.add(testBean);
                            }

                            //mAdapter.notifyDataSetChanged();
                            //追加list.size()个数据到适配器集合最后面
                            //不需要 mAdapter.notifyDataSetChanged();
                            mAdapter.addItemsToLast(list);

                            mRecyclerView.refreshComplete();
                            mRecyclerView.setLoadingMoreEnabled(true);
                        }
                    }, 2000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setLoadingMoreEnabled(false);

                            //
                            Log.e("", "");
                            new Handler().postDelayed(new Runnable() {
                                public void run() {

                                    mAdapter.notifyDataSetChanged();
                                    mRecyclerView.loadMoreComplete();
                                    mRecyclerView.setLoadingMoreEnabled(true);

                                    //
                                    Log.e("", "");
                                }
                            }, 2000);
                        }
                    }, 2000);
                }
                times++;
            }
        });
        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<TestBean> listData = new ArrayList<TestBean>();

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        //这种方式一定要先setAdapter然后才setListAll（）设置数据
        mAdapter = new HomePageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        mAdapter.setListAll(listData);

        //方式一对应的初始化适配器
        //mAdapter = new MyAdapter(listData, this, R.layout.item);
        //方式二对应的初始化适配器
        //mAdapter = new MyAdapter(this, R.layout.item);


        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                Toast.makeText(getApplicationContext(), "我是item" + position, Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 实现左侧滑动
         * */
        set = new SetUpSlidingMenu();
        set.setUp(this);


        /**
         * 点击头像事件
         * */
        circleImageView = (CircleImageView) findViewById(R.id.headImage);
        //引用Util
        OkHttpUtil util = new OkHttpUtil();
        /**
         * 首先判断是否为登陆状态
         * 1:如果登陆了，要显示设置的用户头像
         * 0:如果没有登陆，则需要显示"登陆"头像、然后点击进入登陆功能
         * */
        File file = new File(filePath);
        if (!file.exists()) {
            String headImageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545381120208&di=115402b5db51fc04f9689138c36e66b1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Ff9198618367adab492c1056a80d4b31c8701e482.jpg";
            util.getImageForControl(headImageUrl, circleHandler);
            //TODO 然后将服务器上的图片缓冲到本地
        } else {
            Bitmap bm = BitmapFactory.decodeFile(filePath);
            circleImageView.setImageBitmap(bm);
        }
        //侧划圆形头像的点击事件
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(HomePageActivity.this, IndividualShowActivity.class);
                startActivity(intent);
            }
        });

        //显示用户昵称
        userNameTextView = (TextView) findViewById(R.id.userName);
        //获取用户的昵称
        userName = "Shade";
        userNameTextView.setText("" + userName);

        /**
         * 设置侧划的点击效果 TODO 待整理
         * */
        //消息中心
        msg = (Button) findViewById(R.id.msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先需要得到要切换的图片
                Drawable zhixiangRed =
                        getResources().getDrawable(R.drawable.zhixiang_right_red);
                zhixiangRed.setBounds
                        (0, 0, zhixiangRed.getMinimumWidth(), zhixiangRed.getMinimumHeight());
                Drawable xiaoxiRed =
                        getResources().getDrawable(R.drawable.xiaoxi_red);
                xiaoxiRed.setBounds
                        (0, 0, xiaoxiRed.getMinimumWidth(), xiaoxiRed.getMinimumHeight());
                msg.setCompoundDrawables(xiaoxiRed, null, zhixiangRed, null);
                Intent intent = new Intent(HomePageActivity.this, MsgCenterActivity.class);
                startActivity(intent);
            }
        });
        //我的行程
        myTrip = (Button) findViewById(R.id.mytrip);
        myTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先需要得到要切换的图片
                Drawable zhixiangRed =
                        getResources().getDrawable(R.drawable.zhixiang_right_red);
                zhixiangRed.setBounds
                        (0, 0, zhixiangRed.getMinimumWidth(), zhixiangRed.getMinimumHeight());
                Drawable myTripRed =
                        getResources().getDrawable(R.drawable.wodexingchen_red);
                myTripRed.setBounds
                        (0, 0, myTripRed.getMinimumWidth(), myTripRed.getMinimumHeight());
                myTrip.setCompoundDrawables(myTripRed, null, zhixiangRed, null);
                Intent intent = new Intent(HomePageActivity.this, MyTripActivity.class);
                startActivity(intent);
            }
        });
        //基本设置
        sysSetup = (Button) findViewById(R.id.setup);
        sysSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先需要得到要切换的图片
                Drawable zhixiangRed =
                        getResources().getDrawable(R.drawable.zhixiang_right_red);
                zhixiangRed.setBounds
                        (0, 0, zhixiangRed.getMinimumWidth(), zhixiangRed.getMinimumHeight());
                Drawable sysSetupRed =
                        getResources().getDrawable(R.drawable.shezhi_red);
                sysSetupRed.setBounds
                        (0, 0, sysSetupRed.getMinimumWidth(), sysSetupRed.getMinimumHeight());
                sysSetup.setCompoundDrawables(sysSetupRed, null, zhixiangRed, null);
                Intent intent = new Intent(HomePageActivity.this, BasicSetUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 当程序继续使用的时候将三个菜单恢复成白色
     */
    @Override
    protected void onResume() {
        super.onResume();
        Drawable zhixiang =
                getResources().getDrawable(R.drawable.zhixiang_right);
        zhixiang.setBounds
                (0, 0, zhixiang.getMinimumWidth(), zhixiang.getMinimumHeight());

        //消息中心回退事件
        Drawable xiaoxi =
                getResources().getDrawable(R.drawable.xiaoxi);
        xiaoxi.setBounds
                (0, 0, xiaoxi.getMinimumWidth(), xiaoxi.getMinimumHeight());
        msg.setCompoundDrawables(xiaoxi, null, zhixiang, null);
        //我的行程回退事件
        Drawable myTripWhite =
                getResources().getDrawable(R.drawable.wodexingchen);
        myTripWhite.setBounds
                (0, 0, myTripWhite.getMinimumWidth(), myTripWhite.getMinimumHeight());
        myTrip.setCompoundDrawables(myTripWhite, null, zhixiang, null);
        //系统设置回退事件
        Drawable sysSetupWhite =
                getResources().getDrawable(R.drawable.shezhi);
        sysSetupWhite.setBounds
                (0, 0, sysSetupWhite.getMinimumWidth(), sysSetupWhite.getMinimumHeight());
        sysSetup.setCompoundDrawables(sysSetupWhite, null, zhixiang, null);

        Bitmap bm = BitmapFactory.decodeFile(filePath);
        circleImageView.setImageBitmap(bm);
        Log.d("Debug", "更换完毕");
    }

    /**
     * 程序退出的方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - lastTime) > 2000) {
                Toast.makeText(HomePageActivity.this, "在按一次退出程序", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCallLocationSuc(AMapLocation location) {
        QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.homePageTitle);
        titleBar.setTitle("首页");
        titleBar.addLeftTextButton(location.getDistrict(), R.id.qmui_topbar_item_left_back);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mRecyclerView != null) {
            mRecyclerView.setRefreshing(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.ClifeIndicator);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 权限获取方法
     */
    public void obtainPermission() {
        MultiplePermissionsListener mMultiplePermissionListener =
                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                        .with(findViewById(android.R.id.content), "必须同意所有权限才能正常运行此程序")
                        .withOpenSettingsButton("设置")
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                            }

                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                            }
                        }).build();

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,//大概位置
                        Manifest.permission.ACCESS_FINE_LOCATION,//精确定位
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//写磁盘
                        Manifest.permission.READ_EXTERNAL_STORAGE,//读磁盘
                        Manifest.permission.READ_PHONE_STATE,//电话状态
                        Manifest.permission.INTERNET//网络许可
                ).withListener(mMultiplePermissionListener).check();
    }

    private static int index = 0;

    static String getext() {
        if (index >= text.length) index = 0;
        return text[index++];
    }

    private static String[] text = new String[]{"写游记"};
    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    private static int[] imageResources = new int[]{
            R.drawable.youji1,
    };
}