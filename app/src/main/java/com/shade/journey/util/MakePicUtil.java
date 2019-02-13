package com.shade.journey.util;

import com.shade.journey.R;

import java.util.Random;

/**
 * <p>描述：制造图片工具类</p>
 * 作者： zhouyou<br>
 * 日期： 2018/picture_4/10 14:26 <br>
 * 版本： v1.0<br>
 */
public class MakePicUtil {
    private static int[] pics = {R.mipmap.pic_1, R.mipmap.pic_2, R.mipmap.pic_3
            , R.mipmap.pic_4, R.mipmap.pic_5, R.mipmap.pic_6};

    public static int makePic() {
        return pics[new Random().nextInt(pics.length)];
    }

    public static int makePic(int position) {
        return pics[position % pics.length];
    }
}
