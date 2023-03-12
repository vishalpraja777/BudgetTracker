package com.example.budgettrackerjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("DebugTab","bindView: Printed");
        TextView amountTv = view.findViewById(R.id.amount);
        TextView dateTv = view.findViewById(R.id.date);
        TextView desTv = view.findViewById(R.id.desTv);
        LinearLayout linearLayout = view.findViewById(R.id.linerLayout2);
        ImageView img = view.findViewById(R.id.imgView);


        int time = cursor.getInt(cursor.getColumnIndexOrThrow("time"));
        int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));


        String date = new java.text.SimpleDateFormat("dd/MMM/yyyy HH:mm").format(new java.util.Date (time*1000L));

        dateTv.setText(String.valueOf(time));
        dateTv.setText(date);
        amountTv.setText(String.valueOf(amount));

        if(type.equals("expense")){
            img.setImageResource(R.drawable.income);
            desTv.setText(type.toUpperCase(Locale.ROOT));
        }
        else{
            img.setImageResource(R.drawable.profits);
            desTv.setText(type.toUpperCase(Locale.ROOT));
        }

    }

}

