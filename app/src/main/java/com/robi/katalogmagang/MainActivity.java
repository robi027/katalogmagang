package com.robi.katalogmagang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public final class MainActivity extends AppCompatActivity {

//    public static final String Url = "http://192.168.1.67:8080/katalogmagang/config/";
//    private static final String Url = "http://katalogmagang.esy.es/config/";
    private static final String Url = "http://katalogmagang.000webhostapp.com/config/";
//    public static final String Url = "http://172.30.98.139:8080/katalogmagang/config/";
//    public static final String Url = "http://192.168.1.80:80/katalogmagang/config/";

    private MainActivity(){
    }

    public static String getInstance(){
        return Url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}