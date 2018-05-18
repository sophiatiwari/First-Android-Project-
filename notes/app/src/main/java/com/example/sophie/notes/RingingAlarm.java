package com.example.sophie.notes;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RingingAlarm extends AppCompatActivity {
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        String n=getIntent().getStringExtra("note");
        String a=getIntent().getStringExtra("alarm");
        String i=getIntent().getStringExtra("img");

        Toast.makeText(this, ""+n, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
         try{
             Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
             r = RingtoneManager.getRingtone(getApplicationContext(),notification);
             r.play();
         }
         catch(Exception e){
             Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View view) {
        if(r.isPlaying()){
            r.stop();
            Button t = (Button)view;
            t.setText("stopped");
            t.setEnabled(false);
        }
    }
}
