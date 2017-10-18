package com.example.guhugang.example.guhugang.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.guhugang.example.guhugang.uploadfileservice.UploadService;


/**
 * Created by GuHuGang on 2017/9/19.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    private final String tag = "WIFI链接状况";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                // 当前WIFI名称
                Log.i(tag, "连接到WIFI " + wifiInfo.getSSID());
            }
        }

        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                Log.i(tag, "WIFI关闭");
            }

            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Log.i(tag, "WIFI开启");
                context.startService(new Intent(context,UploadService.class));
            }
        }
    }

}