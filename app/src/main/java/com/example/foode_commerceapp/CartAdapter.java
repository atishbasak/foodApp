//package com.example.foode_commerceapp;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
//
//    private ArrayList<CartModel> cartModalArrayList;
//    private Context context;
//    private SharedPreferences sharedPreferences;
//
//    public CartAdapter(ArrayList<CartModel> cartModalArrayList, Context context) {
//        this.cartModalArrayList = cartModalArrayList;
//        this.context = context;
//        this.sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CartModel modal = cartModalArrayList.get(position);
//
//        holder.cartResturantName.setText(modal.getCartResturantName());
//        holder.cartDISH.setText(modal.getCartDish());
//        holder.cartPrice.setText(modal.getCartPrice());
//        holder.cartNoItem.setText(modal.getCartNoItem());
//
//        Picasso.get().load(modal.getCartImg()).into(holder.cartIMG);
//
//        updateTotalPrice(holder, modal.getCartPrice(), Integer.parseInt(modal.getCartNoItem()));
//
//        holder.increaseItem.setOnClickListener(v -> {
//            int updatedQuantity = updateQuantity(holder.cartNoItem, 1);
//            updateTotalPrice(holder, modal.getCartPrice(), updatedQuantity);
//            updateItemInSharedPreferences(modal, updatedQuantity);
//        });
//
//        holder.decreaseItem.setOnClickListener(v -> {
//            int updatedQuantity = updateQuantity(holder.cartNoItem, -1);
//            if (updatedQuantity <= 0) {
//                removeItem(position, modal);
//            } else {
//                updateTotalPrice(holder, modal.getCartPrice(), updatedQuantity);
//                updateItemInSharedPreferences(modal, updatedQuantity);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return cartModalArrayList.size();
//    }
//
//    private void updateTotalPrice(ViewHolder holder, String price, int quantity) {
//        double itemPrice = Double.parseDouble(price);
//        double totalPrice = itemPrice * quantity;
//        holder.itemTotalPrice.setText(String.valueOf(totalPrice));
//    }
//
//    private int updateQuantity(TextView quantityView, int change) {
//        int currentQuantity = Integer.parseInt(quantityView.getText().toString());
//        int updatedQuantity = currentQuantity + change;
//        quantityView.setText(String.valueOf(updatedQuantity));
//        return updatedQuantity;
//    }
//
//    private void removeItem(int position, CartModel modal) {
//        cartModalArrayList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, cartModalArrayList.size());
//        removeItemFromSharedPreferences(modal);
//    }
//
//    private void updateItemInSharedPreferences(CartModel modal, int quantity) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(modal.getCartDish(), quantity);
//        editor.apply();
//    }
//
//    private void removeItemFromSharedPreferences(CartModel modal) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(modal.getCartDish());
//        editor.commit();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView cartIMG;
//        private TextView cartResturantName, cartDISH, cartPrice, cartNoItem;
//        private TextView increaseItem, decreaseItem, itemTotalPrice;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cartIMG = itemView.findViewById(R.id.cartIMG);
//            cartResturantName = itemView.findViewById(R.id.cartResturantName);
//            cartDISH = itemView.findViewById(R.id.cartDISH);
//            cartPrice = itemView.findViewById(R.id.cartPrice);
//            cartNoItem = itemView.findViewById(R.id.cartNoItem);
//            increaseItem = itemView.findViewById(R.id.increaseItem);
//            decreaseItem = itemView.findViewById(R.id.decraeseItem);
//            itemTotalPrice = itemView.findViewById(R.id.itemTotalPrice);
//        }
//    }
//}









package com.example.foode_commerceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<CartModel> cartModalArrayList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public CartAdapter(ArrayList<CartModel> cartModalArrayList, Context context) {
        this.cartModalArrayList = cartModalArrayList;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartModel modal = cartModalArrayList.get(position);

        holder.cartResturantName.setText(modal.getCartResturantName());
        holder.cartDISH.setText(modal.getCartDish());
        holder.cartPrice.setText(modal.getCartPrice());
        holder.cartNoItem.setText(modal.getCartNoItem());

        Picasso.get().load(modal.getCartImg()).into(holder.cartIMG);


        String item_price = holder.cartPrice.getText().toString();
        String item_quantity = holder.cartNoItem.getText().toString();

        double item_priceDBL = Double.parseDouble(item_price);
        int item_quantityINT = Integer.parseInt(item_quantity);

        double item_total_price = (item_priceDBL * item_quantityINT);
        String item_total_priceSTR = Double.toString(item_total_price);

        holder.itemTotalPrice.setText(item_total_priceSTR);

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartModalArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartModalArrayList.size());
                removeItemFromSharedPreferences(modal);
            }
        });



//        holder.increaseItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String quantity_value = holder.cartNoItem.getText().toString();
//                int quantity_valueINT = Integer.parseInt(quantity_value);
//                int updated_quantity = quantity_valueINT + 1;
//                String updated_quantitySTR = Integer.toString(updated_quantity);
//                holder.cartNoItem.setText(updated_quantitySTR);
//
//                String updated_quantityAFT = holder.cartNoItem.getText().toString();
//                int updated_quantityAFT_INT = Integer.parseInt(updated_quantityAFT);
//                double updatedItemTotalPrice = (item_priceDBL * updated_quantityAFT_INT);
//                String updatedItemTotalPriceSTR = Double.toString(updatedItemTotalPrice);
//                holder.itemTotalPrice.setText(updatedItemTotalPriceSTR);
//            }
//        });

//        holder.decreaseItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String quantity_value = holder.cartNoItem.getText().toString();
//                int quantity_valueINT = Integer.parseInt(quantity_value);
//                if (quantity_valueINT <= 1) {
//                    cartModalArrayList.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, cartModalArrayList.size());
//                    removeItemFromSharedPreferences(modal);
//                } else {
//                    int updated_quantity = quantity_valueINT - 1;
//                    String updated_quantitySTR = Integer.toString(updated_quantity);
//                    holder.cartNoItem.setText(updated_quantitySTR);
//
//                    String updated_quantityAFT = holder.cartNoItem.getText().toString();
//                    int updated_quantityAFT_INT = Integer.parseInt(updated_quantityAFT);
//                    double updatedItemTotalPrice = (item_priceDBL * updated_quantityAFT_INT);
//                    String updatedItemTotalPriceSTR = Double.toString(updatedItemTotalPrice);
//                    holder.itemTotalPrice.setText(updatedItemTotalPriceSTR);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cartModalArrayList.size();
    }

    private void removeItemFromSharedPreferences(CartModel model) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("shared preferences");
        editor.clear();
        editor.apply();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartIMG;
        private TextView cartResturantName, cartDISH, cartPrice, cartNoItem;
        private TextView itemTotalPrice;
        private Button removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartIMG = itemView.findViewById(R.id.cartIMG);
            cartResturantName = itemView.findViewById(R.id.cartResturantName);
            cartDISH = itemView.findViewById(R.id.cartDISH);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartNoItem = itemView.findViewById(R.id.cartNoItem);
            cartNoItem = itemView.findViewById(R.id.cartNoItem);
            itemTotalPrice = itemView.findViewById(R.id.itemTotalPrice);
            removeBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
}


