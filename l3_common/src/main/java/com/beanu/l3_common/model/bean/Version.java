package com.beanu.l3_common.model.bean;

import java.io.Serializable;

/**
 * 版本信息
 * Created by Beanu on 16/6/7.
 */
public class Version implements Serializable {

    private String varsion_name;
    private String datetime;
    private int id;
    private int version;
    private String version_str;
    private String url;
    private String desc;

    public String getVarsion_name() {
        return varsion_name;
    }

    public void setVarsion_name(String varsion_name) {
        this.varsion_name = varsion_name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersion_str() {
        return version_str;
    }

    public void setVersion_str(String version_str) {
        this.version_str = version_str;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
