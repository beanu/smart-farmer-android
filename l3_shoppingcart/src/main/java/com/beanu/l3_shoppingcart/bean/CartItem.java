package com.beanu.l3_shoppingcart.bean;

/**
 * 购物车 实体类
 * Created by Beanu on 2017/3/10.
 */

public class CartItem {

    //产品属性
    private String id;
    private String name;
    private String sku;
    private double price;
    private String faceImgPath;

    //购物车专用
    private int cart_amount;//购车中存放的商品数量
    private boolean cart_checked;//是否被选中
    private boolean delete_checked;//是否被选中删除


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCart_amount() {
        return cart_amount;
    }

    public void setCart_amount(int cart_amount) {
        this.cart_amount = cart_amount;
    }

    public boolean isCart_checked() {
        return cart_checked;
    }

    public void setCart_checked(boolean cart_checked) {
        this.cart_checked = cart_checked;
    }

    public String getFaceImgPath() {
        return faceImgPath;
    }

    public void setFaceImgPath(String faceImgPath) {
        this.faceImgPath = faceImgPath;
    }

    public boolean isDelete_checked() {
        return delete_checked;
    }

    public void setDelete_checked(boolean delete_checked) {
        this.delete_checked = delete_checked;
    }
}
