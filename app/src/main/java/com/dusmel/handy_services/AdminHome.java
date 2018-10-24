package com.dusmel.handy_services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminHome extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    List<HiringListitem> listItems;
    TextView toolbar;
    String url = "jdj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        recyclerView = (RecyclerView) findViewById(R.id.adminHomeRecyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (TextView) findViewById(R.id.AHometoolbar);

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        toolbar.setTypeface(sweet);

        listItems = new ArrayList<>();

        loadData();


    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cursor");
                            url = jsonArray.toString();
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String namo = object.getString("firstname") + " " + object.getString("lastname");
                                HiringListitem listitem = new HiringListitem(
                                        object.getInt("id"),
                                        object.getString("names"),
                                        object.getString("tel"),
                                        object.getString("mail"),
                                        object.getString("field"),
                                        object.getString("address"),
                                        object.getString("description"),
                                        namo,
                                        object.getString("longit"),
                                        object.getString("lat"),
                                        object.getString("timestamp")
                                );
                                listItems.add(listitem);
                            }
                            adapter = new HiringAdapter(listItems, AdminHome.this, url);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdminHome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            loadData();
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
                Map<String, String> params = new HashMap<>();
                params.put("request", "AdminHiring");

                return params;

            }
        };
        MySingleton.getInstance(this).addToRquestQue(stringRequest);


    }

    public void adminHomeback(View view) {
        finish();
    }
}
