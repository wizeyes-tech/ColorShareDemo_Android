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
```
