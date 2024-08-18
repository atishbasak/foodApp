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


