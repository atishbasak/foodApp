package com.example.foode_commerceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ToppingsPage extends AppCompatActivity {
    TextView toppingsPageFinalPrice;
    ImageView toppingsMinus;
    TextView plus;
    TextView toppingQuanValue;
    RecyclerView toppingsbellowRecycler, toppingsCardRecycler;
    ArrayList<String> toppingsBellowitems;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagermain;
    ToppingsBellowAdapter toppingsBellowAdapter;
    ToppingsMainAdapter toppingsMainAdapter;

    public static final String FINAL_PRICE = "com.example.foode_commerceapp.extra.FINAL_PRICE";
    public static final String TOP_BOOL = "com.example.foode_commerceapp.extra.TOP_BOOL";
    public static final String EMAIL_FOR_ORDER_TOPPINGS = "com.example.foode_commerceapp.extra.EMAIL_FOR_ORDER_TOPPINGS";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toppings_page);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toppingsbellowRecycler = findViewById(R.id.toppingsbellowRecycler);
        toppingsBellowitems = new ArrayList<>();

        //layout manager for toppings bellow btn
        linearLayoutManager = new LinearLayoutManager(ToppingsPage.this, LinearLayoutManager.HORIZONTAL, false);
        toppingsBellowAdapter = new ToppingsBellowAdapter(toppingsBellowitems);
        toppingsbellowRecycler.setLayoutManager(linearLayoutManager);
        toppingsbellowRecycler.setAdapter(toppingsBellowAdapter);

        // items adding for toppings main recyclerView
        toppingsCardRecycler = findViewById(R.id.toppingsCardRecycler);
        ArrayList<ToppingsMainModel> toppingsMainItem = new ArrayList<>();
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.tomato, "Tomato", "25"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.onion, "Onion", "33"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.chili_flex, "Chili Flex", "20"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.capsicum, "Capsicum", "35"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.cabbege, "Cabbage", "15"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.chese, "Cheese", "40"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.chicken, "chicken", "40"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.souce, "Sauce", "10"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.chicken_patty, "Patty", "40"));
        toppingsMainItem.add(new ToppingsMainModel(R.drawable.cucamber, "Cucumber", "10"));

        // layout manager for toppings main btn
        linearLayoutManagermain = new LinearLayoutManager(ToppingsPage.this, LinearLayoutManager.HORIZONTAL, false);
        toppingsMainAdapter = new ToppingsMainAdapter(toppingsMainItem, toppingsbellowRecycler);
        toppingsCardRecycler.setLayoutManager(linearLayoutManagermain);
        toppingsCardRecycler.setAdapter(toppingsMainAdapter);

        // find-view into current activity
        toppingsPageFinalPrice = findViewById(R.id.toppingsPageFinalPrice);
        toppingsMinus = findViewById(R.id.toppingsMinus);
        toppingQuanValue = findViewById(R.id.toppingQuanValue);
        plus = findViewById(R.id.plus);

        // get data from previous activity
        Intent intent = getIntent();
        String order_price_value = intent.getStringExtra(SpecificItemPage.EXTRA_ORDER_VALUE); // 349
        String order_quantity = intent.getStringExtra(SpecificItemPage.ORDER_QUANTITY); // 3

        // pre final price calculation.
        double order_priceINT = Double.parseDouble(order_price_value);
        double order_quantityPre = Double.parseDouble(order_quantity);
        double final_pricePre = order_priceINT * order_quantityPre;
        double final_pricePre_decimal = Double.parseDouble(df.format(final_pricePre));
        String final_pricePreString = Double.toString(final_pricePre_decimal);
        toppingsPageFinalPrice.setText(final_pricePreString); // 1047.0
        toppingQuanValue.setText(order_quantity);

        // type casting for new final calculation
        int order_quantityINT = Integer.parseInt(order_quantity);

        // for decrement calculation
        toppingsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity_topping = toppingQuanValue.getText().toString();
                int quantity_ToppingINT = Integer.parseInt(quantity_topping);
                if (quantity_ToppingINT <= 1) {
                    String pre_price = toppingsPageFinalPrice.getText().toString();
                    toppingsPageFinalPrice.setText(pre_price);
                } else {
                    String pre_quantity = toppingQuanValue.getText().toString();
                    String pre_final_price = toppingsPageFinalPrice.getText().toString();

                    double pre_final_priceDBL = Double.parseDouble(pre_final_price);
                    int pre_quantityINT = Integer.parseInt(pre_quantity);

                    //  toppings total price calculation
                    double toppings_price_calc = ((pre_final_priceDBL - (order_priceINT * pre_quantityINT))/pre_quantityINT);

                    // increment of quantity
                    int updated_quantity = pre_quantityINT - 1;

                    //calculation for new price;
                    double final_price = ((order_priceINT * updated_quantity) + (toppings_price_calc * updated_quantity));

                    // setting into the text-view
                    double final_price_decimal = Double.parseDouble(df.format(final_price));
                    String final_priceSTR = Double.toString(final_price_decimal);
                    String updated_quantitySTr = Integer.toString(updated_quantity);
                    toppingQuanValue.setText(updated_quantitySTr);
                    toppingsPageFinalPrice.setText(final_priceSTR);;
                }
            }
        });

        // for increment calculation
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // after increment calculation
                String pre_final_price = toppingsPageFinalPrice.getText().toString();
                String pre_quantity = toppingQuanValue.getText().toString();

                double pre_final_priceDBL = Double.parseDouble(pre_final_price);
                int pre_quantityINT = Integer.parseInt(pre_quantity);

                //  toppings total price calculation
                double toppings_price_calc = ((pre_final_priceDBL - (order_priceINT * pre_quantityINT))/pre_quantityINT);

                // increment of quantity
                int updated_quantity = pre_quantityINT + 1;

                //calculation for new price;
                double final_price = ((order_priceINT * updated_quantity) + (toppings_price_calc * updated_quantity));

                // setting into the text-view
                double final_price_decimal = Double.parseDouble(df.format(final_price));
                String final_priceSTR = Double.toString(final_price_decimal);
                String updated_quantitySTr = Integer.toString(updated_quantity);
                toppingQuanValue.setText(updated_quantitySTr);
                toppingsPageFinalPrice.setText(final_priceSTR);
            }
        });
    }

    // toppings bellow adapter
    class ToppingsBellowAdapter extends RecyclerView.Adapter<ToppingsBellowAdapter.ToppingsBellowHolder> {

        ArrayList<String> toppingsBellowData;

        public ToppingsBellowAdapter(ArrayList<String> toppingsBellowData) {
            this.toppingsBellowData = toppingsBellowData;
        }

        @NonNull
        @Override
        public ToppingsBellowAdapter.ToppingsBellowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ToppingsPage.this).inflate(R.layout.toppings_button, parent, false);
            return new ToppingsBellowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ToppingsBellowAdapter.ToppingsBellowHolder holder, int position) {
            holder.toppingsBellowDataId.setText(toppingsBellowData.get(position));

            holder.toppingsBellowCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String card_price = holder.toppingsCardPrice.getText().toString();
//                    double card_priceDBL = Double.parseDouble(card_price);
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        toppingsBellowData.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, toppingsBellowData.size());

                        String cardID = holder.toppingsBellowDataId.getText().toString();
                        double card_priceDBL = 0.0;
                        if(cardID.equals("Tomato")){
                            card_priceDBL = 25.0;
                        } else if (cardID.equals("Onion")) {
                            card_priceDBL = 33.0;
                        } else if (cardID.equals("Chili Flex")) {
                            card_priceDBL = 20.0;
                        } else if (cardID.equals("Capsicum")) {
                            card_priceDBL = 35.0;
                        } else if (cardID.equals("Cabbage")) {
                            card_priceDBL = 15.0;
                        } else if (cardID.equals("Cheese")) {
                            card_priceDBL = 40.0;
                        } else if (cardID.equals("Chicken")) {
                            card_priceDBL = 40.0;
                        } else if (cardID.equals("Sauce")) {
                            card_priceDBL = 10.0;
                        } else if (cardID.equals("Patty")) {
                            card_priceDBL = 40.0;
                        } else if (cardID.equals("Cucumber")) {
                            card_priceDBL = 10.0;
                        }
                        String updated_bill = toppingsPageFinalPrice.getText().toString();
                        double updated_billDBL = Double.parseDouble(updated_bill);
                        String quantity_updated = toppingQuanValue.getText().toString();
                        int quantity_updatedINT = Integer.parseInt(quantity_updated);
                        double newUpdated_bill = updated_billDBL - (card_priceDBL * quantity_updatedINT);
                        String newUpdated_billSTR = Double.toString(newUpdated_bill);
                        toppingsPageFinalPrice.setText(newUpdated_billSTR);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return toppingsBellowData.size();
        }

        class ToppingsBellowHolder extends RecyclerView.ViewHolder {
            TextView toppingsBellowDataId,toppingsCardPrice,toppingQuanValue;
            CardView toppingsBellowCard;

            public ToppingsBellowHolder(@NonNull View itemView) {
                super(itemView);
                toppingsBellowDataId = itemView.findViewById(R.id.toppingsBellowDataId);
                toppingsBellowCard = itemView.findViewById(R.id.toppingsBellowCard);
                toppingsCardPrice = itemView.findViewById(R.id.toppingsCardPrice);
                toppingQuanValue = itemView.findViewById(R.id.toppingQuanValue);
            }
        }
    }

    //  toppings main adapter
    class ToppingsMainAdapter extends RecyclerView.Adapter<ToppingsMainAdapter.ToppingsMainHolder> {
        ArrayList<ToppingsMainModel> ToppingsMainItem;
        ArrayList<String> toppingsBellowitems;
        LinearLayoutManager linearLayoutManager;
        ToppingsBellowAdapter toppingsBellowAdapter;
        RecyclerView toppingsbellowRecycler;

        public ToppingsMainAdapter(ArrayList<ToppingsMainModel> ToppingsMainItem, RecyclerView toppingsbellowRecycler) {
            this.ToppingsMainItem = ToppingsMainItem;
            this.toppingsbellowRecycler = toppingsbellowRecycler;
            this.toppingsBellowitems = new ArrayList<>();
            this.linearLayoutManager = new LinearLayoutManager(toppingsbellowRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
            this.toppingsBellowAdapter = new ToppingsBellowAdapter(toppingsBellowitems);
            this.toppingsbellowRecycler.setLayoutManager(linearLayoutManager);
            this.toppingsbellowRecycler.setAdapter(toppingsBellowAdapter);
        }

        @NonNull
        @Override
        public ToppingsMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toppings_cards, parent, false);
            return new ToppingsMainHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ToppingsMainHolder holder, int position) {
            ToppingsMainModel model = ToppingsMainItem.get(position);
            holder.toppingsCardImg.setImageResource(model.gettoppingsCardImg());
            holder.toppingsCardName.setText(model.gettoppingsCardName());
            holder.toppingsCardPrice.setText(model.gettoppingsCardPrice());

            holder.toppingsTotalcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toppingName = holder.toppingsCardName.getText().toString();
                    toppingsBellowitems.add(toppingName);
                    toppingsBellowAdapter.notifyItemInserted(toppingsBellowitems.size() - 1);
                    String card_price = holder.toppingsCardPrice.getText().toString();
                    double card_priceDBL = Double.parseDouble(card_price);
                    String updated_bill = toppingsPageFinalPrice.getText().toString();
                    double updated_billDBL = Double.parseDouble(updated_bill);
                    String quantity_updated = toppingQuanValue.getText().toString();
                    int quantity_updatedINT = Integer.parseInt(quantity_updated);
                    double newUpdated_bill = (card_priceDBL * quantity_updatedINT) + updated_billDBL;
                    String newUpdated_billSTR = Double.toString(newUpdated_bill);
                    toppingsPageFinalPrice.setText(newUpdated_billSTR);
                }
            });
        }

        @Override
        public int getItemCount() {
            return ToppingsMainItem.size();
        }

        public class ToppingsMainHolder extends RecyclerView.ViewHolder {
            ImageView toppingsCardImg;
            TextView toppingsCardName, toppingsCardPrice,toppingQuanValue;
            CardView toppingsTotalcard, toppingsBellowCard;

            public ToppingsMainHolder(@NonNull View itemView) {
                super(itemView);
                toppingsCardImg = itemView.findViewById(R.id.toppingsCardImg);
                toppingsCardName = itemView.findViewById(R.id.toppingsCardName);
                toppingsCardPrice = itemView.findViewById(R.id.toppingsCardPrice);
                toppingsTotalcard = itemView.findViewById(R.id.toppingsTotalcard);
                toppingsBellowCard = itemView.findViewById(R.id.toppingsBellowCard);
                toppingQuanValue = itemView.findViewById(R.id.toppingQuanValue);
            }
        }
    }

    // for sending to the next activity
    public void openBillingActivity(View view) {
        Intent intentEmail = getIntent();
//        String email_for_payment = intentEmail.getStringExtra(SpecificItemPage.EMAIL_FOR_ORDER_SPE);
        boolean top_bool = true;
        Intent intent = new Intent(this, BillingPage.class);
        String final_price_toppings = toppingsPageFinalPrice.getText().toString();
        intent.putExtra(FINAL_PRICE,final_price_toppings);
        intent.putExtra(TOP_BOOL,top_bool);
//        intent.putExtra(EMAIL_FOR_ORDER_TOPPINGS,email_for_payment);
        startActivity(intent);
    }

    // intent for going to next activity
    public void backToSpecificItemPage(View view) {
        Intent intent = new Intent(this, SpecificItemPage.class);
        startActivity(intent);
    }
}
