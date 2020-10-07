package com.wizeyes.colorsharedemo.bean;

/**
 * 分享色卡数据bean
 *
 * @auther lvzhao
 * Created on 2020/6/28.
 */
public class ShareThirdDataBean {
    /**
     * 时钟背景颜色
     */
    private String clockBackgroundColor;
    /**
     * 时钟数字背景颜色
     */
    private String clockDigitalBackgroundColor;
    /**
     * 时钟页面背景颜色
     */
    private String clockPageBackgroundColor;
    /**
     * 时钟冒号分隔符颜色
     */
    private String clockColonColor;
    /**
     * 时钟文本颜色
     */
    private String clockTextColor;

    public ShareThirdDataBean(String clockBackgroundColor, String clockDigitalBackgroundColor,
                              String clockPageBackgroundColor, String clockColonColor, String clockTextColor) {
        this.clockBackgroundColor = clockBackgroundColor;
        this.clockDigitalBackgroundColor = clockDigitalBackgroundColor;
        this.clockPageBackgroundColor = clockPageBackgroundColor;
        this.clockColonColor = clockColonColor;
        this.clockTextColor = clockTextColor;
    }
}
