package com.dusmel.handy_services;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText fname,lname,jobTit, desc, pfl, address,tel,mail,pass,confPass;
    Button regSubmit ;
    MaterialSpinner price , field;
    String tfname,tlname,tjobTit, tdesc, tpfl, tprice , tfield ,tadd,ttel,tmail,tstat,tpass,tconfPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regSubmit = (Button) findViewById(R.id.regSubmit);
        fname = (EditText) findViewById(R.id.regFname);
        lname = (EditText) findViewById(R.id.regLname);
        jobTit = (EditText) findViewById(R.id.regJobTit);
        desc = (EditText) findViewById(R.id.regDesc);
        pfl = (EditText) findViewById(R.id.regPflUrl);
        lname = (EditText) findViewById(R.id.regLname);
        address = (EditText) findViewById(R.id.regAdd);
        tel = (EditText) findViewById(R.id.regTel);
        mail = (EditText) findViewById(R.id.regEmail);
        pass = (EditText) findViewById(R.id.regPass);
        confPass = (EditText) findViewById(R.id.regConfPass);



         price = (MaterialSpinner) findViewById(R.id.regPrice);
         field = (MaterialSpinner) findViewById(R.id.regField);

        price.setItems("Low", "Medium", "High");
        field.setItems("Electricity", "Electronics", "knobs and locks", "Plumbery");

        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                tfname = fname.getText().toString();
                tlname = lname.getText().toString();
                tjobTit = jobTit.getText().toString();
                tdesc = desc.getText().toString();
                tpfl = pfl.getText().toString();
                tadd = address.getText().toString();
                ttel = tel.getText().toString();
                tmail = mail.getText().toString();
                tpass = pass.getText().toString();
                tconfPass = confPass.getText().toString();
                tprice = price.getText().toString();

                tfield = field.getText().toString();

                switch (tfield){
                    case "Electricity" :
                        tfield = "1";
                        break;
                    case "Electronics" :
                        tfield = "2";
                        break;
                    case "knobs and locks" :
                        tfield = "3";
                        break;
                    case "Plumbery" :
                        tfield = "4";
                        break;
                    default:
                        break;
                }


                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.URL_API,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getInt("status")!=0){

//                                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();

                                        Snackbar snackbar = Snackbar
                                                .make(v, "Register Successful", Snackbar.LENGTH_LONG)
                                                .setAction("DONE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        String names = fname.getText().toString() + " " + lname.getText().toString();
                                                        String field1 =  field.getText().toString();
                                                        String ml = mail.getText().toString();
                                                        String tl = tel.getText().toString();

                                                        String body = "Your application has been submitted \nyou are required to attach your" +
                                                                "\nCV with an  APPLICATION LETTER\n " +
                                                                "----------------------------------------\n" +
                                                                "Names: '"+names+"' \n" +
                                                                "field: '"+field1+"' \n" +
                                                                "Mail: '"+ml+"' \n" +
                                                                "Tel: '"+tl+"' ";

                                                        Intent i = new Intent(Intent.ACTION_SEND);
                                                        i.setType("message/rfc822");
                                                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"abdmuv@gmail.com"});
                                                        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                                                        i.putExtra(Intent.EXTRA_TEXT   , body);
                                                        try {
                                                            startActivity(Intent.createChooser(i, "Send mail..."));
                                                        } catch (android.content.ActivityNotFoundException ex) {
                                                            ex.printStackTrace();
                                                        }

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

                                error.printStackTrace();
                                Log.d("error registerr", error.getMessage());
                                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap();
                        params.put("request", "Register");
                        params.put("firstname", tfname);
                        params.put("lastname", tlname);
                        params.put("job_tit", tjobTit);
                        params.put("bios", tpfl);
                        params.put("description", tdesc);
                        params.put("address", tadd);
                        params.put("tel", ttel);
                        params.put("mail", tmail);
                        params.put("pass", tpass);
                        params.put("confPass", tconfPass);
                        params.put("price", tprice);
                        params.put("field_id", tfield);

                        return params;
                    }
                };

                MySingleton.getInstance(Register.this).addToRquestQue(stringRequest);

            }
        });

    }
}
