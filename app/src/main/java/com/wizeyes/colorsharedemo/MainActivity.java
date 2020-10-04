package com.wizeyes.colorsharedemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.wizeyes.colorsharedemo.bean.ShareColorClockDataBean;
import com.wizeyes.colorsharedemo.bean.SharePaletteDataBean;

public class MainActivity extends AppCompatActivity {
    /**
     * 分享 色采时钟 scheme
     */
    public static final String SCHEME_COLOR_CLOCK_URL = "colorclock://publicapi/entry";
    /**
     * 分享 色采时钟 scheme 传输数据名称
     */
    public static final String SCHEME_COLOR_CLOCK_PARAMS_NAME = "?data=";
    /**
     * 分享 色采时钟 scheme 数据类型
     */
    public static final int SCHEME_COLOR_CLOCK_TYPE = 2;
    /**
     * gson
     */
    private final Gson gson = new Gson();
    /**
     * logger TextView
     */
    private TextView logger;
    /**
     * SharePaletteDataBean
     */
    private final SharePaletteDataBean sharePaletteDataBean = new SharePaletteDataBean(
            "#FFFFFF",
            "#FFFFFF",
            "#F7F622",
            "#101010",
            "#101010"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger = findViewById(R.id.logger);
        findViewById(R.id.share_color_clock).setOnClickListener((view) -> shareColorClock());
        findViewById(R.id.share_color_clock_system).setOnClickListener((view) -> shareColorClockSystem());

        getColorCaptureShareText();

    }

    /**
     * 获取色采分享数据
     */
    private void getColorCaptureShareText() {
        if (getIntent().getAction().equals(Intent.ACTION_SEND)) {
            String sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            String sharedTitle = getIntent().getStringExtra(Intent.EXTRA_TITLE);
            StringBuilder loggerContentBuilder = new StringBuilder(getString(R.string.logger_prefix));
            loggerContentBuilder.append("title - ").append(sharedTitle).append("\n").append("content - ").append(sharedText);
            logger.setText(loggerContentBuilder.toString());
        }
    }

    /**
     * 分享数据到色采时钟 Scheme
     */
    public void shareColorClock() {
        try {
            ShareColorClockDataBean<SharePaletteDataBean> shareColorClockDataBean =
                    new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, sharePaletteDataBean);
            String data = gson.toJson(shareColorClockDataBean);
            String sourceUrl = SCHEME_COLOR_CLOCK_URL + SCHEME_COLOR_CLOCK_PARAMS_NAME + Uri.encode(data);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
            startActivity(intent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            activityNotFoundException.printStackTrace();
            ToastUtils.showShort(R.string.uninstall_color_clock_app);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getString(R.string.share_color_clock_error, e.getMessage()));
        }
    }

    /**
     * 分享数据到色采时钟 系统API
     */
    public void shareColorClockSystem() {
        try {
            ShareColorClockDataBean<SharePaletteDataBean> shareColorClockDataBean =
                    new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, sharePaletteDataBean);
            String data = gson.toJson(shareColorClockDataBean);
            shareText(data);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getString(R.string.share_color_clock_error, e.getMessage()));
        }
    }

    /**
     * 系统分享-文本
     */
    public void shareText(String text) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                text
        );
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(
                shareIntent,
                "Share Title"
        );
        startActivity(shareIntent);
    }
}