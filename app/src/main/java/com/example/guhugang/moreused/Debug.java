package com.example.guhugang.moreused;

import android.util.Log;

/**
 * Created by GuHuGang on 2017/12/18.
 */

public class Debug {
    private static int LOG_MAXLENGTH = 2000;
    public static void show(String TAG,String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.i(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.i(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }
}
