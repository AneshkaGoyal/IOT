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

                try {
                    new FetchThingspeakTask().execute();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }

    }

    class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            t2.setText("Fetching Data from Server.Please Wait...");
        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://api.thingspeak.com/channels/687247/feeds.json?api_key=6NSETZZWR6HBO6J8&results=2");
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

        protected void onPostExecute(String response) {
            if (response == null) {
                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                //  t1.setText(response);
                JSONObject channel = (JSONObject) new JSONObject(response);
                // t1.setText(""+channel);
                JSONArray feed = channel.getJSONArray("feeds");
                JSONObject first = feed.getJSONObject(0);
                // t1.setText(""+first);
                String v1 = first.getString("field2");
                if (!v1.equals("40.00")) {
                    manager = (AlarmManager) getSystemService(ALARM_SERVICE);


                    Intent intent = new Intent(MainActivity.this, BroadCast.class);
                    PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    long time = System.currentTimeMillis();
                    //manager.set(AlarmManager.RTC_WAKEUP,cc.getTimeInMillis(),broadcast);
                    notificationCall(time + 5 * 1000, broadcast);


                    //setAlarm();


                }

                //    if(v1>=90)
                t1.setText("" + v1);

                //  else
                //    t1.setText("NO VALUES");
                try {
                    new FetchThingspeakTask().execute();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    @SuppressLint("NewApi")
    private void notificationCall(long time, PendingIntent pIntent) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP, time, pIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, time, pIntent);
        }
    }
}

