package com.example.foode_commerceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecycler;
    private CartAdapter adapter;
    private ArrayList<CartModel> cartModalArrayList;
    private Context context;
    TextView cartPriceTotal;

    public static final String CART_BILLING = "com.example.foode_commerceapp.extra.CART_BILLING";
    public static final String CART_BOOL = "com.example.foode_commerceapp.extra.CART_BOOL";
    public static final String SHARED_PREFERENCES = "shared preferences";
    public static final String CARTS = "com.example.foode_commerceapp.extra.carts";
    public static final String TOTAL_PRICE = "com.example.foode_commerceapp.extra.total_price";

    boolean cart_bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cartPriceTotal = findViewById(R.id.cartPriceTotal);
        cartRecycler = findViewById(R.id.cartRecycler);
        context = CartActivity.this;

        loadData();
        buildRecyclerView();

        Intent intent = getIntent();
        if (intent != null) {
            String price_Spe = intent.getStringExtra(SpecificItemPage.EXTRA_ORDER_VALUE);
            String quantity_Spe = intent.getStringExtra(SpecificItemPage.ORDER_QUANTITY);
            String dish_name_Spe = intent.getStringExtra(SpecificItemPage.DISH_NAME);
            String img_from_spe = intent.getStringExtra(SpecificItemPage.PARSE_IMG_TO_CART);

            if (price_Spe != null && quantity_Spe != null && dish_name_Spe != null) {
                int quantity = Integer.parseInt(quantity_Spe);
                if (quantity > 0) {
                    cartModalArrayList.add(new CartModel(
                            img_from_spe,
                            "Red Chili Restaurantoo", dish_name_Spe, price_Spe, quantity_Spe
                    ));
                    adapter.notifyItemInserted(cartModalArrayList.size() - 1);
                    saveData();
                }
                double item_price = Double.parseDouble(price_Spe);
                int quantity_SpeINT = Integer.parseInt(quantity_Spe);
                double item_final_priceDBL = item_price * quantity_SpeINT;

                String price_end = cartPriceTotal.getText().toString();
                double price_endDBL = Double.parseDouble(price_end);
                double price_end_Concat = price_endDBL + item_final_priceDBL;
                String price_end_ConcatSTR = Double.toString(price_end_Concat);
                cartPriceTotal.setText(price_end_ConcatSTR);
                saveTotalPrice(price_end_ConcatSTR);
            }
        }
    }

    private void buildRecyclerView() {
        adapter = new CartAdapter(cartModalArrayList, CartActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        cartRecycler.setHasFixedSize(true);
        cartRecycler.setLayoutManager(manager);
        cartRecycler.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CARTS, null);
        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
        cartModalArrayList = gson.fromJson(json, type);
        if (cartModalArrayList == null) {
            cartModalArrayList = new ArrayList<>();
        }
        removeItemWithZeroQuantity();

        String totalPrice = sharedPreferences.getString(TOTAL_PRICE, "0.0");
        cartPriceTotal.setText(totalPrice);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartModalArrayList);
        editor.putString(CARTS, json);
        editor.apply();
    }

    private void saveTotalPrice(String totalPrice) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOTAL_PRICE, totalPrice);
        editor.apply();
    }

    private void removeItemWithZeroQuantity() {
        for (int i = cartModalArrayList.size() - 1; i >= 0; i--) {
            if (Integer.parseInt(cartModalArrayList.get(i).getCartNoItem()) == 0) {
                cartModalArrayList.remove(i);
            }
        }
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        adapter.notifyDataSetChanged();
    }

    public void toHomePageCart(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void cartToBilling(View view) {
        String card_final_price = cartPriceTotal.getText().toString();
        double card_final_priceDBL = Double.parseDouble(card_final_price);
        if(card_final_priceDBL == 0){
            Toast.makeText(context, "Please add items to the cart", Toast.LENGTH_SHORT).show();
        }else{
            String cart_billing = cartPriceTotal.getText().toString();
            cart_bool = true;
            Intent intent = new Intent(this, BillingPage.class);
            intent.putExtra(CART_BILLING, cart_billing);
            intent.putExtra(CART_BOOL, cart_bool);
            startActivity(intent);
        }
    }
}

















// 1st

