package com.example.budgettrackerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnExp,btnInc,btnView,btnGoal;
    TextView goalTv,currExpenseTv,balanceTv;
    DBhelper DB;
    FirebaseAuth auth;
    FirebaseUser user;

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),loginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExp = findViewById(R.id.btnExp);
        btnInc = findViewById(R.id.btnInc);
        btnView = findViewById(R.id.viewItems);
        btnGoal = findViewById(R.id.btnGoal);
        goalTv = findViewById(R.id.goal);
        currExpenseTv = findViewById(R.id.currExpense);
        balanceTv = findViewById(R.id.balance);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        DB = new DBhelper(this);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(),loginActivity.class);
            startActivity(intent);
            finish();
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();



        int goal = sharedPreferences.getInt("goalKey", 0);
        int total = DB.getTotalExpense();
        goalTv.setText("Goal: Rs. " + String.valueOf(goal));
        currExpenseTv.setText("Current Total expenditure: Rs. " + String.valueOf(total));
        balanceTv.setText("Remaining Expense To Reach Goal: Rs." + String.valueOf(goal - total));


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListViewDetails.class));
            }
        });

        btnGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the pop-up window layout
                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

                // create the pop-up window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the pop-up also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the pop-up window
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // get the input fields from the pop-up window layout
                final EditText input1 = popupView.findViewById(R.id.input1);
                final TextView heading = popupView.findViewById(R.id.heading);
                heading.setText("Set New Goal:");


                // add a click listener to the submit button in the pop-up window
                Button submitButton = popupView.findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String input1Value = input1.getText().toString();


                        editor.putInt("goalKey", Integer.parseInt(input1Value));
                        editor.apply();
                            Toast.makeText(MainActivity.this, "Goal Set" , Toast.LENGTH_SHORT).show();

                        popupWindow.dismiss();
                        int goal = sharedPreferences.getInt("goalKey", 0);
                        int total = DB.getTotalExpense();
                        goalTv.setText("Goal: Rs. " + String.valueOf(goal));
                        currExpenseTv.setText("Current Total expenditure: Rs. " + String.valueOf(total));
                        balanceTv.setText("Remaining Expense To Reach Goal: Rs." + String.valueOf(goal - total));
                    }
                });
            }
        });

        btnExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                final EditText input1 = popupView.findViewById(R.id.input1);
                final TextView heading = popupView.findViewById(R.id.heading);

                heading.setText("Add Expenditure:");


                // add a click listener to the submit button in the pop-up window
                Button submitButton = popupView.findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // retrieve the inputs from the input fields
                        String input1Value = input1.getText().toString();
//                        String input2Value = input2.getText().toString();

                        // do something with the inputs (e.g. display them in a toast)

                        Boolean checkInsert = DB.insertData(Integer.parseInt(input1Value),"expense");
                        if(checkInsert == true){
                            Toast.makeText(MainActivity.this, "Data Inserted" , Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Inserted" , Toast.LENGTH_SHORT).show();
                        }
                        // dismiss the pop-up window
                        popupWindow.dismiss();
                    }
                });
            }
        });

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the pop-up window layout
                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

                // create the pop-up window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the pop-up also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the pop-up window
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // get the input fields from the pop-up window layout
                final EditText input1 = popupView.findViewById(R.id.input1);
                final TextView heading = popupView.findViewById(R.id.heading);
//                final EditText input2 = popupView.findViewById(R.id.input2);

//                input1.setHint("Enter Amount");
                heading.setText("Add Income:");

                // add a click listener to the submit button in the pop-up window
                Button submitButton = popupView.findViewById(R.id.submit_button);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // retrieve the inputs from the input fields
                        String input1Value = input1.getText().toString();
//                        String input2Value = input2.getText().toString();

                        // do something with the inputs (e.g. display them in a toast)
//                        Toast.makeText(MainActivity.this, "Input 1: " + input1Value , Toast.LENGTH_SHORT).show();
                        Boolean checkInsert = DB.insertData(Integer.parseInt(input1Value),"income");
                        if(checkInsert == true){
                            Toast.makeText(MainActivity.this, "Data Inserted" , Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Inserted" , Toast.LENGTH_SHORT).show();
                        }
                        // dismiss the pop-up window
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
}