package com.dusmel.handy_services;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(Splash_screen.this)
                .withFullScreen()
                .withTargetActivity(welcome.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withBeforeLogoText("HandyMan Service")
                .withLogo(R.drawable.handylogo)
                ;

//        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
//        config.getBeforeLogoTextView().setTypeface(lobster);

        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Lemonada-Regular.ttf");
        config.getBeforeLogoTextView().setTypeface(comfortaa);


        //change text color
        config.getBeforeLogoTextView().setTextColor(Color.parseColor("#F78A39"));
        config.getBeforeLogoTextView().setTextSize(25);


        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);

    }
}
