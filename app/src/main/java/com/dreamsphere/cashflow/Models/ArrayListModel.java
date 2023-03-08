package com.dreamsphere.cashflow.Models;

import com.dreamsphere.cashflow.R;

import java.util.ArrayList;

public class ArrayListModel {

    public ArrayList<Cathegories> setListData(){

        ArrayList<Cathegories> arrayList = new ArrayList<>();


        //incomes
        arrayList.add(new Cathegories(R.drawable.ic_job, "Lavoro"));
        arrayList.add(new Cathegories(R.drawable.ic_finance, "Finanza"));
        arrayList.add(new Cathegories(R.drawable.ic_income, "Entrata"));
        arrayList.add(new Cathegories(R.drawable.ic_savings, "Risparmi"));
        //arrayList.add(new Cathegories(R.drawable.ic_work, "Dividendi"));





        //spese quotidiane

        //arrayList.add(new Cathegories(R.drawable.ic_coffee, "Caffè"));
        arrayList.add(new Cathegories(R.drawable.ic_shopping, "Spesa"));
        arrayList.add(new Cathegories(R.drawable.ic_food, "Delivery"));
        arrayList.add(new Cathegories(R.drawable.ic_dining, "Cena Fuori"));
        arrayList.add(new Cathegories(R.drawable.ic_drink, "Drinks"));



        //scadenze mensili //casa
        arrayList.add(new Cathegories(R.drawable.ic_home, "Casa"));
        arrayList.add(new Cathegories(R.drawable.ic_bank, "Mutuo"));
        arrayList.add(new Cathegories(R.drawable.ic_cleaning_services, "Pulizie"));
        arrayList.add(new Cathegories(R.drawable.ic_electricity, "Bolletta"));
        arrayList.add(new Cathegories(R.drawable.ic_engineering, "Riparazioni"));
        arrayList.add(new Cathegories(R.drawable.ic_phone, "Telefono"));
        arrayList.add(new Cathegories(R.drawable.ic_subscriptions, "Abbonamento"));
        arrayList.add(new Cathegories(R.drawable.ic_tv, "Televisione"));



        //tempo libero
        arrayList.add(new Cathegories(R.drawable.ic_gym, "Palestra"));
        arrayList.add(new Cathegories(R.drawable.ic_golf, "Golf"));
        arrayList.add(new Cathegories(R.drawable.ic_holiday2, "Vacanza"));
        //arrayList.add(new Cathegories(R.drawable.ic_holiday3, "Vacanza"));
        arrayList.add(new Cathegories(R.drawable.ic_movie, "Cinema"));
        arrayList.add(new Cathegories(R.drawable.ic_music, "Musica"));
        arrayList.add(new Cathegories(R.drawable.ic_party, "Festa"));


        //spese saltuarie
        arrayList.add(new Cathegories(R.drawable.ic_animals, "Animali"));
        arrayList.add(new Cathegories(R.drawable.ic_apple, "Spreco di $"));
        arrayList.add(new Cathegories(R.drawable.ic_gift, "Regalo"));
        arrayList.add(new Cathegories(R.drawable.ic_amazon, "Amazon"));
        arrayList.add(new Cathegories(R.drawable.ic_crypto, "Criptovalute"));
        arrayList.add(new Cathegories(R.drawable.ic_technology, "Tecnologia"));


        arrayList.add(new Cathegories(R.drawable.ic_barber, "Tagliacapelli"));
        arrayList.add(new Cathegories(R.drawable.ic_clothes, "Vestiti"));
        arrayList.add(new Cathegories(R.drawable.ic_laundry_service, "Lavanderia"));
        arrayList.add(new Cathegories(R.drawable.ic_spa, "Benessere"));


        // problemi
        arrayList.add(new Cathegories(R.drawable.ic_children, "Bambini"));
        arrayList.add(new Cathegories(R.drawable.ic_health, "Salute"));
        arrayList.add(new Cathegories(R.drawable.ic_pharma, "Farmacia"));
        //arrayList.add(new Cathegories(R.drawable.ic_incident, "Incidente"));
        arrayList.add(new Cathegories(R.drawable.ic_sanctions, "Multa"));


        //trasporti
        arrayList.add(new Cathegories(R.drawable.ic_gas, "Benzina"));
        arrayList.add(new Cathegories(R.drawable.ic_car, "Auto"));
        arrayList.add(new Cathegories(R.drawable.ic_bike, "Moto"));
        arrayList.add(new Cathegories(R.drawable.ic_parking, "Parcheggio"));
        arrayList.add(new Cathegories(R.drawable.ic_public_transport, "Trasp. Pubb"));
        arrayList.add(new Cathegories(R.drawable.ic_shipping, "Trasporto"));
        arrayList.add(new Cathegories(R.drawable.ic_taxi, "Taxi"));
        arrayList.add(new Cathegories(R.drawable.ic_train, "Treno"));





        return  arrayList;

    }

}
