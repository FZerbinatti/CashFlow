package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsphere.cashflow.Adapters.RecyclerItemClickListener;
import com.dreamsphere.cashflow.Adapters.Transactions_Adapter;
import com.dreamsphere.cashflow.Models.Transaction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "XYZ MainActivity ";
    ImageView img_test;
    Context context;
    DatabaseHelper mDatabaseHelper;
    ImageButton button_add_expense, button_add_income, button_month_after, button_month_before, imageButtonCalendar, ic_settings;
    TextView id_month, tv_expenses, tv_incomes, tv_balance;
    ArrayList<Transaction> currentMonthTransactions, currentExpenses, currentIncomes;
    PieChart pieChart;
    RecyclerView recycler_transactions;
    Transactions_Adapter transactions_adapter;
    Integer currentMonth;
    DatabaseHelper databaseHelper;
    Calendar myCalendar;
    Long start_date;
    Long end_date;
    DatePickerDialog datePickerDialog1, datePickerDialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= this;
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        button_add_expense = findViewById(R.id.button_add_expenses);
        button_add_income = findViewById(R.id.button_add_income);
        id_month = findViewById(R.id.id_month);
        pieChart = findViewById(R.id.pieChart);
        recycler_transactions = findViewById(R.id.recycler_transactions);
        imageButtonCalendar = findViewById(R.id.imageButtonCalendar);
        databaseHelper = new DatabaseHelper(this);
        myCalendar = Calendar.getInstance();
        ic_settings = findViewById(R.id.ic_settings);


        tv_expenses = findViewById(R.id.tv_expenses);
        tv_incomes = findViewById(R.id.tv_incomes);
        tv_balance = findViewById(R.id.tv_balance);

        button_month_after = findViewById(R.id.button_month_after);
        button_month_before = findViewById(R.id.button_month_before);

        currentMonth = myCalendar.get(Calendar.MONTH);

        //set del mese corrente in alto
        //String monthName=(String)android.text.format.DateFormat.format("MMMM", new Date());
        //id_month.setText(monthName + Calendar.getInstance().get(Calendar.YEAR));

        id_month.setText(getMonthName(currentMonth));

        currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);



        loadGraph(currentMonthTransactions);

        loadListView(currentMonthTransactions);


        //calcolateNumbers in the page
        calcolateFieldsValues(currentMonthTransactions);


        //tasto per aggiungere spesa
        button_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("MODIFY", false);
                intent.putExtra("TYPE", -1);
                startActivity(intent);
            }
        });


        button_add_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("MODIFY", false);
                intent.putExtra("TYPE", 1);
                startActivity(intent);
            }
        });

        tv_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
            }
        });

        tv_incomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentMonthTransactions = mDatabaseHelper.getMonthIncomes(currentMonth);
                currentIncomes = new ArrayList<>();
                for (int i=0; i<currentMonthTransactions.size(); i++){
                    if (currentMonthTransactions.get(i).getType()>0){
                        currentIncomes.add(currentMonthTransactions.get(i));
                    }
                }
                loadListView(currentIncomes);
                loadGraph(currentIncomes);
            }
        });

        tv_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentExpenses = new ArrayList<>();
                for (int i=0; i<currentMonthTransactions.size(); i++){
                    if (currentMonthTransactions.get(i).getType()<0){
                        currentExpenses.add(currentMonthTransactions.get(i));
                    }
                }
                loadListView(currentExpenses);
                loadGraph(currentExpenses);
            }
        });

        buttonCalendar();

        buttonSettings();


