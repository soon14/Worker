package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/17
 * author: SmallCake
 */
public class BannerBean implements Serializable {

    /**
     * contentPath	string 广告内容地址
     * id	        integer ID
     * linkUrl	    string 链接地址 外部链接以http开头；内部链接给ID号即可
     * title        string 标题
     */

    private String contentPath;
    private int id;
    private String linkUrl;
    private String title;

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
