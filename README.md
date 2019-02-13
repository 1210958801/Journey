### 标题栏APP展示的地方为{每一个活动中都有}
      布局文件：注：id需要修改
       <com.qmuiteam.qmui.widget.QMUITopBar
               android:id="@+id/individualTitle"
               android:layout_width="match_parent"
               android:layout_height="50dp"
               app:qmui_topbar_bg_color="@color/DeepSkyBlue" />
       Java类中:titleBar.setTitle("");里边写Title名字/QMUITopBar titleBar = (QMUITopBar) findViewById(R.id.individualTitle);R.id.*。需要改成和布局文件相同的
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
        ###注：需要实现右侧按钮的时候使用下方代码
        R.string.oneKeyFullRead 这个是我在字符文件当中写的<string name="oneKeyFullRead">全部已读</string>
        Button button =
                        titleBar.addRightTextButton(R.string.oneKeyFullRead, R.id.qmui_dialog_edit_right_icon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MsgCenterActivity.this, "全部已读", Toast.LENGTH_SHORT).show();
            }
        });
### 按钮（用的是Android_BootStrap APP展示的地方为{个人中心的提交那妞}）
    首先要在onCreate()方法当中使用TypefaceProvider.registerDefaultIconSets();开启BootStrap组件
    布局文件中
        <com.beardedhen.androidbootstrap.BootstrapButton
                    android:layout_width="200dp"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center"
                    android:text="@string/submit"
                    android:layout_marginTop="@dimen/fbutton_default_padding_left"
                    app:bootstrapBrand="success"
                    app:bootstrapSize="lg"
                    app:buttonMode="regular"
                    app:roundedCorners="true"
                    app:showOutline="false" />
    配置的属性参看:https://github.com/Bearded-Hen/Android-Bootstrap
### 输入框(前端中的input框:APP展示的地方为{发布动态中的输入标题})
    布局文件中：
        <com.rengwuxian.materialedittext.MaterialEditText
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="请输入标题"
                    app:met_baseColor="@color/fbutton_default_shadow_color"
                    app:met_clearButton="true"
                    app:met_errorColor="@color/fbutton_color_pomegranate"
                    app:met_maxCharacters="15"
                    app:met_minCharacters="0" />
        更多配置请看：https://github.com/rengwuxian/MaterialEditText