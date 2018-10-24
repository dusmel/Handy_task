package com.dusmel.handy_services;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {
    EditText username, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username = (EditText) findViewById(R.id.AdminlogUsern);
        pass = (EditText) findViewById(R.id.AdminlogPass);

    }

    public void callAddBranch(View view) {

        String user = username.getText().toString();
        String passw = pass.getText().toString();

        if (user.equals("admin") && passw.equals("handyman")){
            startActivity(new Intent(this, AdminHome.class));

        }else {
            Snackbar.make(view, "Wrong password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void adminLoginback(View view) {
        finish();
    }
}
