package com.wizeyes.app2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.wizeyes.app2.bean.ShareApp1Bean;
import com.wizeyes.app2.bean.SharePaletteDataBean;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    /**
     * 分享 App1 scheme
     */
    public static final String SCHEME_APP1_URL = "colorshareapp1://publicapi/entry";
    /**
     * 分享 App1 scheme 传输数据名称
     */
    public static final String SCHEME_APP1_PARAMS_NAME = "?data=";
    /**
     * 分享数据类型-1-色采分享数据(模拟)
     */
    public static final int SCHEME_APP1_TYPE = 1;
    /**
     * 分享scheme 传输数据名称
     */
    private static final String SHARE_PARAMS_DATA = "data";

    private final Gson gson = new Gson();
    /**
     * 模拟色采-色卡数据
     */
    private final SharePaletteDataBean sharePaletteDataBean = new SharePaletteDataBean(
            "维多利亚的秘密",
            Arrays.asList("#EB6896", "#C782C4", "#6A0E2E", "#C36894", "#FCE4EC")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp1();
            }
        });
    }

    /**
     * 分享数据到App1 Scheme （模拟色采分享）
     */
    public void shareApp1() {
        try {
            ShareApp1Bean<SharePaletteDataBean> shareApp1Bean =
                    new ShareApp1Bean<>(SCHEME_APP1_TYPE, sharePaletteDataBean);
            String data = gson.toJson(shareApp1Bean);
            String sourceUrl = SCHEME_APP1_URL + SCHEME_APP1_PARAMS_NAME + Uri.encode(data);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
            startActivity(intent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            activityNotFoundException.printStackTrace();
            ToastUtils.showShort(R.string.uninstall_app1);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getString(R.string.share_app1_error, e.getMessage()));
        }
    }

}