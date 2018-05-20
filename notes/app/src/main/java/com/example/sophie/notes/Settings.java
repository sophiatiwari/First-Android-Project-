package com.example.sophie.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
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
        setContentView(R.layout.activity_settings);

        //for setting theme for app

        editor = pref.edit();

        Spinner spinner = findViewById(R.id.spin);
        Spinner spinner2 = findViewById(R.id.spin2);
        Spinner spinner3= findViewById(R.id.spin3);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.theme, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setSelection(theme);
        final int temp=theme;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("theme",position);
                editor.apply();
                if(position!=temp){
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(getIntent());
                    overridePendingTransition(0,0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.fontsize, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        int size =pref.getInt("size",0);
        spinner2.setSelection(size);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("size",position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> arrayAdapter3 = ArrayAdapter.createFromResource(this, R.array.fontstyle, android.R.layout.simple_spinner_item);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(arrayAdapter3);
        int style= pref.getInt("style",0);
        spinner3.setSelection(style);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("style",position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,NoteList.class));
        finish();
    }

}
