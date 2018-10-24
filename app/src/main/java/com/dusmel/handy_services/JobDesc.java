package com.dusmel.handy_services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobDesc extends AppCompatActivity {
    TextView namo, add, tel, desc, time;
    String tnamo, tadd, ttel, tdesc, ttime, cursor;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_desc);

        namo = (TextView) findViewById(R.id.jobDescNames);
        add = (TextView) findViewById(R.id.jobDescAdd);
        tel = (TextView) findViewById(R.id.jobDescTel);
        desc = (TextView) findViewById(R.id.jobDescTxtDescr);
        time = (TextView) findViewById(R.id.jobDescTIME);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        cursor = b.getString("url");
        pos = b.getInt("pos");

        loadData();
        
    }

    private void loadData() {

        try {
            JSONArray jsonArray = new JSONArray(cursor);
            JSONObject object = jsonArray.getJSONObject(pos);

            tnamo = object.getString("names");
            ttel = object.getString("tel");
            tadd = "Address: " + object.getString("address");
            tdesc = object.getString("description");
            ttime = object.getString("timestamp");

            namo.setText(tnamo);
            tel.setText("Tel: " + ttel);
            add.setText(tadd);
            desc.setText(tdesc);
            time.setText(ttime);

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void jobDescrCall(View view) {

        String uri = "tel:" + ttel;
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

    public void jobDescback(View view) {
        finish();
    }
}
