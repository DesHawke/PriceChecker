package ru.altstu.pricechecker;

import android.graphics.Bitmap;

public class Product {
    String name;
    String imgUrl;
    String shop;
    String price;

    public Product(String name, String imgUrl, String shop, String price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.shop = shop;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
