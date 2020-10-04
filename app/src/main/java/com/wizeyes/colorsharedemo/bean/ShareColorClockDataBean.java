package com.wizeyes.colorsharedemo.bean;

/**
 * 分享色采时钟Bean
 *
 * @auther lvzhao
 * Created on 2020/9/6
 */
public class ShareColorClockDataBean<T> {
    public int type;
    public T data;

    public ShareColorClockDataBean(int type, T t) {
        this.type = type;
        this.data = t;
    }
}