//package com.example.foode_commerceapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//public class CartActivity extends AppCompatActivity {
//
//    private RecyclerView cartRecycler;
//    private CartAdapter adapter;
//    private ArrayList<CartModel> cartModalArrayList;
//    private Context context;
//    TextView cartPriceTotal;
////    TextView itemTotalPrice;
//
//    public static final String CART_BILLING = "com.example.foode_commerceapp.extra.CART_BILLING";
//    public static final String CART_BOOL = "com.example.foode_commerceapp.extra.CART_BOOL";
//
//    boolean cart_bool = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        cartPriceTotal = findViewById(R.id.cartPriceTotal);
//        cartRecycler = findViewById(R.id.cartRecycler);
////        itemTotalPrice = findViewById(R.id.itemTotalPrice);
//        context = CartActivity.this;
//
//        loadData();
//        buildRecyclerView();
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String price_Spe = intent.getStringExtra(SpecificItemPage.EXTRA_ORDER_VALUE);
//            String quantity_Spe = intent.getStringExtra(SpecificItemPage.ORDER_QUANTITY);
//            String dish_name_Spe = intent.getStringExtra(SpecificItemPage.DISH_NAME);
//            String img_from_spe = intent.getStringExtra(SpecificItemPage.PARSE_IMG_TO_CART);
//
//            if (price_Spe != null && quantity_Spe != null && dish_name_Spe != null) {
//                int quantity = Integer.parseInt(quantity_Spe);
//                if (quantity > 0) {
//                    cartModalArrayList.add(new CartModel(
//                            img_from_spe,
//                            "Red Chili Restaurantoo", dish_name_Spe, price_Spe, quantity_Spe
//                    ));
//                    adapter.notifyItemInserted(cartModalArrayList.size() - 1);
//                    saveData();
//                }
//                double item_price = Double.parseDouble(price_Spe);
//                int quantity_SpeINT = Integer.parseInt(quantity_Spe);
//                double item_final_priceDBL = (item_price * quantity_SpeINT) ;
//
//                String price_end = cartPriceTotal.getText().toString();
//                double price_endDBL = Double.parseDouble(price_end);
//                double price_end_Concat = (price_endDBL + item_final_priceDBL);
//                String price_end_ConcatSTR = Double.toString(price_end_Concat);
//                cartPriceTotal.setText(price_end_ConcatSTR);
//            }
//        }
//    }
//
//    private void buildRecyclerView() {
//        adapter = new CartAdapter(cartModalArrayList, CartActivity.this);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        cartRecycler.setHasFixedSize(true);
//        cartRecycler.setLayoutManager(manager);
//        cartRecycler.setAdapter(adapter);
//    }
//
//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("carts", null);
//        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
//        cartModalArrayList = gson.fromJson(json, type);
//        if (cartModalArrayList == null) {
//            cartModalArrayList = new ArrayList<>();
//        }
//        removeItemWithZeroQuantity();
//    }
//
//    private void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(cartModalArrayList);
//        editor.putString("carts", json);
//        editor.apply();
//    }
//
//    private void removeItemWithZeroQuantity() {
//        for (int i = cartModalArrayList.size() - 1; i >= 0; i--) {
//            if (Integer.parseInt(cartModalArrayList.get(i).getCartNoItem()) == 0) {
//                cartModalArrayList.remove(i);
//            }
//        }
//        saveData();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadData();
//        adapter.notifyDataSetChanged();
//    }
//
//    public void toHomePageCart(View view) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//    }
//
//    public void cartToBilling(View view) {
//        String card_final_price = cartPriceTotal.getText().toString();
//        double card_final_priceDBL = Double.parseDouble(card_final_price);
//        if(card_final_priceDBL == 0){
//            Toast.makeText(context, "Please add items to the cart", Toast.LENGTH_SHORT).show();
//        }else{
//            String cart_billing = cartPriceTotal.getText().toString();
//            cart_bool = true;
//            Intent intent = new Intent(this, BillingPage.class);
//            intent.putExtra(CART_BILLING, cart_billing);
//            intent.putExtra(CART_BOOL, cart_bool);
//            startActivity(intent);
//        }
//    }
//}























//  2nd


