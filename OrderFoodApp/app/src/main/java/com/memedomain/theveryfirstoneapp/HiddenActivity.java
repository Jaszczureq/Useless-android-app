package com.memedomain.theveryfirstoneapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HiddenActivity extends AppCompatActivity {

    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";
    static final String API_KEY = "KGTGAFVASIihSwJKdNo8j2MAOTFHR7Io";
    int mode;

    String inputType;

    EditText emailText;
    TextView responseView;
    Button queryButton;
    Button emailButton;
    Button twitterButton;
    Button phoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hidden_one);
        mode = 0;

        Toast.makeText(this, "You've found it", Toast.LENGTH_SHORT).show();

//        Snackbar mySnackbar = Snackbar.make(findViewById(R.layout.hidden_one), "I'm on Snackbar too", Snackbar.LENGTH_LONG).setAction("Yes indeed", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Log.i("INFO", "Jest git");

        responseView = (TextView) findViewById(R.id.responseView);
        emailText = (EditText) findViewById(R.id.emailText);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        emailButton = findViewById(R.id.emailButton);
        twitterButton = findViewById(R.id.twitterButton);
        phoneButton = findViewById(R.id.phoneButton);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "email=";
                inputType = temp;
                emailText.setHint(R.string.hiddenEmail);
                temp = null;
            }
        });
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "twitter=";
                inputType = temp;
                emailText.setHint(R.string.hiddenTwitter);
                temp = null;
            }
        });
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "phone=";
                inputType = temp;
                emailText.setHint(R.string.hiddenPhone);
                temp = null;
            }
        });

        queryButton = findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.hidden_two);
                responseView = findViewById(R.id.responseView);
                responseView.setText("Testing 1 2 3");
                new RetrieveFeedTask().execute();
            }
        });
    }

    public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            setContentView(R.layout.hidden_two);
            mode = 1;
            //MainActivity.this.wait(100);
            responseView.setText("");
        }

        @Override
        protected String doInBackground(Void... urls) {
            String email = emailText.getText().toString();

            try {
                URL url = new URL(API_URL + inputType + email + "&apiKey=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            //progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            setContentView(R.layout.hidden_two);
            responseView = findViewById(R.id.responseView);
            responseView.setText(response);
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == 1) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (mode == 0) {
            Intent intent = getParentActivityIntent();
            finish();
            startActivity(intent);
        }
    }
}
