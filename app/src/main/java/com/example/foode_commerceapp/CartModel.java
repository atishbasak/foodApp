package com.example.foode_commerceapp;

public class CartModel {
    private String cartImg;
    private String cartResturantName;
    private String cartDISH;
    private String cartPrice;
    private String cartNoItem;


    public CartModel(String cartImg, String cartResturantName, String cartDISH, String cartPrice, String cartNoItem) {
        this.cartImg = cartImg;
        this.cartResturantName = cartResturantName;
        this.cartDISH = cartDISH;
        this.cartPrice = cartPrice;
        this.cartNoItem = cartNoItem;
    }

    public String getCartImg() {
        return cartImg;
    }

    public void setCartImg(String cartImg) {
        this.cartImg = cartImg;
    }

    public String getCartResturantName() {
        return cartResturantName;
    }

    public void setCartResturantName(String cartResturantName) {
        this.cartResturantName = cartResturantName;
    }

    public String getCartDish() {
        return cartDISH;
    }

    public void setCartDish(String cartDISH) {
        this.cartDISH = cartDISH;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartNoItem() {
        return cartNoItem;
    }

    public void setCartNoItem(String cartNoItem) {
        this.cartNoItem = cartNoItem;
    }
}
