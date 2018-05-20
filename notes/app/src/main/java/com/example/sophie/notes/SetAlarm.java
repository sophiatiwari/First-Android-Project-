package com.example.sophie.notes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class SetAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
   Button btn_alarm,btn_date,btn_time;
   boolean time=false;
   boolean date=false;
    int y, m,d,hr,min, p;
   AlarmManager alarmManager;
   PendingIntent pendingIntent;

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
        setContentView(R.layout.activity_setalarm);
        btn_time = findViewById(R.id.btnset_time);
        btn_alarm=findViewById(R.id.btnset_alarm);
        btn_date=findViewById(R.id.btnset_date);

        p=getIntent().getIntExtra("position",-2);
        String n=getIntent().getStringExtra("note");
        String a=getIntent().getStringExtra("alarm");
        String i=getIntent().getStringExtra("img");

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent= new Intent(this,AlarmReciever.class);
        intent.putExtra("note",n);
        intent.putExtra("alarm",a);
        intent.putExtra("img",i);
            Toast.makeText(this, ""+p, Toast.LENGTH_SHORT).show();
        pendingIntent=PendingIntent.getBroadcast(this,p,intent,PendingIntent.FLAG_ONE_SHOT);
            IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
            filter.setPriority(999);// what you want to set
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new timepickerfragment();
                timePicker.show(getFragmentManager(), "time picker");
            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");

            }
        });
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time && date){



                    Calendar current = Calendar.getInstance();

                    Calendar cal = Calendar.getInstance();
                    cal.set(y,m,d,hr,min);

                    if(cal.compareTo(current) <= 0){
                        //The set Date/Time already passed
                        Toast.makeText(getApplicationContext(), "Invalid Date/Time",
                                Toast.LENGTH_LONG).show();

                    }else{
                        SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<String>>() {}.getType();

                        ArrayList<String> alarm;

                        String json = preferences.getString("alarm", null);
                        if (json == null)
                            alarm = new ArrayList<>();
                        else
                            alarm = gson.fromJson(json, type);
                        alarm.set(p, String.valueOf(cal.getTimeInMillis()));
                        json = gson.toJson(alarm);
                        editor.putString("alarm", json);
                        editor.apply();
                        Toast.makeText(SetAlarm.this, "Alarm Set", Toast.LENGTH_SHORT).show();
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
                    }
                }else
                    Toast.makeText(SetAlarm.this, "Set Both Date and Time Correctly", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.textView_time);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
        hr=hourOfDay;
        min=minute;
        time=true;

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            y=year;
            m=month;
            d=dayOfMonth;
            date=true;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,y);
        c.set(Calendar.MONTH,m);
        c.set(Calendar.DAY_OF_MONTH,d);
        String currentdatestring = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = findViewById(R.id.textView_date);
        textView.setText(currentdatestring);
    }

    public void cancel(View view) {
            if(alarmManager!=null){
                alarmManager.cancel(pendingIntent);
            }
    }
}
