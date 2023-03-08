package com.dreamsphere.cashflow;

import static android.webkit.WebSettings.PluginState.ON;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsphere.cashflow.Models.Transaction;

import java.util.Calendar;

public class SpesaRicorrente_Activity extends AppCompatActivity {

    EditText edittext_ricorrente_expense_amount, edittext_ricorrente_expense_description;
    TextView textview_categoria_spesa;
    NumberPicker numberPicker;
    Button button_save_ricorrente;
    DatabaseHelper databaseHelper;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spesa_ricorrente);
        databaseHelper = new DatabaseHelper(this);

        edittext_ricorrente_expense_amount = findViewById(R.id.edittext_ricorrente_expense_amount);
        edittext_ricorrente_expense_description = findViewById(R.id.edittext_ricorrente_expense_description);
        textview_categoria_spesa = findViewById(R.id.textview_categoria_spesa);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(31);
        button_save_ricorrente = findViewById(R.id.button_save_ricorrente);
        myCalendar = Calendar.getInstance();


        Intent intent = getIntent();
        Float amount = Float.parseFloat(intent.getStringExtra("AMOUNT"));
        String description = intent.getStringExtra("DESCRIPTION");
        String cathegory = intent.getStringExtra("CATHEGORY");
        Integer type = intent.getIntExtra("TYPE", 0);

        edittext_ricorrente_expense_amount.setText(amount.toString());
        if (!description.isEmpty()){
            edittext_ricorrente_expense_description.setText(description);
        }
        textview_categoria_spesa.setText(cathegory);


        button_save_ricorrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer currentMonth = myCalendar.get(Calendar.MONTH);
                Integer currentYear = myCalendar.get(Calendar.YEAR);
                Long currentMillis= System.currentTimeMillis();

                //String desc="";
                if (edittext_ricorrente_expense_description.getText().toString().isEmpty()){
                    Toast.makeText(SpesaRicorrente_Activity.this, "Descrizione Spesa Obbligatoria!", Toast.LENGTH_SHORT).show();
                }else {

                    for(int i=0; i<3; i++){
                        databaseHelper.insertSpesa(new Transaction(
                                type,
                                Float.parseFloat(edittext_ricorrente_expense_amount.getText().toString()),
                                cathegory,
                                edittext_ricorrente_expense_description.getText().toString(),
                                numberPicker.getValue(),
                                currentMonth,
                                currentYear,
                                currentMillis

                        ));

                        if (currentMonth==11){
                            currentMonth=0;
                            currentYear++;

                        }else {
                            currentMonth++;

                        }

                        currentMillis+=2629800000L;


                    }
                    Toast.makeText(SpesaRicorrente_Activity.this, "Ricorrente Aggiunta!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SpesaRicorrente_Activity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }




            }
        });





    }
}