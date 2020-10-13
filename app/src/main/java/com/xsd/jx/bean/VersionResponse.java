package com.xsd.jx.bean;

/**
 * Date: 2020/10/12
 * author: SmallCake
 */
public class VersionResponse {

    /**
     * desc : string
     * id : 0
     * is_must : 0
     * platform : 0
     * url : string
     * version : 0
     * version_name : string
     */

    private String desc;
    private int id;
    private int is_must;
    private int platform;
    private String url;
    private int version;
    private String version_name;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_must() {
        return is_must;
    }

    public void setIs_must(int is_must) {
        this.is_must = is_must;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
