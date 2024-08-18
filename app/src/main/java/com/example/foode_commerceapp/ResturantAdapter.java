package com.example.foode_commerceapp;

//public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {

//    private ArrayList<CardModel> list;
//    private Context context;
//
//    public ResturantAdapter(ArrayList<CardModel> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.resturant_card_recycle, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CardModel model = list.get(position);
//        holder.cardIMG.setImageResource(model.getcardIMG());
//        holder.cardTITLE.setText(model.getcardTITLE());
//        holder.ratingOFFOOD.setText(model.getratingOFFOOD());
//        holder.dishNAME.setText(model.getdishNAME());
//        holder.foodDESTINATION.setText(model.getfoodDESTINATION());
//        holder.foodPRICE.setText(model.getfoodPRICE());
//        holder.forNUMMBERpeople.setText(model.getforNUMMBERpeople());
//        holder.deliveryTIME.setText(model.getdeliveryTIME());
//        holder.distance.setText(model.getdistance());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView cardIMG;
//        TextView cardTITLE;
//        TextView ratingOFFOOD;
//        TextView dishNAME;
//        TextView foodDESTINATION;
//        TextView foodPRICE;
//        TextView forNUMMBERpeople;
//        TextView deliveryTIME;
//        TextView distance;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cardIMG = itemView.findViewById(R.id.cardIMG);
//            cardTITLE = itemView.findViewById(R.id.cardTITLE);
//            ratingOFFOOD = itemView.findViewById(R.id.ratingOFFOOD);
//            dishNAME = itemView.findViewById(R.id.dishNAME);
//            foodDESTINATION = itemView.findViewById(R.id.foodDESTINATION);
//            foodPRICE = itemView.findViewById(R.id.foodPRICE);
//            forNUMMBERpeople = itemView.findViewById(R.id.forNUMMBERpeople);
//            deliveryTIME = itemView.findViewById(R.id.deliveryTIME);
//            distance = itemView.findViewById(R.id.distance);
//        }
//    }
//}
