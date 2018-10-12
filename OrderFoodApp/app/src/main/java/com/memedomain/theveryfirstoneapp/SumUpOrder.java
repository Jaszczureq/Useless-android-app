package com.memedomain.theveryfirstoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SumUpOrder extends AppCompatActivity {

    TextView sumUpText;
    Button hidden;
    int clicks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_up);
        clicks = 0;

        Bundle b = getIntent().getExtras();
        ArrayList<String> list = new ArrayList<>();
        list = b.getStringArrayList("list");
        sumUpText = (TextView) findViewById(R.id.sumupText);

        final String text = ("Nazwa zamównienia: " + list.get(0) +
                "\nKategoria: " + list.get(1) +
                "\nData Zamówienia: " + list.get(2));

        Log.i("INFO", text);
        System.out.println(text);

        sumUpText.setText(text);

        hidden = findViewById(R.id.hiddenButton);

        hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks < 2) {
                    clicks++;
                    String tempText = text + "\nLiczba klikniec: " + clicks;
                    //sumUpText.setText(tempText);
                    tempText = null;
                } else {
                    changeActivity();
                }
            }
        });
    }

    public void changeActivity() {
        Intent intent = new Intent(this, HiddenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getParentActivityIntent();
        finish();
        startActivity(intent);
    }
}
