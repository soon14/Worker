package com.xsd.jx.bean;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public class AddrBean {


    /**
     * id : 1
     * name : 北京市
     * level : 1
     * center : 116.407394,39.904211
     * parent_id : -1
     */

    private int id;
    private String name;
    private int level;
    private String center;
    private int parent_id;

    @Override
    public String toString() {
        return "AddrBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", center='" + center + '\'' +
                ", parent_id=" + parent_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
