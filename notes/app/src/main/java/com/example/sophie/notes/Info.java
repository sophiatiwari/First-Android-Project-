package com.example.sophie.notes;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        int theme=pref.getInt("theme",0);
        if(theme==0)
            setTheme(R.style.YellowAppTheme);
        else if(theme==1)
            setTheme(R.style.GreyAppTheme);
        else if(theme==2)
            setTheme(R.style.TileAppTheme);
        else if(theme==3)
            setTheme(R.style.GreenAppTheme);
        else if(theme==4)
            setTheme(R.style.IndigoAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
}
