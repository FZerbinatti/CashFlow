package com.dreamsphere.cashflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dreamsphere.cashflow.Models.Transaction;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHelper ";

    private static final String DB_NAME = "LocalDB";
    private static final int DB_VERSION = 1;

    //Tabelle
    static final String TABLE_TRANSACTIONS = "TABLE_TRANSACTIONS";
    static final String TABLE_CATHEGORIES = "TABLE_CATHEGORIES";

    //Tabella Transactions
    private static final String ID = "ID";
    private static final String TYPE = "TYPE";
    private static final String AMOUNT = "AMOUNT";
    private static final String CATHEGORY = "CATHEGORY";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String YEAR = "YEAR";
    private static final String MONTH = "MONTH";
    private static final String DAY = "DAY";
    private static final String MILLISECONDS = "MILLISECONDS";


    //Tabella Cathegories
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String USAGES = "USAGES";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }


    private void createTables(SQLiteDatabase db){
        //creating tables

        String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS +
                "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE + " INTEGER ,  "
                + AMOUNT + " INTEGER ,  "
                + CATHEGORY + " TEXT ,  "
                + DESCRIPTION + " TEXT ,  "
                + DAY + " INTEGER ,  "
                + MONTH + " INTEGER ,  "
                + YEAR + " INTEGER ,  "
                + MILLISECONDS + " LONG  );";

        String CREATE_TABLE_CATHEGORIES = "CREATE TABLE " + TABLE_CATHEGORIES +
                "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT ,  "
                + IMAGE + " TEXT ,  "
                + USAGES + " INTEGER  );";

        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_CATHEGORIES);


        //insertIcons();

    }

/*    public void insertIcons(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, "Animals");
        cv.put(USAGES, 0  );
        db.insert(TABLE_CATHEGORIES, null, cv);

        db.close();

    }

    public void updateIconUsages(String cathegory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Integer usages =0;
        String selectQuery = "SELECT "+ USAGES +" FROM "+ TABLE_CATHEGORIES +" WHERE NAME = ? " ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{cathegory});
        if (cursor.moveToFirst()) {
            do {
                usages = ( cursor.getInt(0)) ;
                //Log.d(TAG, "getUserMorningReminderRegions: "+regions);
            } while (cursor.moveToNext());
        }


        cv.put(USAGES, usages+1);
        db.update(TABLE_CATHEGORIES,  cv, "NAME = ?", new String []{cathegory});
        db.close();
    }*/

    public void insertSpesa(Transaction transaction){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TYPE, transaction.getType());
        cv.put(AMOUNT, transaction.getAmount());
        cv.put(CATHEGORY, transaction.getCathegory());
        cv.put(DESCRIPTION, transaction.getDescription());
        cv.put(YEAR, transaction.getYear());
        cv.put(MONTH, transaction.getMonth());
        cv.put(DAY, transaction.getDay());
        cv.put(MILLISECONDS, transaction.getMilliseconds());


        db.insert(TABLE_TRANSACTIONS, null, cv);
        Log.d(TAG, "inserted Transaction");
        db.close();

    }

    public ArrayList<Transaction> getFullMonthTransactions(){

        Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ?" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ currentMonth.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }

    public ArrayList<Transaction> getMonthTransactions(Integer mese){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? ORDER BY MILLISECONDS DESC" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ mese.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }

    public ArrayList<Transaction> getMonthIncomes(Integer mese){


        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? AND TYPE = 1 ORDER BY MILLISECONDS DESC"  ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ mese.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }

    public ArrayList<Transaction> getMonthExpenses(Integer month){


        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? AND TYPE = -1 ORDER BY MILLISECONDS DESC"  ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ month.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
