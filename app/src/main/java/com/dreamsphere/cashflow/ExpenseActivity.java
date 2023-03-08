package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsphere.cashflow.Adapters.GridviewAdapter;
import com.dreamsphere.cashflow.Models.ArrayListModel;
import com.dreamsphere.cashflow.Models.Cathegories;
import com.dreamsphere.cashflow.Models.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ExpenseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button button_save_today, button_data, button_ricorrente;
    EditText edittext_expense_amount, edittext_expense_description;
    DatabaseHelper databaseHelper;
    private static String TAG = "XYZ Transaction page ";
    Integer extraTYpe;
    TextView id_title;
    String cathegory_selected;
    Context context;
    Calendar myCalendar;
    float oldAmount;
    long oldMillis;
    Boolean modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        databaseHelper = new DatabaseHelper(this);

        id_title = findViewById(R.id.id_title);
        button_save_today = findViewById(R.id.button_save_today);
        edittext_expense_amount = findViewById(R.id.edittext_expense_amount);
        edittext_expense_description = findViewById(R.id.edittext_expense_description);
        button_data = findViewById(R.id.button_data);
        button_ricorrente = findViewById(R.id.button_ricorrente);

        cathegory_selected ="";
        myCalendar = Calendar.getInstance();
        context = this;
        edittext_expense_amount.requestFocus();

        Intent intent = getIntent();
        extraTYpe = intent.getIntExtra("TYPE",1);
        modify = intent.getBooleanExtra("MODIFY", false);

        if (modify){
            Log.d(TAG, "onCreate: ELEMENTO IN MODIFICA");
            oldAmount = Float.parseFloat(intent.getStringExtra("AMOUNT"));
            oldMillis = Long.parseLong(intent.getStringExtra("MILLIS"));
            Transaction singleTransaction = databaseHelper.getSingleTransactions(oldAmount, oldMillis);
            cathegory_selected = singleTransaction.getCathegory();
            id_title.setText("Modifica Spesa: "+ singleTransaction.getCathegory());
            edittext_expense_description.setText(singleTransaction.getDescription());
            edittext_expense_amount.setText(String.valueOf(oldAmount));
        } else if (extraTYpe>0){
            id_title.setText("Nuova Entrata: ");
        }else {
            id_title.setText("Nuova Spesa: ");
        }
        Log.d(TAG, "onCreate: extra: "+extraTYpe);

        GridView gridView = findViewById(R.id.grid_view);
        GridviewAdapter gridviewAdapter = new GridviewAdapter(ExpenseActivity.this, new ArrayListModel().setListData());

        gridView.setAdapter(gridviewAdapter);
        gridView.setOnItemClickListener(this);

        saveTodayExpenses();

        saveAtDate();

        spesaRicorrente();

    }

    private void spesaRicorrente() {

        button_ricorrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passa i campi fatti all'activity sucessiva
                Intent intent = new Intent(ExpenseActivity.this, SpesaRicorrente_Activity.class);
                intent.putExtra("AMOUNT", edittext_expense_amount.getText().toString());
                intent.putExtra("TYPE", extraTYpe);
                intent.putExtra("DESCRIPTION", edittext_expense_description.getText().toString());
                intent.putExtra("CATHEGORY", cathegory_selected);

                startActivity(intent);

            }
        });

    }

    private void saveAtDate() {

        button_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ExpenseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth ) {

                                // all'ok del datepicker apri il dialog dell'ora e setta il textview
                                monthOfYear = monthOfYear;
                                //Log.d(TAG, "onDateSet: "+String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear) + "/" + year);
                                String selectedDate = year+"-"+String.format("%02d", monthOfYear)+"-"+String.format("%02d", dayOfMonth);
                                Log.d(TAG, "onDateSet:date1  "+selectedDate);


                                if (cathegory_selected.isEmpty()||edittext_expense_amount.getText().toString().isEmpty()){
                                    Toast.makeText(ExpenseActivity.this, "Inserisci Spesa e Seleziona Categoria", Toast.LENGTH_SHORT).show();
                                }else {

                                    Log.d(TAG, "onDateSet: "+dayOfMonth +" "+ monthOfYear +" "+year);

                                    Log.d(TAG, "onDateSet: date3 : "+ myCalendar.get(Calendar.DAY_OF_MONTH) + myCalendar.get(Calendar.MONTH)+myCalendar.get(Calendar.YEAR));


                                    if (modify){
                                        Log.d(TAG, "onDateSet: elemento in modifica inviato a db "+ edittext_expense_amount.getText().toString() + " - " );
                                        databaseHelper.updateTransaction(new Transaction(extraTYpe,
                                                Float.parseFloat(edittext_expense_amount.getText().toString()),
                                                cathegory_selected,
                                                edittext_expense_description.getText().toString(),
                                                dayOfMonth,
                                                monthOfYear,
                                                year,
                                                datetimeToMillis(selectedDate)
                                        ), String.valueOf(oldAmount),String.valueOf(oldMillis));
                                    }else {

                                        databaseHelper.insertSpesa(new Transaction(extraTYpe,
                                                Float.parseFloat(edittext_expense_amount.getText().toString()),
                                                cathegory_selected,
                                                edittext_expense_description.getText().toString(),
                                                dayOfMonth,
                                                monthOfYear,
                                                year,
                                                datetimeToMillis(selectedDate)
                                        ));

                                    }




                                    Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        },  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

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
        Log.d(TAG, "datetimeToMillis: "+matchTimeMillis);
        return matchTimeMillis+2629800000L;
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

    private void saveTodayExpenses() {

        button_save_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cathegory_selected.isEmpty()){
                    Toast.makeText(ExpenseActivity.this, "Seleziona Categoria", Toast.LENGTH_SHORT).show();
                }else {

                    if (modify){
                        databaseHelper.updateTransaction(new Transaction(extraTYpe,
                                Float.parseFloat(edittext_expense_amount.getText().toString()),
                                cathegory_selected,
                                edittext_expense_description.getText().toString(),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                Calendar.getInstance().get(Calendar.MONTH),
                                Calendar.getInstance().get(Calendar.YEAR),
                                System.currentTimeMillis()
                        ),String.valueOf(oldAmount),String.valueOf(oldMillis));
                    }else {

                        databaseHelper.insertSpesa(new Transaction(extraTYpe,
                                Float.parseFloat(edittext_expense_amount.getText().toString()),
                                cathegory_selected,
                                edittext_expense_description.getText().toString(),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                Calendar.getInstance().get(Calendar.MONTH),
                                Calendar.getInstance().get(Calendar.YEAR),
                                System.currentTimeMillis()
                        ));

                    }


                    Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }



            }


        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Cathegories model = (Cathegories) parent.getItemAtPosition(position);
        //view.setBackgroundColor(getResources().getColor(R.color.bk_red));
        //view.setBackground(getDrawable(R.drawable.buttons_green));

        Toast.makeText(this, model.getIconText(), Toast.LENGTH_SHORT).show();
        cathegory_selected = model.getIconText();
        if (extraTYpe>0){
            id_title.setText("Nuova Entrata: "+ cathegory_selected);
        }else {
            id_title.setText("Nuova Spesa: "+ cathegory_selected);
        }

    }
}