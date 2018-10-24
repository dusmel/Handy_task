package com.dusmel.handy_services;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HandymanDesc extends AppCompatActivity {
    TextView names, status, add, price, success, content;
    CircleImageView pfl;
    String id;
    String field = null;
    Button hire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_desc);

        names = (TextView) findViewById(R.id.descrHandyNames);
        status = (TextView) findViewById(R.id.descrHandyStatus);
        add = (TextView) findViewById(R.id.descrHandyAdd);
        price = (TextView) findViewById(R.id.descrHandyPrice);
        success = (TextView) findViewById(R.id.descrHandySuccess);
        content = (TextView) findViewById(R.id.descrHandyContent);
        pfl = (CircleImageView) findViewById(R.id.descrHandyPfl);
        hire = (Button) findViewById(R.id.descrHandyHire);



        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        names.setTypeface(pier);
        status.setTypeface(pier);
        add.setTypeface(pier);
        price.setTypeface(pier);
        success.setTypeface(pier);
        content.setTypeface(comfortaa);
        hire.setTypeface(comfortaa);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        id = Integer.toString(bundle.getInt("ids"));

        loadData();

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cursor");
                            JSONObject object = jsonArray.getJSONObject(0);
                            String namo = object.getString("firstname") + " " + object.getString("lastname");
                            names.setText(namo);

                            int field_id = object.getInt("field_id");

                            switch (field_id){
                                case 1:
                                    field = "Electricity";
                                    break;
                                case 2:
                                    field = "Electronics";
                                    break;
                                case 3:
                                    field = "Locks";
                                    break;
                                case 4:
                                    field = "Plumbery";
                                    break;
                                default:
                                    break;
                            }

                            status.setText(field);
                            add.setText(object.getString("address"));
                            price.setText("Price:" + object.getString("price"));
                            success.setText("Job success: "+ object.getString("success"));
                            content.setText(object.getString("description"));

                            Picasso.with(getApplicationContext())
                                    .load(object.getString("bios"))
                                    .into(pfl);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("request", "handyman");
                params.put("selectArgs", id);
                return params;


            }
        };

        MySingleton.getInstance(this).addToRquestQue(stringRequest);


    }

    public void openHiring(View view) {
        Intent i = new Intent(this, Hiring.class);
        i.putExtra("id", id);
        i.putExtra("field", field);
        startActivity(i);
    }

    public void adminDescBack(View view) {
        finish();
    }
}
