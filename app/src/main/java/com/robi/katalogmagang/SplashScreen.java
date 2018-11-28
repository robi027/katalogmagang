package com.robi.katalogmagang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.TimerTask;

public class SplashScreen extends Activity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sharedPreferences = getSharedPreferences("Cache", Context.MODE_PRIVATE);
        new Handler().postDelayed(new TimerTask() {
            @Override
            public void run() {

                SharedPreferences.Editor editor =sharedPreferences.edit();
                boolean firstTime=sharedPreferences.getBoolean("first", true);
                if (firstTime){
                    editor.putBoolean("first",false);
                    editor.apply();
                    Intent intent = new Intent(SplashScreen.this, AppIntro.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, DaftarTempat.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }
}
