package com.wizeyes.colorsharedemo.bean;

/**
 * 分享色采时钟Bean
 *
 * @auther lvzhao
 * Created on 2020/9/6
 */
public class ShareColorClockDataBean<T> {
    /**
     * 数据类型
     */
    public int type;
    /**
     * 数据
     */
    public T data;

    public ShareColorClockDataBean(int type, T t) {
        this.type = type;
        this.data = t;
    }
}
