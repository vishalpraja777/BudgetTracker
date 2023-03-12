package com.example.budgettrackerjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table BudgetDetails(_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER, amount INTEGER, type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists BudgetDetails");
    }
    public Boolean insertData(int amount, String type){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int time = (int)(System.currentTimeMillis()/1000L);
        contentValues.put("time",time);
        contentValues.put("amount",amount);
        contentValues.put("type",type);
        long result = DB.insert("BudgetDetails", null, contentValues);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getData(String type){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from BudgetDetails where type = ? order by time desc", new String[]{type});
        return cursor;
    }
    public Cursor getAllData(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from BudgetDetails order by time desc",null);
        return cursor;
    }
    public int getTotalExpense(){
        SQLiteDatabase DB = this.getReadableDatabase();
        String type = "expense";
        Cursor cursor = DB.rawQuery("select amount from BudgetDetails where type = ?", new String[]{type});
        int total = 0;
        while(cursor.moveToNext()){
            int amt = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
            total += amt;
        }
        return total;
    }
}
