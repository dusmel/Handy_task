package com.dusmel.handy_services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HandymanField extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListHandyman> listHandymen;
    String url;
    int pos;
    TextView hhtoolb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_field);


        recyclerView = (RecyclerView) findViewById(R.id.handyRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hhtoolb = (TextView) findViewById(R.id.handymanFieldtoolbar);

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        hhtoolb.setTypeface(sweet);

        listHandymen = new ArrayList<>();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        pos = bundle.getInt("position");

        switch (pos){
            case 0:
                url = Constants.REQUEST_HANDYMENELEC;
                break;
            case 1:
                url = Constants.REQUEST_HANDYMENELECTRO;
                break;
            case 2:
                url = Constants.REQUEST_HANDYMENLOCKS;
                break;
            case 3:
                url = Constants.REQUEST_HANDYMENPLUMBER;
                break;
            default:
                break;
        }



        loadHandyMen();


    }

    private void loadHandyMen() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.d("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cursor");


                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                ListHandyman listHandyman = new ListHandyman(
                                        o.getInt("id"),
                                        o.getString("firstname"),
                                        o.getString("lastname"),
                                        o.getString("job_tit"),
                                        o.getString("address"),
                                        o.getString("price"),
                                        o.getString("success"),
                                        o.getString("bios"),
                                        o.getString("field_id"),
                                        o.getString("description"),
                                        o.getString("date_joined")

                                );
                                listHandymen.add(listHandyman);

                            }

                            adapter = new HandyAdapter(listHandymen, HandymanField.this, url);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG ).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG ).show();

                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).addToRquestQue(stringRequest);
    }

    public void handymanFieldback(View view) {
        finish();
    }
}
