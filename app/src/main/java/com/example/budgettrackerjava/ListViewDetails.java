package com.example.budgettrackerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListViewDetails extends AppCompatActivity {
    ListView listView;
    Button btnAll,btnExpense,btnIncome;
    MyCursorAdapter myCursorAdapter,expenseAdapter,incomeAdapter;
    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_details);
        DB = new DBhelper(this);
        listView = findViewById(R.id.listItems);
        btnAll = findViewById(R.id.viewallBtn);
        btnExpense = findViewById(R.id.expenseBtn);
        btnIncome = findViewById(R.id.incomeBtn);
        myCursorAdapter = new MyCursorAdapter(this,DB.getAllData());
        expenseAdapter = new MyCursorAdapter(this,DB.getData("expense"));
        incomeAdapter = new MyCursorAdapter(this,DB.getData("income"));
        listView.setAdapter(myCursorAdapter);
        btnAll.setBackgroundResource(R.drawable.btn_background);
        btnExpense.setBackgroundResource(R.drawable.btn_inactive);
        btnIncome.setBackgroundResource(R.drawable.btn_inactive);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(myCursorAdapter);
                btnAll.setBackgroundResource(R.drawable.btn_background);
                btnExpense.setBackgroundResource(R.drawable.btn_inactive);
                btnIncome.setBackgroundResource(R.drawable.btn_inactive);
            }
        });
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(expenseAdapter);
                btnExpense.setBackgroundResource(R.drawable.btn_background);
                btnAll.setBackgroundResource(R.drawable.btn_inactive);
                btnIncome.setBackgroundResource(R.drawable.btn_inactive);
            }
        });
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(incomeAdapter);
                btnIncome.setBackgroundResource(R.drawable.btn_background);
                btnExpense.setBackgroundResource(R.drawable.btn_inactive);
                btnAll.setBackgroundResource(R.drawable.btn_inactive);
            }
        });
    }
}