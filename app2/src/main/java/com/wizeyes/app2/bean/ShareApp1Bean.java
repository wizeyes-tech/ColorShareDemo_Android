package com.wizeyes.app2.bean;

/**
 * 分享 App1 Bean
 *
 * @auther lvzhao
 * Created on 2020/9/6
 */
public class ShareApp1Bean<T> {
    /**
     * 数据类型
     */
    public int type;
    /**
     * 数据
     */
    public T data;

    public ShareApp1Bean(int type, T t) {
        this.type = type;
        this.data = t;
    }
}
