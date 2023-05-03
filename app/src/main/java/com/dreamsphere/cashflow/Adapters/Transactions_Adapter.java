package com.dreamsphere.cashflow.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dreamsphere.cashflow.Models.Transaction;
import com.dreamsphere.cashflow.R;
import java.util.List;

public class Transactions_Adapter extends RecyclerView.Adapter <Transactions_Adapter.ViewHolder> {

    private Context context;
    private List<Transaction> transactionList;
    private String TAG ="Adapter RecyclerVIew";
    //RecyclerViewClickListener clickListener;
    Transaction thisTransactions;
    String imageTeamPath;
    private AdapterView.OnItemClickListener onItemClickListener;





    public Transactions_Adapter() {
    }

    public Transactions_Adapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactionList = transactions;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.transaction_recyciclervirew_item, viewGroup,false);
        thisTransactions = new Transaction();

        //imageTeamPath = context.getFilesDir().getAbsolutePath() + "/images/regions/";

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String cathegory = transactionList.get(i).getCathegory();
        String description = transactionList.get(i).getDescription();
        Integer type = transactionList.get(i).getType();
        Float amount = transactionList.get(i).getAmount();
        Log.d(TAG, "onBindViewHolder: amount: "+amount +" - type: "+type);



        thisTransactions.setDescription(description);
        thisTransactions.setDay(transactionList.get(i).getDay());
        thisTransactions.setMonth(transactionList.get(i).getMonth());
        thisTransactions.setYear(transactionList.get(i).getYear());


        String date = String.format("%02d",transactionList.get(i).getDay()) + "/"+ String.format("%02d",transactionList.get(i).getMonth()+1) + "/" +transactionList.get(i).getYear().toString()  ;


        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                .error(R.drawable.ic_add);

        if (cathegory.equals("Lavoro")){
            Glide.with(context).load(R.drawable.ic_job).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }else if (cathegory.equals("Finanza")){
            Glide.with(context).load(R.drawable.ic_finance).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }else if (cathegory.equals("Entrata")){
            Glide.with(context).load(R.drawable.ic_income).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }else if (cathegory.equals("Risparmi")){
            Glide.with(context).load(R.drawable.ic_savings).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }else if (cathegory.equals("Spesa")){
            Glide.with(context).load(R.drawable.ic_shopping).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Delivery")){
            Glide.with(context).load(R.drawable.ic_food).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Cena  Fuori")){
            Glide.with(context).load(R.drawable.ic_dining).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Drinks")){
            Glide.with(context).load(R.drawable.ic_drink).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Casa")){
            Glide.with(context).load(R.drawable.ic_home).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Mutuo")){
            Glide.with(context).load(R.drawable.ic_bank).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Pulizie")){
            Glide.with(context).load(R.drawable.ic_cleaning_services).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Bolletta")){
            Glide.with(context).load(R.drawable.ic_electricity).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }

        else if (cathegory.equals("Sp. Lavoro")){
            Glide.with(context).load(R.drawable.ic_work).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Lavori")){
            Glide.with(context).load(R.drawable.ic_works).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Tasse")){
            Glide.with(context).load(R.drawable.ic_tax).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Software")){
            Glide.with(context).load(R.drawable.ic_software).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }


        else if (cathegory.equals("Riparazioni")){
            Glide.with(context).load(R.drawable.ic_engineering).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Telefono")){
            Glide.with(context).load(R.drawable.ic_phone).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Abbonamento")){
            Glide.with(context).load(R.drawable.ic_subscriptions).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Televisione")){
            Glide.with(context).load(R.drawable.ic_tv).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Palestra")){
            Glide.with(context).load(R.drawable.ic_gym).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Golf")){
            Glide.with(context).load(R.drawable.ic_golf).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Vacanza")){
            Glide.with(context).load(R.drawable.ic_holiday2).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Cinema")){
            Glide.with(context).load(R.drawable.ic_movie).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Musica")){
            Glide.with(context).load(R.drawable.ic_music).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Festa")){
            Glide.with(context).load(R.drawable.ic_party).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Animali")){
            Glide.with(context).load(R.drawable.ic_animals).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Spreco di $")){
            Glide.with(context).load(R.drawable.ic_apple).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Regalo")){
            Glide.with(context).load(R.drawable.ic_gift).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Amazon")){
            Glide.with(context).load(R.drawable.ic_amazon).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Cryptovalute")){
            Glide.with(context).load(R.drawable.ic_crypto).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Tecnologia")){
            Glide.with(context).load(R.drawable.ic_technology).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Tagliacapelli")){
            Glide.with(context).load(R.drawable.ic_barber).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Vestiti")){
            Glide.with(context).load(R.drawable.ic_clothes).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Lavanderia")){
            Glide.with(context).load(R.drawable.ic_laundry_service).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Benessere")){
            Glide.with(context).load(R.drawable.ic_spa).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Bambini")){
            Glide.with(context).load(R.drawable.ic_children).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Salute")){
            Glide.with(context).load(R.drawable.ic_health).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Farmacia")){
            Glide.with(context).load(R.drawable.ic_pharma).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Multa")){
            Glide.with(context).load(R.drawable.ic_sanctions).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Benzina")){
            Glide.with(context).load(R.drawable.ic_gas).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Auto")){
            Glide.with(context).load(R.drawable.ic_car).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Moto")){
            Glide.with(context).load(R.drawable.ic_bike).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Parcheggio")){
            Glide.with(context).load(R.drawable.ic_parking).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Trasp. Pubb")){
            Glide.with(context).load(R.drawable.ic_public_transport).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Trasporto")){
            Glide.with(context).load(R.drawable.ic_shipping).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Taxi")){
            Glide.with(context).load(R.drawable.ic_taxi).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }
        else if (cathegory.equals("Treno")){
            Glide.with(context).load(R.drawable.ic_train).apply(options).transition(DrawableTransitionOptions.withCrossFade(500)).into(viewHolder.icon_image);
        }




        if (description.isEmpty()){
            viewHolder.textview_item_description.setText(cathegory);
        }else {
            viewHolder.textview_item_description.setText(description);
        }

        if (type<0){
            viewHolder.textview_item_amount.setText("- "+String.format("%.2f",amount));
        }else {
            viewHolder.textview_item_amount.setText("+ "+String.format("%.2f",amount));
        }

        viewHolder.textview_item_date.setText(date);

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon_image;
        private TextView textview_item_date;
        private TextView textview_item_description;

        private TextView textview_item_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon_image = (ImageView) itemView.findViewById(R.id.item_image);
            textview_item_date = (TextView) itemView.findViewById(R.id.item_date);
            textview_item_description = (TextView) itemView.findViewById(R.id.item_description);
            textview_item_amount = (TextView) itemView.findViewById(R.id.item_amount);
        }


        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }






}

