package com.example.foode_commerceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private ArrayList<FilterModel> listFilter;
    private Context context;

    public static final String PARSE_NAME = "com.example.foode_commerceapp.extra.PARSE_NAME";
    public static final String PARSE_PRICE = "com.example.foode_commerceapp.extra.PARSE_PRICE";
    public static final String PARSE_IMG = "com.example.foode_commerceapp.extra.PARSE_IMG";
    public static final String PARSE_BOOL= "com.example.foode_commerceapp.extra.PARSE_BOOL";
    public static final String EMAIL_FOR_ORDER_FILTER = "com.example.foode_commerceapp.extra.EMAIL_FOR_ORDER_FILTER";

    public FilterAdapter(ArrayList<FilterModel> listFilter, Context context) {
        this.listFilter = listFilter;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturant_card_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilterModel model = listFilter.get(position);
        holder.cardTITLE.setText(model.getcardTITLE());
        holder.ratingOFFOOD.setText(model.getratingOFFOOD());
        holder.dishNAME.setText(model.getdishNAME());
        holder.foodDESTINATION.setText(model.getfoodDESTINATION());
        holder.foodPRICE.setText(model.getfoodPRICE());
        holder.forNUMMBERpeople.setText(model.getforNUMMBERpeople());
        holder.deliveryTIME.setText(model.getdeliveryTIME());
        holder.distance.setText(model.getdistance());

        Picasso.get().load(model.getcardIMG()).into(holder.cardIMG);

        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentId = ((Activity)context).getIntent();
                boolean ID = intentId.getBooleanExtra(HomeActivity.DISH_NAME,false);
//                String email_for_payment = intentId.getStringExtra(HomeActivity.EMAIL_FOR_ORDER_HOME);
//                Toast.makeText(context, ""+ID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,SpecificItemPage.class);
                intent.putExtra(PARSE_IMG,listFilter.get(position).getcardIMG());
                intent.putExtra(PARSE_NAME,listFilter.get(position).getdishNAME());
                intent.putExtra(PARSE_PRICE,listFilter.get(position).getfoodPRICE());
                intent.putExtra(PARSE_BOOL,ID);
//                intent.putExtra(EMAIL_FOR_ORDER_FILTER,email_for_payment);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIMG;
        TextView cardTITLE;
        TextView ratingOFFOOD;
        TextView dishNAME;
        TextView foodDESTINATION;
        TextView foodPRICE;
        TextView forNUMMBERpeople;
        TextView deliveryTIME;
        TextView distance;
        CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardIMG = itemView.findViewById(R.id.cardIMG);
            cardTITLE = itemView.findViewById(R.id.cardTITLE);
            ratingOFFOOD = itemView.findViewById(R.id.ratingOFFOOD);
            dishNAME = itemView.findViewById(R.id.dishNAME);
            foodDESTINATION = itemView.findViewById(R.id.foodDESTINATION);
            foodPRICE = itemView.findViewById(R.id.foodPRICE);
            forNUMMBERpeople = itemView.findViewById(R.id.forNUMMBERpeople);
            deliveryTIME = itemView.findViewById(R.id.deliveryTIME);
            distance = itemView.findViewById(R.id.distance);
            itemCard = itemView.findViewById(R.id.itemCard);
        }
    }
}
