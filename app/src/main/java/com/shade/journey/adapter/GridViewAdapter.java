package com.shade.journey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shade.journey.R;
import com.shade.journey.base.MainConstant;

import java.util.List;


/**
 * @Author:Liangzy(Shade)
 * @Date:Create in 2018/12/picture_4 picture_4:40 PM
 * @Description:添加上传图片的适配器
 */

public class GridViewAdapter extends android.widget.BaseAdapter {
    
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView 
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            //RequestOptions.overrideOf(300,300);
            Glide.with(mContext)
                    .load(picUrl)
                    .into(iv);
        } else {
            iv.setImageResource(R.mipmap.find_add_img);//最后一个显示加号图片
        }
        return convertView;
    }
}  
