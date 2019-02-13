package com.shade.journey.activities;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.shade.journey.R;
import com.shade.journey.base.ItemGroup;

import com.shade.journey.entity.AreaEntity;
import com.shade.journey.util.OkHttpUtil;
import com.shade.journey.util.GetJsonString;

import org.json.JSONArray;

import de.hdodenhof.circleimageview.CircleImageView;
import zhangyf.vir56k.androidimageblur.BlurUtil;

public class IndividualDataActivity extends AppCompatActivity implements ItemGroup.ItemOnClickListener {

    private static final String TAG = "ItemGroupActivity";
    private Context mContext;
    private ItemGroup userNameIG, sexIG, dateIG, cityIG, tradeIG;
    //性别选择
    private String[] sexArry = new String[]{"女", "男"};
    //省
    private ArrayList<AreaEntity> options1Items = new ArrayList<>();
    //市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    //行业
    private ArrayList<String> tradeList = new ArrayList<>();
    //引入插件的实例
    private OptionsPickerView cityOptions;
    private OptionsPickerView tradeOptions;
    //圆形头像对象
    private CircleImageView circleImageView;
    //高斯模糊对象
    private ImageView back;

    private Handler circleHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            circleImageView.setImageBitmap(bitmap);//将图片的流转换成图片
        }
    };

    private Handler blurHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            //模糊算法
            Bitmap newImg = BlurUtil.doBlur(bitmap, 10, 3);
            back.setImageBitmap(newImg);//将图片的流转换成图片
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_individual_data);
        //初始化圆形图像和模糊背景
        back = (ImageView) findViewById(R.id.h_back);
        circleImageView = (CircleImageView) findViewById(R.id.h_head);

        /**
         * 实现标题栏
         * */
        QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.individualTitle);
        titleBar.setTitle("个人中心");
        QMUIAlphaImageButton leftBack =
                titleBar.addLeftImageButton(R.drawable.title_back, R.id.qmui_topbar_item_left_back);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * 实现用户头像
         * 首先查看本地是否存在图片，如果不存在则需要从服务器中拉取下来缓冲到本地
         * 如果存在于本地，则直接用本地缓冲的图片
         * */
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "shade/journey/picture/userImage.jpg";
        File file = new File(filePath);
        if (!file.exists()) {
            String headImageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545381120208&di=115402b5db51fc04f9689138c36e66b1&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Ff9198618367adab492c1056a80d4b31c8701e482.jpg";
            getRoundAndBackHead(headImageUrl);
            //TODO 然后将服务器上的图片缓冲到本地

        } else {
            Bitmap bm = BitmapFactory.decodeFile(filePath);
            circleImageView.setImageBitmap(bm);
            Bitmap newImg = BlurUtil.doBlur(bm, 10, 3);
            back.setImageBitmap(newImg);
        }
        //实现个人信息列表
        mContext = this;
        initView();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现更换头像
                imageConfig();
            }
        });
    }

    /**
     * 从服务器中实现圆形头像和高斯模糊背景
     */
    private void getRoundAndBackHead(String headImageUrl) {
        //引用Util
        OkHttpUtil util = new OkHttpUtil();
        //实现圆形头像
        util.getImageForControl(headImageUrl, circleHandler);
        //实现高斯模糊
        util.getImageForControl(headImageUrl, blurHandler);
    }

    /**
     * 实现信息列表
     */
    private void initView() {
        //昵称
        userNameIG = (ItemGroup) findViewById(R.id.userName);
        sexIG = (ItemGroup) findViewById(R.id.select_sex);
        dateIG = (ItemGroup) findViewById(R.id.select_birthday_ig);
        cityIG = (ItemGroup) findViewById(R.id.select_city_ig);
        tradeIG = (ItemGroup) findViewById(R.id.trade);
        tradeIG.setItemOnClickListener(this);
        sexIG.setItemOnClickListener(this);
        dateIG.setItemOnClickListener(this);
        cityIG.setItemOnClickListener(this);
        //TODO 提交的方式为上方的title返回按钮和系统的back
    }

    @Override
    public void onItemClick(View v) {
        switch (v.getId()) {
            case R.id.select_sex:
                //实现选择性别
                showSexChooseDialog();
                break;
            case R.id.select_birthday_ig:
                //实现选择时间
                TimePickerView pvTime = new TimePickerBuilder
                        (IndividualDataActivity.this, new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                SimpleDateFormat dateFormat
                                        = new SimpleDateFormat("yyyy年MM月dd日");
                                dateIG.setText(dateFormat.format(date));
                            }
                        }).build();
                pvTime.show();
                break;
            case R.id.select_city_ig:
                //实现选择城市
                initCityData();
                showCityData();
                break;
            case R.id.trade:
                Log.e(TAG, "点击了");
                stringArrToList();
                showSelectTrade();
                break;
        }
    }

    /**
     * 实现性别选择框
     */
    private void showSexChooseDialog() {
        final int checkedIndex = 1;
        new QMUIDialog.CheckableDialogBuilder(this)
                .setCheckedIndex(checkedIndex)
                .addItems(sexArry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sexIG.setText(sexArry[which]);
                    }
                }).show();
    }

    /**
     * 解析数据省市区三级联动JSON
     */
    private void initCityData() {
        //获取assets目录下的json文件数据
        String JsonData = GetJsonString.getJson(this, "area.json");
        //用Gson 转成实体
        ArrayList<AreaEntity> jsonBean = parseCityData(JsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        //遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            //该省的城市列表（第二级）
            ArrayList<String> CityList = new ArrayList<>();
            //该省的所有地区列表（第三级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();
            //遍历该省份的所有城市
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                //添加城市
                CityList.add(CityName);
                //该城市的所有地区列表
                ArrayList<String> City_AreaList = new ArrayList<>();
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                //添加该省所有地区数据
                Province_AreaList.add(City_AreaList);
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    /**
     * 三级行政区划展示选择器
     */
    private void showCityData() {
        // 弹出选择器
        cityOptions = new OptionsPickerBuilder
                (this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String prov = options1Items.get(options1).getPickerViewText();
                        String city = options2Items.get(options1).get(options2);
                        String cou = options3Items.get(options1).get(options2).get(options3);
                        //拼接成形成区划
                        if (prov.equals(city)) {
                            cityIG.setText(prov + "/" + cou);
                        } else {
                            cityIG.setText(prov + "/" + city + "/" + cou);
                        }

                    }
                })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isRestoreItem(false)
                //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //三级选择器
        cityOptions.setPicker(options1Items, options2Items, options3Items);
        cityOptions.show();
    }

    /**
     * 行业展示选择器
     */
    private void showSelectTrade() {
        // 弹出选择器
        tradeOptions = new OptionsPickerBuilder
                (this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回定义的每一个name
                        String tradeName = tradeList.get(options1);
                        Log.e(TAG, "消息" + options1);
                        Log.e(TAG, "消息1" + tradeName);
                        Log.e(TAG, "消息" + options2);
                        Log.e(TAG, "消息" + options3);
                        tradeIG.setText(tradeName);
                    }

                })
                .setDividerColor(Color.BLACK)
                .setOutSideCancelable(true)
                //设置选中项文字颜色
                .setContentTextSize(20)
                .isDialog(true)//是否显示为对话框样式
                .build();
        //一级选择器
        tradeOptions.setPicker(tradeList);
        tradeOptions.show();
    }

    /**
     * 返回三级行程区划的集合
     */
    public ArrayList<AreaEntity> parseCityData(String result) {
        ArrayList<AreaEntity> detail = new ArrayList<>();
        try {
            JSONArray cityData = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < cityData.length(); i++) {
                AreaEntity entity = gson.fromJson(cityData.optJSONObject(i).toString(), AreaEntity.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 转化为List数组
     */
    public ArrayList<String> stringArrToList() {
        tradeList.add("农、林、牧、渔业");
        tradeList.add("采矿业");
        tradeList.add("制造业");
        tradeList.add("电力、燃气及水的生产和供应业");
        tradeList.add("建筑业");
        tradeList.add("交通运输、仓储和邮政业");
        tradeList.add("信息传输、计算机服务和软件业");
        tradeList.add("批发和零售业");
        tradeList.add("住宿和餐饮业");
        tradeList.add("金融业");
        tradeList.add("租赁和商务服务业");
        tradeList.add("房地产业");
        tradeList.add("租赁和商务服务业");
        tradeList.add("科学研究、技术服务和地质勘查业");
        tradeList.add("水利、环境和公共设施管理业");
        tradeList.add("居民服务和其他服务业");
        return tradeList;
    }

    public void imageConfig() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(210, 210)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia local : localMedia) {
                        //照片压缩路径
                        String compressPath = local.getCompressPath();
                        Log.d("Debug", "文件地址:" + compressPath);
                        File file = new File(compressPath);
                        //圆形头像
                        circleImageView = (CircleImageView) findViewById(R.id.h_head);
                        //背景
                        back = (ImageView) findViewById(R.id.h_back);
                        if (file.exists()) {
                            /**
                             * 将压缩的照片显示在控件上
                             * */
                            Bitmap bm = BitmapFactory.decodeFile(compressPath);
                            circleImageView.setImageBitmap(bm);
                            Bitmap newImg = BlurUtil.doBlur(bm, 10, 3);
                            back.setImageBitmap(newImg);
                            /**
                             * 将图片上传到服务器且将图片修改位置保存
                             * */
                            //将文件缓存在本地
                            String folderPath = Environment.getExternalStorageDirectory().getPath() + "/" + "shade/journey/picture";
                            String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "shade/journey/picture/userImage.jpg";
                            File folder = new File(folderPath);
                            if (!folder.exists()) {
                                Log.d("Debug", "文件夹创建成功");
                                folder.mkdirs();
                            }
                            //将压缩的图片复制到filePath路径下
                            copyFile(compressPath, filePath);
                            //TODO 将图片发送到服务器上
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 复制文件
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.d("Debug", "复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}