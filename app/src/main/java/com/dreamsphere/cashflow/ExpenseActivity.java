package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsphere.cashflow.Adapters.GridviewAdapter;
import com.dreamsphere.cashflow.Models.ArrayListModel;
import com.dreamsphere.cashflow.Models.Cathegories;
import com.dreamsphere.cashflow.Models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button button_save_today;
    EditText edittext_expense_amount, edittext_expense_description;
    DatabaseHelper databaseHelper;
    private static String TAG = "Transaction page ";
    Integer extraTYpe;
    TextView id_title;
    String cathegory_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        databaseHelper = new DatabaseHelper(this);

        id_title = findViewById(R.id.id_title);
        button_save_today = findViewById(R.id.button_save_today);
        edittext_expense_amount = findViewById(R.id.edittext_expense_amount);
        edittext_expense_description = findViewById(R.id.edittext_expense_description);
        cathegory_selected ="";
        edittext_expense_amount.requestFocus();

        Intent intent = getIntent();
        extraTYpe = intent.getIntExtra("TYPE",1);
        if (extraTYpe>0){
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

    }

    private void saveTodayExpenses() {

        button_save_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cathegory_selected.isEmpty()){
                    Toast.makeText(ExpenseActivity.this, "Seleziona Categoria", Toast.LENGTH_SHORT).show();
                }else {



                    databaseHelper.insertSpesa(new Transaction(extraTYpe,
                            Float.parseFloat(edittext_expense_amount.getText().toString()),
                            cathegory_selected,
                            edittext_expense_description.getText().toString(),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MILLISECOND)
                            ));

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