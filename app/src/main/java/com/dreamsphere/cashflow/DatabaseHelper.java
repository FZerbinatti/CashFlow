package com.dreamsphere.cashflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;

import com.dreamsphere.cashflow.Models.TotalCathegory;
import com.dreamsphere.cashflow.Models.Transaction;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "XYZ DatabaseHelper ";

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
    private static final String DAY = "DAY";
    private static final String MONTH = "MONTH";
    private static final String YEAR = "YEAR";
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
*/
    public void updateTransaction(Transaction transaction, String oldAmount, String oldMillis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TYPE, transaction.getType());
        cv.put(AMOUNT, transaction.getAmount());
        cv.put(CATHEGORY, transaction.getCathegory());
        cv.put(DESCRIPTION, transaction.getDescription());
        cv.put(DAY, transaction.getDay());
        cv.put(MONTH, transaction.getMonth());
        cv.put(YEAR, transaction.getYear());
        cv.put(MILLISECONDS, transaction.getMilliseconds());

        Log.d(TAG, "updateTransaction: "+transaction.getAmount() +" - " + transaction.getMilliseconds());
        db.update(TABLE_TRANSACTIONS,  cv,  AMOUNT + "= ? AND "+ MILLISECONDS +"= ?", new String []{oldAmount, oldMillis});
        db.close();
    }

    public void insertSpesa(Transaction transaction){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TYPE, transaction.getType());
        cv.put(AMOUNT, transaction.getAmount());
        cv.put(CATHEGORY, transaction.getCathegory());
        cv.put(DESCRIPTION, transaction.getDescription());
        cv.put(DAY, transaction.getDay());
        cv.put(YEAR, transaction.getYear());
        cv.put(MONTH, transaction.getMonth());
        cv.put(MILLISECONDS, transaction.getMilliseconds());


        db.insert(TABLE_TRANSACTIONS, null, cv);
        Log.d(TAG, "inserted Transaction: millis: " +transaction.getMilliseconds());
        db.close();

    }

    public Transaction getSingleTransactions(Float amount, Long millis){


        SQLiteDatabase db = this.getReadableDatabase();
        Transaction singleransactions = new Transaction();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ AMOUNT +" = ? AND "+ MILLISECONDS +"= ?" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ amount.toString(), millis.toString()});
        if (cursor.moveToFirst()) {
            do {
                singleransactions =  new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7)) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return singleransactions;

    }

    public ArrayList<Transaction> getMonthTransactions(Integer mese, Integer year){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? AND YEAR = ? ORDER BY DAY ASC" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ mese.toString(), year.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
                Log.d(TAG, "getMonthTransactions: "+cursor.getInt(0) +" - "+cursor.getFloat(1)+" - " +cursor.getString(2)+" - "+cursor.getString(3)+" - "+cursor.getInt(4)+" - "+cursor.getInt(5)+" - "+cursor.getInt(6)+" - "+cursor.getLong(7));
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }


    public ArrayList<TotalCathegory> getAmountForCathegories(Integer year, Integer type){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TotalCathegory> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+CATHEGORY +", SUM ( "+AMOUNT +") FROM "+ TABLE_TRANSACTIONS +" WHERE YEAR = ? AND TYPE = ? GROUP BY CATHEGORY ORDER BY SUM ( "+AMOUNT +") ASC" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{  year.toString(), type.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new TotalCathegory(cursor.getString(0), cursor.getFloat(1)));
                Log.d(TAG, "getMonthTransactions: "+cursor.getString(0) +" - "+cursor.getFloat(1));
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getAmountForCathegories: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        return monthTransactions;

    }





    public ArrayList<Transaction> getPeriodTransactions(Long start_millis, Long end_millis){
        Log.d(TAG, "getPeriodTransactions: start millis: "+start_millis);
        Log.d(TAG, "getPeriodTransactions: end_millis millis: "+end_millis);



        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MILLISECONDS+" BETWEEN ? AND ? ORDER BY MILLISECONDS ASC" ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ start_millis.toString() , end_millis.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactions.add( new Transaction(cursor.getInt(0), cursor.getFloat(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getLong(7))) ;
                //Log.d(TAG, "getPeriodTransactions: " + cursor.getString(2) + " / " + cursor.getInt(7));
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "getMonthTransactions: NO RECORDS FOR THIS MONTH");
        }
        cursor.close();
        db.close();
        Log.d(TAG, "getPeriodTransactions: TOTAL TRANSACTIONS : " + monthTransactions.size());
        return monthTransactions;

    }

    public Float getMonthSumOfTransactions(Integer mese, Integer anno, Integer type){


        SQLiteDatabase db = this.getReadableDatabase();
        Float monthTransactionsSum = null;
        //                                 0
        String selectQuery = "SELECT SUM ( "+AMOUNT +") FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? AND YEAR = ? AND TYPE = ?"  ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ mese.toString(), anno.toString(), type.toString()});
        if (cursor.moveToFirst()) {
            do {
                monthTransactionsSum = (cursor.getFloat(0)) ;
            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "ERROR FInding Transaction sum");

        }
        cursor.close();
        db.close();

        return monthTransactionsSum;

    }

    public Float getBalance(){

        Long currentMillis = System.currentTimeMillis();

        //prendo i valori con tempo < now cosi non vengono considerati gli abbonamenti gia fissati futuri

        SQLiteDatabase db = this.getReadableDatabase();
        Float monthTransactionsSum = 0f;
        //                                 0
        String selectQuery = "SELECT   "+ TYPE +","+AMOUNT +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MILLISECONDS +" < " + currentMillis  ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ });
        if (cursor.moveToFirst()) {
            do {
                Integer type = cursor.getInt(0);
                if (type==1){
                    Log.d(TAG, "getBalance: adding "+cursor.getFloat(1) +" new balance: "+ monthTransactionsSum);
                    monthTransactionsSum += (cursor.getFloat(1)) ;

                }else if (type ==-1){
                    Log.d(TAG, "getBalance: minuss "+cursor.getFloat(1) +" new balance: "+ monthTransactionsSum);
                    monthTransactionsSum -= (cursor.getFloat(1)) ;
                }

            } while (cursor.moveToNext());
        }else {
            Log.d(TAG, "ERROR FInding Transaction sum");

        }
        cursor.close();
        db.close();



        return monthTransactionsSum;

    }



    public ArrayList<Transaction> getMonthYearPositiveTransactions(Integer mese, Integer anno, Integer type){


        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transaction> monthTransactions = new ArrayList<>();
        //                                 0
        String selectQuery = "SELECT "+ TYPE +","+AMOUNT +","+CATHEGORY +","+DESCRIPTION +","+DAY +","+MONTH +","+ YEAR+","+ MILLISECONDS +" FROM "+ TABLE_TRANSACTIONS +" WHERE "+ MONTH +" = ? AND YEAR = ? AND TYPE = ? ORDER BY MILLISECONDS DESC"  ;
        Cursor cursor = db.rawQuery(selectQuery, new String []{ mese.toString(), anno.toString(), type.toString()});
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

    public void removeEntry(Long milliseconds) {

        Log.d(TAG, "remove entry: ");
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = MILLISECONDS+"=?";
        String[] whereArgs = new String[] { milliseconds.toString()};
        db.delete(TABLE_TRANSACTIONS, whereClause, whereArgs);

    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
