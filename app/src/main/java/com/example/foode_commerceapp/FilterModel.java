package com.example.foode_commerceapp;

public class FilterModel {
    String cardIMG;
    String cardTITLE;
    String ratingOFFOOD;
    String dishNAME;
    String foodDESTINATION;
    String foodPRICE;
    String forNUMMBERpeople;
    String deliveryTIME;
    String distance;

    public FilterModel(String cardIMG,String cardTITLE,String ratingOFFOOD,String dishNAME,String foodDESTINATION,String foodPRICE,String forNUMMBERpeople,String deliveryTIME,String distance){
        this.cardIMG = cardIMG;
        this.cardTITLE = cardTITLE;
        this.ratingOFFOOD = ratingOFFOOD;
        this.dishNAME = dishNAME;
        this.foodDESTINATION = foodDESTINATION;
        this.foodPRICE = foodPRICE;
        this.forNUMMBERpeople = forNUMMBERpeople;
        this.deliveryTIME = deliveryTIME;
        this.distance = distance;
    }


    public String  getcardIMG(){
        return cardIMG;
    }
    public void setcardIMG(String img){
        this.cardIMG = cardIMG;
    }


    public String getcardTITLE(){
        return cardTITLE;
    }
    public void setcardTITLEl(){
        this.cardTITLE = cardTITLE;
    }

    public String getratingOFFOOD(){
        return ratingOFFOOD;
    }
    public void setratingOFFOOD(){
        this.ratingOFFOOD = ratingOFFOOD;
    }

    public String getdishNAME(){
        return dishNAME;
    }
    public void setdishNAME(){
        this.dishNAME = dishNAME;
    }

    public String getfoodDESTINATION(){
        return foodDESTINATION;
    }
    public void setfoodDESTINATION(){
        this.foodDESTINATION = foodDESTINATION;
    }

    public String getfoodPRICE(){
        return foodPRICE;
    }
    public void setfoodPRICE(){
        this.foodPRICE = foodPRICE;
    }

    public String getforNUMMBERpeople(){
        return forNUMMBERpeople;
    }
    public void setforNUMMBERpeople(){
        this.forNUMMBERpeople = forNUMMBERpeople;
    }

    public String getdeliveryTIME(){
        return deliveryTIME;
    }
    public void setdeliveryTIME(){
        this.deliveryTIME = deliveryTIME;
    }

    public String getdistance(){
        return distance;
    }
    public void setdistance(){
        this.distance = distance;
    }

}

