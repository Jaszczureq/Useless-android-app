package com.memedomain.theveryfirstoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar menuBar;
    Button menuButton;
    EditText menuText1;
    EditText menuText2;
    EditText menuText3;

    long barTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        /*TODO Make back buttons work
         * Add some secret way to enter hidden activity
         * */

        menuButton = (Button) findViewById(R.id.menuButton);
        menuBar = (ProgressBar) findViewById(R.id.menuBar);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuBar.setVisibility(View.GONE);
                        changeActivity();
                    }
                }, barTime);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void changeActivity() {
        Intent intent = new Intent(this, SumUpOrder.class);

        menuText1 = (EditText) findViewById(R.id.menuText1);
        menuText2 = (EditText) findViewById(R.id.menuText2);
        menuText3 = (EditText) findViewById(R.id.menuText3);

        ArrayList<String> list = new ArrayList<>();

        list.add(menuText1.getText().toString());
        list.add(menuText2.getText().toString());
        list.add(menuText3.getText().toString());

        System.out.println("menuText1 getText:" + menuText1.getText());
        System.out.println("menuText1 getText toString: " + menuText1.getText().toString());

        intent.putStringArrayListExtra("list", list);

        startActivity(intent);
        finish();
    }
}
