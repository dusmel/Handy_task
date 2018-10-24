package com.dusmel.handy_services;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hiring extends AppCompatActivity {

    EditText names, tel, mail, add, descr;
    String id, field;
    TextView hiringtit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring);

        names = (EditText) findViewById(R.id.hiringNames);
        tel = (EditText) findViewById(R.id.hiringTel);
        mail = (EditText) findViewById(R.id.hiringMail);
        add = (EditText) findViewById(R.id.hiringAdd);
        descr = (EditText) findViewById(R.id.hiringDescription);
        hiringtit = (TextView) findViewById(R.id.hiringtxttithead);

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        hiringtit.setTypeface(pier);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        id = bundle.getString("id");
        field = bundle.getString("field");
    }

    public void hiringSubmit(final View view) {


        final String txtnames, txttel, txtmail, txtadd, txtdescr;

        txtnames = names.getText().toString();
        txttel = tel.getText().toString();
        txtmail = mail.getText().toString();
        txtadd = add.getText().toString();
        txtdescr = descr.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status")!=0){

                                Snackbar snackbar = Snackbar
                                        .make(view, "Request submited", Snackbar.LENGTH_LONG)
                                        .setAction("DONE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent i = new Intent(getApplicationContext(), welcome.class);
                                                startActivity(i);

                                            }
                                        });

                                snackbar.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Hiring.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request", "hire");
                params.put("names", txtnames);
                params.put("tel", txttel);
                params.put("mail", txtmail);
                params.put("address", txtadd);
                params.put("description", txtdescr);
                params.put("field", field);
                params.put("handyman_id", id);


                return params;

            }
        };
        MySingleton.getInstance(this).addToRquestQue(stringRequest);

    }
}
