package com.wizeyes.app2.bean;

import java.util.List;

/**
 * 分享色卡数据bean
 *
 * @auther lvzhao
 * Created on 2020/6/28.
 */
public class SharePaletteDataBean {
    /**
     * 色卡名称
     */
    public String name;
    /**
     * 色卡颜色
     */
    public List<String> colors;

    public SharePaletteDataBean(String name, List<String> colors) {
        this.name = name;
        this.colors = colors;
    }
}
