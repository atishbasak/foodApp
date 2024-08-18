package com.example.foode_commerceapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class BillingPage extends AppCompatActivity {
    private TextView billingPagePrice;
    private TextView deliveryCharge;
    private TextView billingPageTax;
    private TextView billingPriceTotal;
    private TextView finalPriceEnd;

    RadioButton radioCredit;
    RadioButton radioDebit,radioCOD;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static final String CREDIT_PAYMENT = "com.example.foode_commerceapp.extra.CREDIT_PAYMENT";
    public static final String CREDIT_PRICE = "com.example.foode_commerceapp.extra.CREDIT_PRICE";
    public static final String COD_TRUE = "com.example.foode_commerceapp.extra.COD_TRUE";
    public static final String PRICE_TO_COD = "com.example.foode_commerceapp.extra.PRICE_TO_COD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        billingPagePrice = findViewById(R.id.billingPagePrice);
        billingPageTax = findViewById(R.id.billingPageTax);
        deliveryCharge = findViewById(R.id.deliveryCharge);
        billingPriceTotal = findViewById(R.id.billingPriceTotal);
        finalPriceEnd = findViewById(R.id.finalPriceEnd);
        radioCredit = findViewById(R.id.radioCredit);
        radioDebit = findViewById(R.id.radioDebit);

        radioDebit.bringToFront();

        Intent intent = getIntent();
        String billing_final_price = intent.getStringExtra(ToppingsPage.FINAL_PRICE);
        boolean top_bool = intent.getBooleanExtra(ToppingsPage.TOP_BOOL,false);
        String spe_billing_final_price = intent.getStringExtra(SpecificItemPage.BILL_PRICE);
        boolean spe_bool = intent.getBooleanExtra(SpecificItemPage.SPE_BOOL,false);
        boolean cart_bool = intent.getBooleanExtra(CartActivity.CART_BOOL,false);
        String cart_billing = intent.getStringExtra(CartActivity.CART_BILLING);

        if(top_bool){
            billingPagePrice.setText(billing_final_price);

            //   tax calculation
            double const_tax = 100.00;
            double billing_final_priceINT = Double.parseDouble( billing_final_price);
            double final_tax = billing_final_priceINT - ((const_tax * billing_final_priceINT)/118);
            double final_tax_decimal = Double.parseDouble(df.format(final_tax));
            String final_taxDBL = Double.toString( final_tax_decimal);
            billingPageTax.setText(final_taxDBL);

            //   final price calculation
            String order_pre_price = billingPagePrice.getText().toString();
            String delivery_charge = deliveryCharge.getText().toString();
            double order_pre_priceDLB = Double.parseDouble(order_pre_price);
            double delivery_chargeDLB = Double.parseDouble(delivery_charge);
            double final_price = (order_pre_priceDLB + delivery_chargeDLB);
            double final_price_decimal = Double.parseDouble(df.format(final_price));
            String final_priceSTR = Double.toString(final_price_decimal);
            billingPriceTotal.setText(final_priceSTR);
            finalPriceEnd.setText(final_priceSTR);
        } else if (spe_bool) {
            billingPagePrice.setText(spe_billing_final_price);

            //tax calculation
            double const_tax = 100.00;
            double spe_billing_final_priceINT = Double.parseDouble( spe_billing_final_price);
            double final_tax = spe_billing_final_priceINT - ((const_tax * spe_billing_final_priceINT)/118);
            double final_tax_decimal = Double.parseDouble(df.format(final_tax));
            String final_taxDBL = Double.toString( final_tax_decimal);
            billingPageTax.setText(final_taxDBL);

            //   final price calculation
            String order_pre_price = billingPagePrice.getText().toString();
            String delivery_charge = deliveryCharge.getText().toString();
            double order_pre_priceDLB = Double.parseDouble(order_pre_price);
            double delivery_chargeDLB = Double.parseDouble(delivery_charge);
            double final_price = (order_pre_priceDLB + delivery_chargeDLB);
            double final_price_decimal = Double.parseDouble(df.format(final_price));
            String final_priceSTR = Double.toString(final_price_decimal);
            billingPriceTotal.setText(final_priceSTR);
            finalPriceEnd.setText(final_priceSTR);
        } else if (cart_bool) {
            billingPagePrice.setText(cart_billing);

            //tax calculation
            double const_tax = 100.00;
            double cart_billingINT = Double.parseDouble( cart_billing);
            double final_tax = cart_billingINT - ((const_tax * cart_billingINT)/118);
            double final_tax_decimal = Double.parseDouble(df.format(final_tax));
            String final_taxDBL = Double.toString( final_tax_decimal);
            billingPageTax.setText(final_taxDBL);

            //   final price calculation
            String order_pre_price = billingPagePrice.getText().toString();
            String delivery_charge = deliveryCharge.getText().toString();
            double order_pre_priceDLB = Double.parseDouble(order_pre_price);
            double delivery_chargeDLB = Double.parseDouble(delivery_charge);
            double final_price = (order_pre_priceDLB + delivery_chargeDLB);
            double final_price_decimal = Double.parseDouble(df.format(final_price));
            String final_priceSTR = Double.toString(final_price_decimal);
            billingPriceTotal.setText(final_priceSTR);
            finalPriceEnd.setText(final_priceSTR);
        }


        // disable other payment method
        if(radioCredit.isChecked()){
            radioDebit.setEnabled(false);
        }
        else if (radioDebit.isChecked()) {
            radioCredit.setEnabled(false);
        }
    }

    // intent for going to next activity
    public void backToToppingsPage(View view){
        Intent intent = new Intent(this, ToppingsPage.class);
        startActivity(intent);
    }

    // for going to credit activity
    public void creditActivity(View view){
        radioCredit = findViewById(R.id.radioCredit);
        radioDebit = findViewById(R.id.radioDebit);
        radioCOD = findViewById(R.id.radioCOD);
        String price_to_payment = finalPriceEnd.getText().toString();

        String final_price_end = finalPriceEnd.getText().toString();

        boolean cod_true = true;
//        boolean credit_true = true;
//        boolean debit_true = true;

        if(radioCredit.isChecked()){
            Intent intent = new Intent(this,CodActivity.class);
            String credit_name = "CREDIT CARD NUMBER";
            intent.putExtra(CREDIT_PAYMENT,credit_name);
            intent.putExtra(CREDIT_PRICE,price_to_payment);
            intent.putExtra(PRICE_TO_COD,final_price_end);
//            intent.putExtra(CREDIT_TRUE,credit_true);
            startActivity(intent);
        } else if (radioDebit.isChecked()) {
            Intent intent = new Intent(this,CodActivity.class);
            intent.putExtra(COD_TRUE,cod_true);
            intent.putExtra(PRICE_TO_COD,final_price_end);
            startActivity(intent);
        } else if (radioCOD.isChecked()) {
            Intent intent= new Intent(this,CodActivity.class);
            String credit_name = "DEBIT CARD NUMBER";
//            intent.putExtra(DEBIT_TRUE,debit_true);
            intent.putExtra(PRICE_TO_COD,final_price_end);
            intent.putExtra(CREDIT_PAYMENT,credit_name);
            intent.putExtra(CREDIT_PRICE,price_to_payment);
            startActivity(intent);
        }
    }
}