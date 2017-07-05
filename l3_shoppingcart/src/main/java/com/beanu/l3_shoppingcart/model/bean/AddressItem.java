package com.beanu.l3_shoppingcart.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 地址 信息
 * Created by Beanu on 2017/5/11.
 */

public class AddressItem implements Parcelable {

    private String id;//用户ID
    private String link_name;//联系人姓名
    private String link_phone;//联系人电话
    private String link_address;//详细地址
    private String province_id;//省份ID
    private String province_cn;//省份名称
    private String city_id;//城市ID
    private String createtime;//创建时间
    private String city_cn;//城市名称
    private String county_id;//区县ID
    private String county_cn;//区县名称
    private int is_default;//是否是默认选项  0  不是   1 是

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getLink_address() {
        return link_address;
    }

    public void setLink_address(String link_address) {
        this.link_address = link_address;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_cn() {
        return province_cn;
    }

    public void setProvince_cn(String province_cn) {
        this.province_cn = province_cn;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCity_cn() {
        return city_cn;
    }

    public void setCity_cn(String city_cn) {
        this.city_cn = city_cn;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getCounty_cn() {
        return county_cn;
    }

    public void setCounty_cn(String county_cn) {
        this.county_cn = county_cn;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.link_name);
        dest.writeString(this.link_phone);
        dest.writeString(this.link_address);
        dest.writeString(this.province_id);
        dest.writeString(this.province_cn);
        dest.writeString(this.city_id);
        dest.writeString(this.createtime);
        dest.writeString(this.city_cn);
        dest.writeString(this.county_id);
        dest.writeString(this.county_cn);
        dest.writeInt(this.is_default);
    }

    public AddressItem() {
    }

    protected AddressItem(Parcel in) {
        this.id = in.readString();
        this.link_name = in.readString();
        this.link_phone = in.readString();
        this.link_address = in.readString();
        this.province_id = in.readString();
        this.province_cn = in.readString();
        this.city_id = in.readString();
        this.createtime = in.readString();
        this.city_cn = in.readString();
        this.county_id = in.readString();
        this.county_cn = in.readString();
        this.is_default = in.readInt();
    }

    public static final Parcelable.Creator<AddressItem> CREATOR = new Parcelable.Creator<AddressItem>() {
        @Override
        public AddressItem createFromParcel(Parcel source) {
            return new AddressItem(source);
        }

        @Override
        public AddressItem[] newArray(int size) {
            return new AddressItem[size];
        }
    };
}