//decimo minuto mane

        button_month_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth++;
                currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
                id_month.setText(getMonthName(currentMonth));
            }
        });

        button_month_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth--;
                currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
                id_month.setText(getMonthName(currentMonth));
            }
        });




    }

    private void buttonSettings() {

        ic_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    private void buttonCalendar() {

        /*imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                DatePicker picker = new DatePicker(context);
                picker.setCalendarViewShown(false);

                builder.setTitle("Data Inizio");
                builder.setView(picker);
                builder.setNegativeButton("Cancella", null);
                builder.setPositiveButton("imposta", null, builder.);

                builder.show();

            }
        });*/
        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_date = 0L;
                end_date    = 0L;

                datePickerDialog1 = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
                                datePickerDialog1.setButton(DatePickerDialog.BUTTON_POSITIVE,"DATA INIZIO" ,datePickerDialog1);
                                String selectedDate = year+"-"+String.format("%02d", monthOfYear+1)+"-"+String.format("%02d", dayOfMonth);
                                Log.d(TAG, "onDateSet: START DATE: "+selectedDate);
                                start_date = datetimeToMillis(selectedDate);


                                DatePickerDialog datePickerDialog2 = new DatePickerDialog(MainActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth ) {


                                                datePickerDialog1.setMessage("Data Fine");
                                                String selectedDate = year+"-"+String.format("%02d", monthOfYear+1)+"-"+String.format("%02d", dayOfMonth);
                                                Log.d(TAG, "onDateSet: END DATE: "+selectedDate);
                                                end_date = datetimeToMillis(selectedDate);


                                                ArrayList<Transaction> periodTransactions = databaseHelper.getPeriodTransactions(start_date, end_date);
                                                loadListView(periodTransactions);
                                                loadGraph(periodTransactions);



                                            }
                                        },  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                                datePickerDialog2.setTitle("Data Fine");
                                datePickerDialog2.show();


                            }
                        },  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog1.setTitle("Data Inizio");
                datePickerDialog1.show();
            }
        });

    }

    private void loadGraph(ArrayList<Transaction> currentMonthTransactions) {

        Log.d(TAG, "onCreate: graph size: "+currentMonthTransactions.size());
        ArrayList<PieEntry> pieTransactions = new ArrayList<>();
        for (int i=0; i<currentMonthTransactions.size(); i++){
            if (currentMonthTransactions.get(i).getDescription().isEmpty()){
                pieTransactions.add(new PieEntry(currentMonthTransactions.get(i).getAmount(), currentMonthTransactions.get(i).getCathegory()));
            }else {
                pieTransactions.add(new PieEntry(currentMonthTransactions.get(i).getAmount(), currentMonthTransactions.get(i).getDescription()));
            }
        }

        PieDataSet pieDataSet = new PieDataSet(pieTransactions, "");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        pieDataSet.setColors(colors);

        //pieDataSet.setColors((ColorTemplate.COLORFUL_COLORS));
        //pieDataSet.setValueTextColor(Color.RED);
        pieDataSet.setValueTextSize(16f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);


        //pieChart.setCenterText("Test");
        pieChart.setHoleRadius(70f);
        pieChart.animate();

    }

    private void calcolateFieldsValues(ArrayList<Transaction> currentMonthTransactions) {
        Float expenses =0f, incomes =0f, totalBalance =0f;

        for (int i=0; i<currentMonthTransactions.size(); i++){
            if (currentMonthTransactions.get(i).getType()>0){
                incomes+=currentMonthTransactions.get(i).getAmount();
            }else {
                expenses+=currentMonthTransactions.get(i).getAmount();
            }

        }
        totalBalance = incomes-expenses;
        tv_balance.setText("Bilancio:"+  String.format("%.2f",totalBalance));
        tv_expenses.setText("-"+String.format("%.2f",expenses));
        tv_incomes.setText("+"+String.format("%.2f",incomes));

    }


    private void loadListView(ArrayList<Transaction> transactions) {
        transactions_adapter = new Transactions_Adapter(this, transactions);
        transactions_adapter.notifyDataSetChanged();
        recycler_transactions.setLayoutManager(new LinearLayoutManager(this));


        recycler_transactions.setAdapter(transactions_adapter);

        recycler_transactions.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recycler_transactions , new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Log.d(TAG, "onLongItemClick: "+ position);

                        //questo è il numero dellitrem nell lista che devi modificare
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Modifica");
                        alertDialog.setMessage("Modificare Elemento?");
                        /*alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                                        Transaction transaction = transactions.get(position);
                                        intent.putExtra("ELEMENT_TO_MODIFY", transaction);

                                        dialog.dismiss();
                                    }
                                });*/
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "MODIFICA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                                intent.putExtra("MODIFY", true);
                                intent.putExtra("TYPE", transactions.get(position).getType());
                                intent.putExtra("AMOUNT", transactions.get(position).getAmount().toString());
                                intent.putExtra("MILLIS", transactions.get(position).getMilliseconds().toString());
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ELIMINA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.removeEntry(transactions.get(position).getMilliseconds());
                                transactions.remove(position);
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        alertDialog.show();

                    }
                })
        );




    }

    public String getMonthName(Integer month){

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        cal.set(Calendar.MONTH,month);
        String month_name = month_date.format(cal.getTime()) + Calendar.getInstance().get(Calendar.YEAR);


        return month_name;

    }

    public Long datetimeToMillis(String datetime) {
        //in base agli ID dell'array list, trova la data sucessiva o coincidente a quella attuale
        Integer itemPosition=0;
        // Log.d(TAG, "loadMatchDays: "+matchDays.get(i));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date strDate = null;

        try {
            strDate = sdf.parse(convertDatetimeZtoLocale(datetime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long matchTimeMillis = strDate.getTime();
        //Log.d(TAG, "datetimeToMillis: "+matchTimeMillis);
        return matchTimeMillis;
    }

    public String convertDatetimeZtoLocale(String datetime){
        //Log.d(TAG, "convertDatetimeZtoLocale: eating this: "+datetime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date strDate = null;

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        String formattedDate = sdf.format(date);

        //Log.d(TAG, "convertDatetimeZtoLocale: shitting this: "+formattedDate.toString());

        return formattedDate.toString();
    }
}