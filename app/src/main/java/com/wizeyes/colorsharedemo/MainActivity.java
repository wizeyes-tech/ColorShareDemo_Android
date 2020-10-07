package com.wizeyes.colorsharedemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.wizeyes.colorsharedemo.bean.ShareColorClockDataBean;
import com.wizeyes.colorsharedemo.bean.ShareDataInfo;
import com.wizeyes.colorsharedemo.bean.SharePaletteDataBean;
import com.wizeyes.colorsharedemo.bean.ShareThirdDataBean;
import com.wizeyes.colorsharedemo.utils.CommonUtils;

public class MainActivity extends AppCompatActivity {
    /**
     * 接收数据参数 - start
     */
    /**
     * 接收分享数据 - scheme 传输数据名称
     */
    private static final String SHARE_PARAMS_DATA = "data";
    /**
     * 接收色采分享 - 色卡数据 默认类型
     */
    private static final int SHARE_TYPE_DEFAULT_COLOR_CAPTURE = 1;
    /**
     * 接收数据参数 - end
     */

    /**
     * 分享数据参数 - start
     */
    /**
     * 分享 色采时钟 scheme
     */
    public static final String SCHEME_COLOR_CLOCK_URL = "colorclock://publicapi/entry";
    /**
     * 分享 色采时钟 scheme 传输数据名称
     */
    public static final String SCHEME_COLOR_CLOCK_PARAMS_NAME = "?data=";
    /**
     * 分享数据类型 色采时钟 scheme - 第三方应用接入类型：2
     */
    public static final int SCHEME_COLOR_CLOCK_TYPE = 2;
    /**
     * ShareThirdDataBean - 第三方分享到色采时钟的数据结构
     */
    private final ShareThirdDataBean shareThirdDataBean = new ShareThirdDataBean(
            "#FFFFFF",
            "#FFFFFF",
            "#F7F622",
            "#101010",
            "#101010"
    );
    /**
     * 分享数据参数 - end
     */

    /**
     * gson
     */
    private final Gson gson = new Gson();
    /**
     * logger TextView
     */
    private TextView logger;
    /**
     * Toolbar
     */
    private Toolbar toolbar;
    /**
     * 页面布局
     */
    private LinearLayout page;
    /**
     * 分享到色采时钟 按钮
     */
    private TextView shareColorClock;
    /**
     * 系统分享到色采时钟 按钮
     */
    private TextView shareColorClockSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger = findViewById(R.id.logger);
        toolbar = findViewById(R.id.toolbar);
        page = findViewById(R.id.page);
        shareColorClock = findViewById(R.id.share_color_clock);
        shareColorClockSystem = findViewById(R.id.share_color_clock_system);

        findViewById(R.id.share_color_clock).setOnClickListener((view) -> shareColorClock());
        findViewById(R.id.share_color_clock_system).setOnClickListener((view) -> shareColorClockSystem());

        getShareInfo();
    }

    /**
     * 接收分享数据 - start
     */
    /**
     * 接收分享数据
     */
    private void getShareInfo() {
        //接收 色采App 分享的数据
        Intent mIntent = getIntent();
        Uri mUri = mIntent.getData();
        String mIntentAction = mIntent.getAction();
        if (!StringUtils.isEmpty(mIntentAction) && mIntentAction.equals(Intent.ACTION_SEND)) {
            shareByAction(mIntent);
        } else if (mUri != null) {
            shareByScheme(mUri);
        }
    }

    /**
     * 接收色采分享的色卡数据 - Intent->Intent.ACTION_SEND
     */
    private void shareByAction(Intent mIntent) {
        String sharedText = mIntent.getStringExtra(Intent.EXTRA_TEXT);
        SharePaletteDataBean sharePaletteDataBean = gson.fromJson(sharedText, SharePaletteDataBean.class);
        refreshUI(sharePaletteDataBean);
        logger.setText(String.format("%s content - %s", getString(R.string.logger_prefix), sharedText));
    }

    /**
     * 接收色采分享的色卡数据 - Scheme
     *
     * @param mUri
     */
    private void shareByScheme(Uri mUri) {
        try {
            String data = mUri.getQueryParameter(SHARE_PARAMS_DATA);
            data = Uri.decode(data);
            ShareDataInfo shareDataInfo = gson.fromJson(data, ShareDataInfo.class);
            //这里的type字段是为了兼容后期数据结构改变
            if (shareDataInfo.type == SHARE_TYPE_DEFAULT_COLOR_CAPTURE) {
                SharePaletteDataBean sharePaletteDataBean = gson.fromJson(shareDataInfo.data,
                        SharePaletteDataBean.class);
                refreshUI(sharePaletteDataBean);
                logger.setText(String.format("%s content - %s", getString(R.string.logger_prefix), shareDataInfo.data));
            } else {
                ToastUtils.showShort(getString(R.string.share_color_clock_error,
                        "未知数据类型-type-" + shareDataInfo.type));
            }
        } catch (Exception e) {
            ToastUtils.showShort(getString(R.string.share_color_clock_error, e.getMessage()));
        }
    }
    /**
     * 接收分享数据 - end
     */

    /**
     * 发生分享数据 - start
     */
    /**
     * 分享数据到色采时钟 - Scheme
     */
    public void shareColorClock() {
        try {
            ShareColorClockDataBean<ShareThirdDataBean> shareColorClockDataBean =
                    new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, shareThirdDataBean);
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
     * 分享数据到色采时钟 - 系统API
     */
    public void shareColorClockSystem() {
        try {
            ShareColorClockDataBean<ShareThirdDataBean> shareColorClockDataBean =
                    new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, shareThirdDataBean);
            String data = gson.toJson(shareColorClockDataBean);
            shareText(data);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getString(R.string.share_system_error, e.getMessage()));
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
                getString(R.string.share_title)
        );
        startActivity(shareIntent);
    }
    /**
     * 发生分享数据 - end
     */

    /**
     * 刷新UI
     *
     * @param sharePaletteDataBean
     */
    private void refreshUI(SharePaletteDataBean sharePaletteDataBean) {
        int toolbarColor = Color.parseColor(sharePaletteDataBean.colors.get(1));
        toolbar.setBackgroundColor(toolbarColor);
        shareColorClock.setBackgroundColor(Color.parseColor(sharePaletteDataBean.colors.get(0)));
        shareColorClockSystem.setBackgroundColor(Color.parseColor(sharePaletteDataBean.colors.get(0)));
        shareColorClock.setTextColor(Color.parseColor(sharePaletteDataBean.colors.get(2)));
        shareColorClockSystem.setTextColor(Color.parseColor(sharePaletteDataBean.colors.get(4)));
        logger.setTextColor(Color.parseColor(sharePaletteDataBean.colors.get(3)));
        if (CommonUtils.isLightColor(toolbarColor)) {
            toolbar.setTitleTextColor(Color.DKGRAY);
        } else {
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }

}