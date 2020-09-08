package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2019/12/24
 * author: SmallCake
 * 轮播图高宽 1：2.5
 * 福利推送广告图高宽 1：5
 */
public class AdBean implements Serializable {
    /**
     * id : 5
     * content_path : http://e.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cef70d70409845d688d53f20f7.jpg
     * link_url : http://www.baidu.com
     * title : eee
     * type : 2
     */

    private String id;
    private String image_url;
    private String path;
    private String title;
    private int link_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLink_type() {
        return link_type;
    }

    public void setLink_type(int link_type) {
        this.link_type = link_type;
    }
}
