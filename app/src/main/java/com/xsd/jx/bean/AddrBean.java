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

    private AddrBean(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setLevel(builder.level);
        setCenter(builder.center);
        setParent_id(builder.parent_id);
    }


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

    public static final class Builder {
        private int id;
        private String name;
        private int level;
        private String center;
        private int parent_id;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder level(int val) {
            level = val;
            return this;
        }

        public Builder center(String val) {
            center = val;
            return this;
        }

        public Builder parent_id(int val) {
            parent_id = val;
            return this;
        }

        public AddrBean build() {
            return new AddrBean(this);
        }
    }
}
