package com.example.foode_commerceapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

public class SpecificItemPage extends AppCompatActivity {

    private TextView itemIncrease;
    private ImageView itemDecrease;
    private TextView itemNumberBox;
    private Button itemPageOrderValue;
    private TextView dishNameSPE,spePriceSingle,imgStr;
    private  ImageView specificIMG;

    ConstraintLayout specificPage;
    int index=0;
    public static  final String EXTRA_ORDER_VALUE = "com.example.foode_commerceapp.extra.EXTRA_ORDER_VALUE";
    public static  final String ORDER_QUANTITY = "com.example.foode_commerceapp.extra.ORDER_QUANTITY";
    public static final String RESTURANT_NAME = "com.example.foode_commerceapp.extra.RESTURANT_NAME";
    public static final String DISH_NAME = "com.example.foode_commerceapp.extra.DISH_NAME";
//    public static final String IMG = "com.example.foode_commerceapp.extra.IMG";
    public static final String BILL_PRICE = "com.example.foode_commerceapp.extra.BILL_PRICE";
    public static final String SPE_BOOL = "com.example.foode_commerceapp.extra.SPE_BOOL";
    public static final String PARSE_IMG_TO_CART = "com.example.foode_commerceapp.extra.PARSE_IMG_TO_CART";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_item_page);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //  find-view into current activity
        itemIncrease = findViewById(R.id.itemIncrease);
        itemDecrease = findViewById(R.id.itemDecrease);
        itemNumberBox = findViewById(R.id.itemNumberBox);
//        itemNumberBox.setText(decreaseInd[index]);
        itemPageOrderValue = findViewById(R.id.itemPageOrderValue);
        itemPageOrderValue= findViewById(R.id.itemPageOrderValue);
        spePriceSingle = findViewById(R.id.spePriceSingle);
        dishNameSPE = findViewById(R.id.dishNameSPE);
        specificIMG = findViewById(R.id.specificIMG);
        imgStr = findViewById(R.id.imgStr);


        Intent intent = getIntent();
        String parse_nameSPECIFIC = intent.getStringExtra(FilterAdapter.PARSE_NAME);
        String parse_priceSPECIFIC = intent.getStringExtra(FilterAdapter.PARSE_PRICE);
        String parse_imgSPECIFIC = intent.getStringExtra(FilterAdapter.PARSE_IMG);
        spePriceSingle.setText(parse_priceSPECIFIC);
        dishNameSPE.setText(parse_nameSPECIFIC);
        imgStr.setText(parse_imgSPECIFIC);

        Picasso.get()
                .load(parse_imgSPECIFIC)
                .into(specificIMG);

        //  decrease calculation
        itemDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity_spe = itemNumberBox.getText().toString();
                int quantity_speINT = Integer.parseInt(quantity_spe);
                if(quantity_speINT <= 1 ){
                    int quantity_speUpdated = quantity_speINT;
                    String quantity_speUpdatedSTR = Integer.toString(quantity_speUpdated);
                    itemNumberBox.setText(quantity_speUpdatedSTR);
                }
                else{
                    int quantity_speUpdated = quantity_speINT -1;
                    String quantity_speUpdatedSTR = Integer.toString(quantity_speUpdated);
                    itemNumberBox.setText(quantity_speUpdatedSTR);
                }
            }
        });


        //  increase calculation
        itemIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity_spe = itemNumberBox.getText().toString();
                int quantity_speINT = Integer.parseInt(quantity_spe);
                int quantity_speUpdated = quantity_speINT + 1;
                String quantity_speUpdatedSTR = Integer.toString(quantity_speUpdated);
                itemNumberBox.setText(quantity_speUpdatedSTR);
            }
        });
    }


    // intent to pass values to next activity
    public void openTopingsActivitySPE(View view){

        Intent intentBool = getIntent();
        boolean ID = intentBool.getBooleanExtra(FilterAdapter.PARSE_BOOL,false);



        if(ID){
            Intent intent = new Intent(this,ToppingsPage.class);
            spePriceSingle = findViewById(R.id.spePriceSingle);
            itemNumberBox = findViewById(R.id.itemNumberBox);
            String quantity = itemNumberBox.getText().toString();
            String orderPrice = spePriceSingle.getText().toString();
            intent.putExtra(EXTRA_ORDER_VALUE,orderPrice);
            intent.putExtra(ORDER_QUANTITY,quantity);
//            intent.putExtra(PARSE_IMG_TO_CART,parse_img);
//            intent.putExtra(PARSE_IMG_TO_CART,parse_img);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, BillingPage.class);
            spePriceSingle = findViewById(R.id.spePriceSingle);
            itemNumberBox = findViewById(R.id.itemNumberBox);
            String quantity = itemNumberBox.getText().toString();
            String orderPrice = spePriceSingle.getText().toString();
            double orderPriceDBL = Double.parseDouble(orderPrice);
            int quantityINT = Integer.parseInt(quantity);
            double final_price = (orderPriceDBL * quantityINT);
            String final_priceSTR = Double.toString(final_price);
            boolean SPE_bool = true;
            intent.putExtra(BILL_PRICE,final_priceSTR);
            intent.putExtra(SPE_BOOL,SPE_bool);
            startActivity(intent);
        }
    }


    //  intent to going to next activity
    public void backToFilterPage(View view){
        Intent intent = new Intent(this, FilterItemsPage.class);
        startActivity(intent);
    }

    //  intent to going to next activity
    public void toCartActivity(View view){
        imgStr = findViewById(R.id.imgStr);
        String parse_img = imgStr.getText().toString();
        Intent intent = new Intent(this,CartActivity.class);
        String order_price = spePriceSingle.getText().toString();
        String dish_name = dishNameSPE.getText().toString();
        String quantity = itemNumberBox.getText().toString();
        intent.putExtra(EXTRA_ORDER_VALUE,order_price);
        intent.putExtra(ORDER_QUANTITY,quantity);
        intent.putExtra(DISH_NAME,dish_name);
        intent.putExtra(PARSE_IMG_TO_CART,parse_img);
        startActivity(intent);
    }
}