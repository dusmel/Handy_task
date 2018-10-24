package com.dusmel.handy_services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HandymanHome extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    List<Joblists> listItems;
    String url = "jkv";
    String cursor;
    TextView field, jobtit, names, add, price;
    String tfield, tjobtit, tnames, tadd, tprice, id;
    CircleImageView pfl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_home);

        recyclerView = (RecyclerView) findViewById(R.id.
        handymanHomeRecyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        field = (TextView) findViewById(R.id.handyHomeField);
        jobtit = (TextView) findViewById(R.id.handyHomeJobTit);
        names = (TextView) findViewById(R.id.handyHomeNames);
        add = (TextView) findViewById(R.id.handyHomeAdd);
        price = (TextView) findViewById(R.id.handyHomePrice);

        pfl = (CircleImageView) findViewById(R.id.handyHomepfl);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cursor = bundle.getString("cursor");

        loadData();
        loadRecycler();





    }

    private void loadRecycler() {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.URL_API_2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONArray jarray = new JSONArray(response);
                            JSONObject object = jarray.getJSONObject(0);
                            JSONArray array = object.getJSONArray("cursor");
                            url = array.toString();
                            for (int i = 0; i < array.length(); i++){

                                JSONObject jsonObject = array.getJSONObject(i);
                                Joblists joblists = new Joblists(
                                        jsonObject.getString("names"),
                                        jsonObject.getString("tel"),
                                        jsonObject.getString("mail"),
                                        jsonObject.getString("address"),
                                        jsonObject.getString("description"),
                                        jsonObject.getString("timestamp")
                                );
                                listItems.add(joblists);
                            }

                            adapter = new JobListAdapter(listItems, HandymanHome.this, url);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        Toast.makeText(HandymanHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request", "handymanHome");
                params.put("id", id);
                return params;
            }
        };
        MySingleton.getInstance(HandymanHome.this).addToRquestQue(request);


    }

    private void loadData() {

        try {
            JSONArray jarray = new JSONArray(cursor);
            JSONObject object = jarray.getJSONObject(0);
            tnames = object.getString("firstname") + " " + object.getString("lastname");
            tadd = "Zone: " + object.getString("address");
            tprice = "Price: " + object.getString("price");

            switch (object.getString("field_id")){
                case "1" :
                    tfield = "Electricity";
                    break;
                case "2" :
                    tfield = "Electronics";
                    break;
                case "3" :
                    tfield = "Locks and  Knobs";
                    break;
                case "4" :
                    tfield = "Plumbery";
                    break;
            }

            tjobtit = object.getString("job_tit");

            id = object.getString("id");


            names.setText(tnames);
            field.setText(tfield);
            jobtit.setText(tjobtit);
            add.setText(tadd);
            price.setText(tprice);

            Picasso.with(getApplicationContext())
                    .load(object.getString("bios"))
                    .into(pfl);





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handyHomeback(View view) {
        finish();
    }
}