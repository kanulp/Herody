package com.kanu_lp.support;

/**
 * Created by Kanu on 6/16/2017.
 */

public class Product_Data {

    private String product_id;
    private String product_title;
    private String product_price;
    private String product_url;
    private String product_delivery_time;



    public Product_Data() {
    }

    public Product_Data(String product_id, String product_title, String product_price, String product_url, String product_delivery_time) {
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_url = product_url;
        this.product_delivery_time = product_delivery_time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getProduct_delivery_time() {
        return product_delivery_time;
    }

    public void setProduct_delivery_time(String product_delivery_time) {
        this.product_delivery_time = product_delivery_time;
    }
}
