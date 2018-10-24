package com.dusmel.handy_services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class welcome extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    List<ListItem> listItems;

    TextView tit, titClient, titHandy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        tit = (TextView) findViewById(R.id.handyTitHeader);
        titClient = (TextView) findViewById(R.id.homeTitclient);
        titHandy = (TextView) findViewById(R.id.homeTitHandymen);

        listItems = new ArrayList<>();

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        tit.setTypeface(sweet);
        titClient.setTypeface(pier);
        titHandy.setTypeface(comfortaa);

        handleSSLHandshake();

        loadingfield();


        }







    public void loadingfield(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.REQUEST_FIELD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray jsonArray = jsonobject.getJSONArray("cursor");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                ListItem listItem = new ListItem(
                                        o.getString("image"),
                                        o.getString("title"),
                                        o.getString("logo")

                                );

//
                                listItems.add(listItem);
                            }
                            adapter = new MyAdapter(listItems, welcome.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                        Toast.makeText(welcome.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                        error.printStackTrace();
                        loadingfield();



                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openHandyLogin(View view) {

        startActivity(new Intent(this, LoginHandymen.class));
    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
