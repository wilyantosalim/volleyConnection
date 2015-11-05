package com.example.multunus.testapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList forecast = new ArrayList<String>();
        forecast.add("Today - 21");
        forecast.add("Tomorrow - 22");
        forecast.add("Thursday - 23");
        forecast.add("Friday - 24");
        forecast.add("Saturday - 25");
        forecast.add("Sunday - 26");

        final ListView listView = (ListView) this.findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, R.id.listViewTextItem, forecast);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=celsius&cnt=7";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            ArrayList temps = new ArrayList<String>();
                            JSONObject item = (JSONObject) list.get(0);
                            String temp = item.getJSONObject("temp").getString("day");
                            String timeString = item.getString("dt");

                            Time time = new Time();
                            time.set(Long.parseLong(timeString) * 1000);
                            temps.add(time.monthDay + " -- " + temp);
                            arrayAdapter.addAll(temps);
                            listView.setAdapter(arrayAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(getApplicationContext(), "Hello Sreenath",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
