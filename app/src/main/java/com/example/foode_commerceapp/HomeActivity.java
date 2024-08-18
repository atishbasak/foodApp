package com.example.foode_commerceapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    RecyclerView resturantRecycler;
    public static  final String FILTER = "ccom.example.foode_commerceapp.extra.FILTER";
    public static final String DISH_NAME = "com.example.foode_commerceapp.extra.DISH_NAME";
    private static final String SUFFELED_FOOD = "https://api.jsonbin.io/v3/b/6694292dad19ca34f887a672";
    private static final String RATING_ABOVE_FOUR = "https://api.jsonbin.io/v3/b/66c0f90bacd3cb34a875f1c6";
    private static final String WEEKLY_TOP = "https://api.jsonbin.io/v3/b/66c10323ad19ca34f897771e";

    private  int images[];
    private  String text[];
    private SliderAdapter adapter;
    private SliderView sliderView;
    String imgURL;

    //////////////////////////////////
    String homeJsonFilter;
    CardView weeklyTopButton,ratingButton;

    Boolean filter = false;
    /////////////////////////////////


    private Context context;
    private RequestQueue requestQueue;
    private ArrayList<FilterModel> listFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        resturantRecycler = findViewById(R.id.resturantRecycler);
        resturantRecycler.setLayoutManager(new LinearLayoutManager(this));
//        restaurantRecycler.setLayoutManager(new LinearLayoutManager(this));


        sliderView=findViewById(R.id.imageSlider);
        images= new int[]{R.drawable.slider1, R.drawable.slider4, R.drawable.slider2, R.drawable.slider5, R.drawable.slider3};
        text=new String[]{"","","","",""};
        adapter=new SliderAdapter(images,text);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();

        init();
        requestJsonData();

        resturantRecycler.setLayoutManager(new LinearLayoutManager(this));
        listFilter = new ArrayList<>();
    }


    private void init() {
        resturantRecycler = findViewById(R.id.resturantRecycler);
        context = HomeActivity.this;
    }

    private void requestJsonData() {

        weeklyTopButton = findViewById(R.id.weeklyTopButton);
        ratingButton = findViewById(R.id.ratingButton);

//        homeJsonFilter = SUFFELED_FOOD;

        ///////////////////////////////////
//        weeklyTopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                homeJsonFilter = WEEKLY_TOP;
//                filter = true;
//
//                requestQueue = Volley.newRequestQueue(context);
//                StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                        WEEKLY_TOP,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("record");
//                                    fetchTheData(jsonArray);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    showToast("Failed to parse JSON response");
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        showToast("API call error");
//                    }
//                }
//                ){
//                    @Override
//                    public Map<String , String> getHeaders() throws AuthFailureError {
//                        return super.getHeaders();
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//            }
//        });
//
//        ratingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                homeJsonFilter = RATING_ABOVE_FOUR;
//
//                filter = true;
//
//                requestQueue = Volley.newRequestQueue(context);
//                StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                        RATING_ABOVE_FOUR,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("record");
//                                    fetchTheData(jsonArray);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    showToast("Failed to parse JSON response");
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        showToast("API call error");
//                    }
//                }
//                ){
//                    @Override
//                    public Map<String , String> getHeaders() throws AuthFailureError {
//                        return super.getHeaders();
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//            }
//        });

        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                SUFFELED_FOOD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("record");
                            fetchTheData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Failed to parse JSON response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("API call error");
            }
        }
        ){
            @Override
            public Map<String , String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        requestQueue.add(stringRequest);



//        if(!filter){
//            requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                    SUFFELED_FOOD,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                JSONArray jsonArray = jsonObject.getJSONArray("record");
//                                fetchTheData(jsonArray);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                showToast("Failed to parse JSON response");
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    showToast("API call error");
//                }
//            }
//            ){
//                @Override
//                public Map<String , String> getHeaders() throws AuthFailureError {
//                    return super.getHeaders();
//                }
//            };
//
//            requestQueue.add(stringRequest);
//        }

        ///////////////////////////////////



//        requestQueue = Volley.newRequestQueue(context);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                SUFFELED_FOOD,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("record");
//                            fetchTheData(jsonArray);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            showToast("Failed to parse JSON response");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                showToast("API call error");
//            }
//        }
//        ){
//            @Override
//            public Map<String , String> getHeaders() throws AuthFailureError {
//                return super.getHeaders();
//            }
//        };
//
//        requestQueue.add(stringRequest);
    }

    private void fetchTheData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject product = jsonArray.getJSONObject(i);
//                imgURL = product.getString("img");
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
                showToast("Failed to parse product data");
            }
        }

        FilterAdapter adapterFilter = new FilterAdapter(listFilter, context);
        resturantRecycler.setAdapter(adapterFilter);
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public void toFilterPageBiriyani(View view) {
        String item = "biriyani";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPagePizza(View view) {
        String item = "pizza";
        boolean id = true;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageChicken(View view) {
        String item = "chicken";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageChiliChicken(View view) {
        String item = "chiliChicken";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageEggRoll(View view) {
        String item = "eggRoll";
        boolean id = true;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageCake(View view) {
        String item = "cake";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPagePaneer(View view) {
        String item = "paneer";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageBurger(View view) {
        String item = "burger";
        boolean id = true;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageSandwich(View view) {
        String item = "sandwich";
        boolean id = true;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageChinese(View view) {
        String item = "chinese";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageManchurian(View view) {
        String item = "manchurian";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageColdDrinks(View view) {
        String item = "coldDrinks";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageWeeklyTop(View view) {
        String item = "weeklyTop";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }
    public void toFilterPageRating(View view) {
        String item = "rating";
        boolean id = false;
        Intent intent = new Intent(this, FilterItemsPage.class);
        intent.putExtra(FILTER,item);
        intent.putExtra(DISH_NAME,id);
        startActivity(intent);
    }

    public void toAboutPage(View view){
        Intent intent = new Intent(this,AboutMePage.class);
        startActivity(intent);
    }

    public void toContactPage(View view ){
        Intent intent = new Intent(this, ContactMePage.class);
        startActivity(intent);
    }

//    public void toReviewPage(View view){
//        Intent intent = new Intent(this, ReviewsPage.class);
//        startActivity(intent);
//    }

    public void toCartPage(View view){
        Intent intent = new Intent(this,CartActivity.class);
        startActivity(intent);
    }

}