//package com.example.foode_commerceapp;
//
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.content.pm.ActivityInfo;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.TextView;
//
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.google.gson.Gson;
//        import com.google.gson.reflect.TypeToken;
//
//        import java.lang.reflect.Type;
//        import java.util.ArrayList;
//        import java.util.Random;
//
//public class CartActivity extends AppCompatActivity {
//
//    private RecyclerView cartRecycler;
//    private CartAdapter adapter;
//    private ArrayList<CartModel> cartModalArrayList;
//    private Context context;
//    TextView cartPriceTotal;
////    TextView itemTotalPrice;
//
//    public static final String CART_BILLING = "com.example.foode_commerceapp.extra.CART_BILLING";
//    public static final String CART_BOOL = "com.example.foode_commerceapp.extra.CART_BOOL";
//
//    boolean cart_bool = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        cartPriceTotal = findViewById(R.id.cartPriceTotal);
//        cartRecycler = findViewById(R.id.cartRecycler);
////        itemTotalPrice = findViewById(R.id.itemTotalPrice);
//        context = CartActivity.this;
//
//        loadData();
//        buildRecyclerView();
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String price_Spe = intent.getStringExtra(SpecificItemPage.EXTRA_ORDER_VALUE);
//            String quantity_Spe = intent.getStringExtra(SpecificItemPage.ORDER_QUANTITY);
//            String dish_name_Spe = intent.getStringExtra(SpecificItemPage.DISH_NAME);
////            String img_from_spe = intent.getStringExtra(SpecificItemPage.PARSE_IMG_TO_CART);
//
//            if (price_Spe != null && quantity_Spe != null && dish_name_Spe != null) {
//                int quantity = Integer.parseInt(quantity_Spe);
//                if (quantity > 0) {
////                    cartModalArrayList.add(new CartModel(
////                            img_from_spe,
////                            "Red Chili Restaurantoo", dish_name_Spe, price_Spe, quantity_Spe
////                    ));
//
//                    Random random = new Random();
//                    int cart_id = random.nextInt(500000-400000) + 452364;
//                    String cart_idSTR = Integer.toString(cart_id);
//                    cartModalArrayList.add(new CartModel(price_Spe,
//                            "https://media.istockphoto.com/id/1150589013/photo/fried-shrimps-in-sweet-spicy-sauce.jpg?s=612x612&w=0&k=20&c=lGxES5oHE22Uq9wwpAcWh31TM9i9w7utISVeOcuYR2o=",
//                            "Red Chili Restaurantoo", dish_name_Spe, price_Spe, quantity_Spe
//                    ));
//                    adapter.notifyItemInserted(cartModalArrayList.size() - 1);
//                    saveData();
////                    Toast.makeText(context, "img link is "+img_from_spe, Toast.LENGTH_SHORT).show();
//                }
////                String item_final_price = itemTotalPrice.getText().toString();
//                double item_price = Double.parseDouble(price_Spe);
//                int quantity_SpeINT = Integer.parseInt(quantity_Spe);
//                double item_final_priceDBL = (item_price * quantity_SpeINT) ;
//
//                String price_end = cartPriceTotal.getText().toString();
//                double price_endDBL = Double.parseDouble(price_end);
//                double price_end_Concat = (price_endDBL + item_final_priceDBL);
//                String price_end_ConcatSTR = Double.toString(price_end_Concat);
//                cartPriceTotal.setText(price_end_ConcatSTR);
//            }
//        }
//    }
//
//    private void buildRecyclerView() {
//        adapter = new CartAdapter(cartModalArrayList, CartActivity.this);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        cartRecycler.setHasFixedSize(true);
//        cartRecycler.setLayoutManager(manager);
//        cartRecycler.setAdapter(adapter);
//    }
//
//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("carts", null);
//        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
//        cartModalArrayList = gson.fromJson(json, type);
//        if (cartModalArrayList == null) {
//            cartModalArrayList = new ArrayList<>();
//        }
//        removeItemWithZeroQuantity();
//    }
//
//    private void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(cartModalArrayList);
//        editor.putString("carts", json);
//        editor.apply();
//    }
//
//    private void removeItemWithZeroQuantity() {
//        for (int i = cartModalArrayList.size() - 1; i >= 0; i--) {
//            if (Integer.parseInt(cartModalArrayList.get(i).getCartNoItem()) == 0) {
//                cartModalArrayList.remove(i);
//            }
//        }
//        saveData();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadData();
//        adapter.notifyDataSetChanged();
//    }
//
//    public void toHomePageCart(View view) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//    }
//
//    public void cartToBilling(View view) {
//        String cart_billing = cartPriceTotal.getText().toString();
//        cart_bool = true;
//        Intent intent = new Intent(this, BillingPage.class);
//        intent.putExtra(CART_BILLING, cart_billing);
//        intent.putExtra(CART_BOOL, cart_bool);
//        startActivity(intent);
//    }
//}

























