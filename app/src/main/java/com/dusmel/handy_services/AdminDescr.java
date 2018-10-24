package com.dusmel.handy_services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AdminDescr extends AppCompatActivity {
    TextView names, tel, mail, add, descr, handynames, field, time;
    List<HiringListitem> hiringListitems;
    int pos;
    String url, telp, email, id;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_descr);
//
        names = (TextView) findViewById(R.id.AdmninDescNames);
        tel = (TextView) findViewById(R.id.adminDescTel);
        mail = (TextView) findViewById(R.id.adminDescmail);
        add = (TextView) findViewById(R.id.AdminDescAdd);
        descr = (TextView) findViewById(R.id.AdminDescDescr);
        handynames = (TextView) findViewById(R.id.AdminDescHandyNames);
        field = (TextView) findViewById(R.id.AdminDescField);
        time = (TextView) findViewById(R.id.AdminDescTime);

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        names.setTypeface(pier);
        tel.setTypeface(pier);
        mail.setTypeface(pier);
        add.setTypeface(pier);
        descr.setTypeface(comfortaa);
        handynames.setTypeface(pier);
        field.setTypeface(pier);
        time.setTypeface(pier);



        Intent i = getIntent();
        Bundle b = i.getExtras();
        pos = b.getInt("pos");
        url = b.getString("url");
        loadData();

    }

    private void loadData()  {


        try {
            JSONArray jsonArray = new JSONArray(url);
            JSONObject object = jsonArray.getJSONObject(pos);

            String namo = object.getString("firstname") + " " + object.getString("lastname");
            names.setText("Names: "+ object.getString("names"));
            add.setText("Address: "+ object.getString("address"));
            descr.setText("Description: "+ object.getString("description"));
            handynames.setText("Handyman Names: "+ namo);
            field.setText("Field: "+ object.getString("field"));
            time.setText("Timestamp: "+ object.getString("timestamp"));
            tel.setText("Tel: "+ object.getString("tel"));
            mail.setText("Email: "+ object.getString("mail"));

            telp = object.getString("tel");
            email = object.getString("mail");
            
            id = object.getString("id");
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void adminDescrCall(View view) {


        String uri = "tel:" + telp;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    public void adminDescrMail(View view) {

        String body = "just to inform you that you have a job request ..... thank you";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , body);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void transferData(final View view) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_API_2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if (code.equals("transfer_sucess")){
                                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdminDescr.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AdminDescr.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request", "transfer");
                params.put("id", id);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRquestQue(stringRequest);
    }

    public void adminDescrback(View view) {
        finish();
    }
}
