package com.example.foode_commerceapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class FilterItemsPage extends AppCompatActivity {

    String jsonFilter;
    private static final String JSON_BIRIYANI = "https://api.jsonbin.io/v3/b/669426bde41b4d34e411e6e4";
    private static final String JSON_BURGER = "https://api.jsonbin.io/v3/b/66942749e41b4d34e411e70c";
    private static final String JSON_CAKE = "https://api.jsonbin.io/v3/b/66942774acd3cb34a86620cf";
    private static final String JSON_CHICKENDISH = "https://api.jsonbin.io/v3/b/6694279fad19ca34f887a603";
    private static final String JSON_CHILICHICKEN = "https://api.jsonbin.io/v3/b/669427c5ad19ca34f887a610";
    private static final String JSON_CHINESE = "https://api.jsonbin.io/v3/b/669427eee41b4d34e411e74a";
    private static final String JSON_COLDDRINKS = "https://api.jsonbin.io/v3/b/66942817ad19ca34f887a629";
    private static final String JSON_EGGROLL = "https://api.jsonbin.io/v3/b/66942843e41b4d34e411e765";
    private static final String JSON_MANCHURIAN = "https://api.jsonbin.io/v3/b/66942872e41b4d34e411e775";
    private static final String JSON_PANEER = "https://api.jsonbin.io/v3/b/6694289be41b4d34e411e77f";
    private static final String JSON_PIZZA = "https://api.jsonbin.io/v3/b/669428c2acd3cb34a866212c";
    private static final String JSON_SANDWICH = "https://api.jsonbin.io/v3/b/669428f2e41b4d34e411e79c";
    private static final String RATING_ABOVE_FOUR = "https://api.jsonbin.io/v3/b/66c0f90bacd3cb34a875f1c6";
    private static final String WEEKLY_TOP = "https://api.jsonbin.io/v3/b/66c10323ad19ca34f897771e";

    public static final String DISH_NAMETOSPE = "com.example.foode_commerceapp.extra.DISH_NAMETOSPE";

    private Context context;
    private RequestQueue requestQueue;

    private RecyclerView filterRecycler;
    private ArrayList<FilterModel> listFilter;


    TextView dishNAME;
    TextView foodPRICE;
    ImageView cardIMG;
    String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_items_page);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init();
        requestJsonData();

        filterRecycler.setLayoutManager(new LinearLayoutManager(this));
        listFilter = new ArrayList<>();
    }

    private void init() {
        filterRecycler = findViewById(R.id.filterRecycler);
        context = FilterItemsPage.this;
    }

    private void requestJsonData() {

        Intent intent = getIntent();
        String itemFilter = intent.getStringExtra(HomeActivity.FILTER);
        String biriyani = "biriyani";
        String pizza = "pizza";
        String chicken = "chicken";
        String chiliChicken = "chiliChicken";
        String eggRoll = "eggRoll";
        String cake = "cake";
        String paneer = "paneer";
        String burger = "burger";
        String sandwich = "sandwich";
        String chinese = "chinese";
        String manchurian = "manchurian";
        String coldDrinks = "coldDrinks";
        String weeklyTop = "weeklyTop";
        String rating = "rating";


        if (itemFilter.equals(biriyani)) {
            jsonFilter = JSON_BIRIYANI;
        } else if (itemFilter.equals(pizza)) {
            jsonFilter = JSON_PIZZA;
        } else if (itemFilter.equals(chicken)) {
            jsonFilter = JSON_CHICKENDISH;
        } else if (itemFilter.equals(chiliChicken)) {
            jsonFilter = JSON_CHILICHICKEN;
        } else if (itemFilter.equals(eggRoll)) {
            jsonFilter = JSON_EGGROLL;
        } else if (itemFilter.equals(cake)) {
            jsonFilter = JSON_CAKE;
        } else if (itemFilter.equals(paneer)) {
            jsonFilter = JSON_PANEER;
        } else if (itemFilter.equals(burger)) {
            jsonFilter = JSON_BURGER;
        } else if (itemFilter.equals(sandwich)) {
            jsonFilter = JSON_SANDWICH;
        } else if (itemFilter.equals(chinese)) {
            jsonFilter = JSON_CHINESE;
        } else if (itemFilter.equals(manchurian)) {
            jsonFilter = JSON_MANCHURIAN;
        } else if (itemFilter.equals(coldDrinks)) {
            jsonFilter = JSON_COLDDRINKS;
        } else if (itemFilter.equals(weeklyTop)) {
            jsonFilter = WEEKLY_TOP;
        } else if (itemFilter.equals(rating)) {
            jsonFilter = RATING_ABOVE_FOUR;
        }


        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                jsonFilter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("record");
                            fetchTheData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            showToast("Failed to parse JSON response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                showToast("API call error");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQueue.add(stringRequest);
    }

    private void fetchTheData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject product = jsonArray.getJSONObject(i);
                imgURL = product.getString("img");
                listFilter.add(new FilterModel(
                        product.getString("img"),
                        product.getString("title"),
                        product.getString("rating"),
                        product.getString("name"),
                        product.getString("foodDestination"),
                        product.getString("price"),
                        product.getString("numberOfPeople"),
                        product.getString("deliveryTime"),
                        product.getString("distance")
                ));
            } catch (JSONException e) {
                e.printStackTrace();
//                showToast("Failed to parse product data");
            }
        }

        FilterAdapter adapterFilter = new FilterAdapter(listFilter, context);
        filterRecycler.setAdapter(adapterFilter);
    }
    public void toHomePage(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