//  3rd

//package com.example.foode_commerceapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//public class CartActivity extends AppCompatActivity {
//
//    private RecyclerView cartRecycler;
//    private CartAdapter adapter;
//    private ArrayList<CartModel> cartModalArrayList;
//    private Context context;
//    TextView cartPriceTotal;
////    TextView cartNoItem,cartPrice,ItemTotalPrice;
//
//    public static final String CART_BILLING = "com.example.foode_commerceapp.extra.CART_BILLING";
//    public static final String CART_BOOL = "com.example.foode_commerceapp.extra.CART_BOOL";
//    public static final String EMAIL_FOR_ORDER_CART = "com.example.foode_commerceapp.extra.EMAIL_FOR_ORDER_CART";
//
//    boolean cart_bool = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        cartPriceTotal = findViewById(R.id.cartPriceTotal);
//        cartRecycler = findViewById(R.id.cartRecycler);
//        context = CartActivity.this;
//
//        loadData();
//        buildRecyclerView();
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String price_Spe = intent.getStringExtra(SpecificItemPage.EXTRA_ORDER_VALUE);
//            String quantity_Spe = intent.getStringExtra(SpecificItemPage.ORDER_QUANTITY);
//            String dish_name_Spe = intent.getStringExtra(SpecificItemPage.DISH_NAME);
//
//            if (price_Spe != null && quantity_Spe != null && dish_name_Spe != null) {
//                int quantity = Integer.parseInt(quantity_Spe);
//                if (quantity > 0) {
//                    cartModalArrayList.add(new CartModel(
//                            "https://media.istockphoto.com/id/1150589013/photo/fried-shrimps-in-sweet-spicy-sauce.jpg?s=612x612&w=0&k=20&c=lGxES5oHE22Uq9wwpAcWh31TM9i9w7utISVeOcuYR2o=",
//                            "Red Chili Restaurantoo", dish_name_Spe, price_Spe, quantity_Spe
//                    ));
//                    adapter.notifyItemInserted(cartModalArrayList.size() - 1);
//                    saveData();
//                }
//            }
//        }
//    }
//
//    private void buildRecyclerView() {
//        adapter = new CartAdapter(cartModalArrayList, CartActivity.this);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        cartRecycler.setHasFixedSize(true);
//        cartRecycler.setLayoutManager(manager);
//        cartRecycler.setAdapter(adapter);
//    }
//
//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("carts", null);
//        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
//        cartModalArrayList = gson.fromJson(json, type);
//        if (cartModalArrayList == null) {
//            cartModalArrayList = new ArrayList<>();
//        }
//    }
//
//    private void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(cartModalArrayList);
//        editor.putString("carts", json);
//        editor.apply();
//    }
//
//    public void toHomePageCart(View view) {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//    }
//
//    public void cartToBilling(View view) {
////        Intent intentEmail = getIntent();
////        String email_for_payment = intentEmail.getStringExtra(SpecificItemPage.EMAIL_FOR_ORDER_SPE);
//        String cart_billing = cartPriceTotal.getText().toString();
//        cart_bool = true;
//        Intent intent = new Intent(this, BillingPage.class);
//        intent.putExtra(CART_BILLING, cart_billing);
//        intent.putExtra(CART_BOOL, cart_bool);
////        intent.putExtra(EMAIL_FOR_ORDER_CART,email_for_payment);
//        startActivity(intent);
//    }
//
//    // Add this method to remove items with quantity 0
//    public void removeItemWithZeroQuantity() {
//        for (int i = cartModalArrayList.size() - 1; i >= 0; i--) {
//            if (Integer.parseInt(cartModalArrayList.get(i).getCartNoItem()) == 0) {
//                cartModalArrayList.remove(i);
//                adapter.notifyItemRemoved(i);
//            }
//        }
//        saveData();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadData();
//        removeItemWithZeroQuantity();
//        adapter.notifyDataSetChanged();
//    }
//}



