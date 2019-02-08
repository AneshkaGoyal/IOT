package com.example.aneshkagoyal.iot;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AppComponentFactory;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = "687247";
    private static final String THINGSPEAK_API_KEY = "6NSETZZWR6HBO6J8"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "api_key";
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD1 = "Temperature";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/channels/687247/status.json?api_key=6NSETZZWR6HBO6J8";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/fields/1.json?";
    TextView t1, t2;
    Button b1;
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.textView2);
        t2 = (TextView) findViewById(R.id.textView3);
        b1 = (Button) findViewById(R.id.button);
        t2.setText("");
        startService(new Intent(this, YourService.class));




    }


    }

    /*EditText in;
    AlarmManager manager;
    Calendar cc;
    Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in = findViewById(R.id.input);
        sub = findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if(!in.getText().toString().equals("1234")){
             manager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Intent intent = new Intent(MainActivity.this,BroadCast.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        long time = System.currentTimeMillis();
        //manager.set(AlarmManager.RTC_WAKEUP,cc.getTimeInMillis(),broadcast);
        notificationCall(time+5*1000,broadcast);



        //setAlarm();


        }
            }
        });
    }*/



