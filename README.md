ColorShareDemo_Android
===
色采分享Demo：分享数据到色采时钟（Scheme分享、系统API）、接收色采分享的数据(系统API)

## 色采分享配色主题到第三方App


<img src="https://github.com/wizeyes-tech/ColorShareDemo_Android/blob/master/image/readme_color_capture_share.png?raw=true" width=40% hspace="10" /> <img src="https://raw.githubusercontent.com/wizeyes-tech/ColorShareDemo_Android/master/image/readme_color_capture_share2.png" width=40% hspace="10" />


## 色采分享配色主题Json

```
{
	"colors": ["#FFEDF5", "#FDCBE6", "#7C686A", "#FFA0B1", "#A56D74"],
	"name": "粉红之梦"
}
```

## 获取色采分析配色主题代码示例

```
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
```

## 分享到色采时钟代码示例

```
/**
 * 分享数据到色采时钟 Scheme
 */
public void shareColorClock() {
    try {
        ShareColorClockDataBean<SharePaletteDataBean> shareApp1Bean =
                new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, shareThirdDataBean);
        String data = gson.toJson(shareApp1Bean);
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
        ShareColorClockDataBean<SharePaletteDataBean> shareApp1Bean =
                new ShareColorClockDataBean<>(SCHEME_COLOR_CLOCK_TYPE, shareThirdDataBean);
        String data = gson.toJson(shareApp1Bean);
        shareText(data);
    } catch (Exception e) {
        e.printStackTrace();
        ToastUtils.showShort(getString(R.string.share_color_clock_error, e.getMessage()));
    }
}
```
&nbsp;
### 自定义Scheme方式的分享功能 - 示例:
在AndroidMainfest.xml中接收分享数据的页面添加以下\<intent-filter\>(建议单独配置)
```
<!-- scheme 形式分享 in -->
<intent-filter>

    <!-- 下面这几个必须要设置 intent-filter 配置 示例 -->
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <!--colorclock://publicapi/entry-->
    <data
        android:host="publicapi"
        android:pathPrefix="/entry"
        android:scheme="colorshareapp1" />
</intent-filter>
```
在页面中接收scheme的Uri并解析数据
```
 /**
 * 接收分享数据
 */
private void getShareInfo() {
    //接收 色采App 分享的数据
    Intent mIntent = getIntent();
    Uri mUri = mIntent.getData();
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
```
