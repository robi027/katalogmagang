package com.robi.katalogmagang;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public IntroManager(Context context){
        this.context=context;
        pref=context.getSharedPreferences("first",0);
        editor = pref.edit();
        //Mendeklarasikan data pref 0 atau aplikasi belum dibuka
    }

    public void setFirst(boolean isFirst){
        editor.putBoolean("check", isFirst);
        editor.commit();
    }

    public boolean Check(){
        return pref.getBoolean("check", true);
        //return data pref jika benar aplikasi belum dibuka
    }
}
