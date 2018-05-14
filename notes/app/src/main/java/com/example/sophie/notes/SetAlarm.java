package com.example.sophie.notes;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        int theme=pref.getInt("theme",0);
        if(theme==0)
            setTheme(R.style.PinkAppTheme);
        else if(theme==1)
            setTheme(R.style.RedAppTheme);
        else if(theme==2)
            setTheme(R.style.GreenAppTheme);
        else if(theme==3)
            setTheme(R.style.TealAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setalarm);
        Button btn_time = findViewById(R.id.btnset_time);

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new timepickerfragment();
                timePicker.show(getFragmentManager(), "time picker");
            }
        });
        Button btn_date = findViewById(R.id.btnset_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");

            }
        });
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.textView_time);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentdatestring = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = findViewById(R.id.textView_date);
        textView.setText(currentdatestring);
    }
}
