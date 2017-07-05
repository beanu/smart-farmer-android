package com.beanu.l3_shoppingcart.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 购物车 实体类
 * Created by Beanu on 2017/3/10.
 */

public class CartItem implements Parcelable {

    //产品属性
    private String id;
    private String productId;
    private String name;
    private String press;
    private double price;
    private String productImg;

    //购物车专用
    private int num;//购车中存放的商品数量
    private int isSelect;//是否被选中 0否 1是
    private boolean delete_checked;//是否被选中删除


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int isSelect() {
        return isSelect;
    }

    public void setSelect(int select) {
        this.isSelect = select;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public boolean isDelete_checked() {
        return delete_checked;
    }

    public void setDelete_checked(boolean delete_checked) {
        this.delete_checked = delete_checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.press);
        dest.writeDouble(this.price);
        dest.writeString(this.productImg);
        dest.writeInt(this.num);
        dest.writeInt(this.isSelect);
        dest.writeByte(this.delete_checked ? (byte) 1 : (byte) 0);
    }

    public CartItem() {
    }

    protected CartItem(Parcel in) {
        this.id = in.readString();
        this.productId = in.readString();
        this.name = in.readString();
        this.press = in.readString();
        this.price = in.readDouble();
        this.productImg = in.readString();
        this.num = in.readInt();
        this.isSelect = in.readInt();
        this.delete_checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel source) {
            return new CartItem(source);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
