package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamsphere.cashflow.Adapters.Transactions_Adapter;
import com.dreamsphere.cashflow.Models.Transaction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity ";
    ImageView img_test;
    Context context;
    DatabaseHelper mDatabaseHelper;
    ImageButton button_add_expense, button_add_income, button_month_after, button_month_before, imageButtonCalendar;
    TextView id_month, tv_expenses, tv_incomes, tv_balance;
    ArrayList<Transaction> currentMonthTransactions;
    PieChart pieChart;
    RecyclerView recycler_transactions;
    Transactions_Adapter transactions_adapter;
    Integer currentMonth;


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


        tv_expenses = findViewById(R.id.tv_expenses);
        tv_incomes = findViewById(R.id.tv_incomes);
        tv_balance = findViewById(R.id.tv_balance);

        button_month_after = findViewById(R.id.button_month_after);
        button_month_before = findViewById(R.id.button_month_before);

        currentMonth = Calendar.getInstance().get(Calendar.MONTH);

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
                intent.putExtra("TYPE", -1);
                startActivity(intent);
            }
        });


        button_add_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("TYPE", 1);
                startActivity(intent);
            }
        });

        tv_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
            }
        });

        tv_incomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonthTransactions = mDatabaseHelper.getMonthIncomes(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
            }
        });

        tv_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonthTransactions = mDatabaseHelper.getMonthExpenses(currentMonth);
                loadListView(currentMonthTransactions);
                loadGraph(currentMonthTransactions);
            }
        });

        buttonCalendar();


//decimo minuto mane

        button_month_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth++;
                currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);

                id_month.setText(getMonthName(currentMonth));
            }
        });

        button_month_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth--;
                currentMonthTransactions = mDatabaseHelper.getMonthTransactions(currentMonth);
                loadListView(currentMonthTransactions);
                id_month.setText(getMonthName(currentMonth));
            }
        });




    }

    private void buttonCalendar() {

        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                DatePicker picker = new DatePicker(context);
                picker.setCalendarViewShown(false);

                builder.setTitle("Create Year");
                builder.setView(picker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", null);

                builder.show();

            }
        });

    }

    private void loadGraph(ArrayList<Transaction> currentMonthTransactions) {

        Log.d(TAG, "onCreate: "+currentMonthTransactions.size());
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
        for (int c : ColorTemplate.PASTEL_COLORS)
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


    }

    public String getMonthName(Integer month){

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        cal.set(Calendar.MONTH,month);
        String month_name = month_date.format(cal.getTime()) + Calendar.getInstance().get(Calendar.YEAR);


        return month_name;

    }
}