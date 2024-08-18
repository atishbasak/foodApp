package com.example.foode_commerceapp;

public class ToppingsMainModel {
    int toppingsCardImg;
    String toppingsCardName;
    String toppingsCardPrice;
    public ToppingsMainModel(int toppingsCardImg,String toppingsCardName,String toppingsCardPrice){
        this.toppingsCardImg = toppingsCardImg;
        this.toppingsCardName = toppingsCardName;
        this.toppingsCardPrice = toppingsCardPrice;
    }

    public int gettoppingsCardImg(){
        return toppingsCardImg;
    }
    public void settoppingsCardImg(int toppingsCardImg){
        this.toppingsCardImg = toppingsCardImg;
    }

    public String  gettoppingsCardName(){
        return toppingsCardName;
    }
    public void settoppingsCardName(){
        this.toppingsCardName = toppingsCardName;
    }

    public String  gettoppingsCardPrice(){
        return toppingsCardPrice;
    }
    public void settoppingsCardPrice(){
        this.toppingsCardPrice = toppingsCardPrice;
    }
}
