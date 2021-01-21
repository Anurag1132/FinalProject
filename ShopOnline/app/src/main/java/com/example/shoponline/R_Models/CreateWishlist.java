package com.example.shoponline.R_Models;

public class CreateWishlist {
    private int id;
    private String product_id;
    private String user_name;

    public CreateWishlist(String product_id, String user_name) {
        this.product_id = product_id;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

