package com.shade.journey.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shade.journey.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/11/26 picture_5:49 PM
 * @Description:从服务器中获取图片
 */
public class OkHttpUtil {

    //定义Bitmap对象
    Bitmap bitmap;

    /**
     * 获取服务器中的图片展示到控件中
     * Method Of Work:通过OkHTTP3读取url路径，然后解析图片资源得到图片流，
     * 然后通过${@link BitmapFactory}解析流之后设置到控件上
     * use: 用在给URL解析成图片直接设置到控件上的场景
     *
     * @param url、handler
     */
    public void getImageForControl(String url, final Handler handler) {

        OkHttpClient ok = new OkHttpClient();
        Request build = new Request.Builder()
                .url(url)
                .build();
        ok.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到图片的流
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 通过解析URL得到图片
     * Method Of Work:通过OkHTTP3读取url路径，然后解析图片资源得到图片流，然后通过${@link BitmapFactory}解析流
     * use: 用在给URL解析成图片返回的场景
     *
     * @param url
     * @return Bitmap
     */
    public Bitmap getBitmapImage(String url) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到流
                InputStream inputStream = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            }
        });
        return bitmap;
    }
}
