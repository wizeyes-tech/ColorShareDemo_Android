package com.wizeyes.colorsharedemo.utils;

/**
 * 通用工具类
 *
 * @auther lvzhao
 * Created on 2020/10/7.
 */
public class CommonUtils {
    /**
     * 是否亮色
     *
     * @param color
     * @return
     */
    public static boolean isLightColor(int color) {
        return androidx.core.graphics.ColorUtils.calculateLuminance(color) >= 0.5;
    }
}
