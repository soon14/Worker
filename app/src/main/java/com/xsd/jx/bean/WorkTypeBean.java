package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/8
 * author: SmallCake
 */
public class WorkTypeBean implements Serializable {

    /**
     * id : 1
     * title : 工长
     */

    private int id;
    private String title;

    @Override
    public String toString() {
        return "WorkTypeBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
