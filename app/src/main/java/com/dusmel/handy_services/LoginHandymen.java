package com.dusmel.handy_services;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class LoginHandymen extends AppCompatActivity {
    Button callReg;
    EditText username, pass;
    TextView titc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_handymen);


        callReg = (Button) findViewById(R.id.btnsignUp);
        username = (EditText) findViewById(R.id.HtxtUsername);
        pass = (EditText) findViewById(R.id.HtxtPass);
        titc = (TextView) findViewById(R.id.jDtoolbar);

        Typeface sweet = Typeface.createFromAsset(getAssets(), "fonts/Sweet.ttf");
        Typeface pier = Typeface.createFromAsset(getAssets(), "fonts/PierSans-Medium.otf");
        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");




        titc.setTypeface(sweet);

        callReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    public void openAdminLogin(View view) {
        startActivity(new Intent(this, AdminLogin.class));
    }

    public void loginHandy(final View view) {


        final String txtusername = username.getText().toString();
        final String txtpass = pass.getText().toString();

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



                            if (code.equals("Login")){

                                JSONArray jarray = jsonObject.getJSONArray("cursor");
                                String cursor  = jarray.toString();
                                Intent i = new Intent(getApplicationContext(), HandymanHome.class);
                                i.putExtra("cursor", cursor);
                                startActivity(i);

                            }
                            else if (code.equals("error")){
                                Snackbar snackbar = Snackbar
                                        .make(view, message, Snackbar.LENGTH_LONG);
                                snackbar.show();
                                username.setText("");
                                pass.setText("");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginHandymen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(LoginHandymen.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("request", "handylogin");
                params.put("mail", txtusername);
                params.put("pass", txtpass);


                return params;
            }
        };

        MySingleton.getInstance(LoginHandymen.this).addToRquestQue(stringRequest);

    }

    public void handyLoginback(View view) {

        finish();
    }
}
