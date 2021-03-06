package com.example.sophie.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wooplr.spotlight.SpotlightView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

public class NoteCreate extends AppCompatActivity {

    FloatingActionButton fab_plus,fab_image,fab_alarm,fab_close,fab_save;
    Animation open,close,clockwise,anticlockwise;
    TextView textcancel,textsave,textimage,textalarm;
    LinearLayout l;
    boolean isopen=false;

    private static final int RC_PHOTO_PICKER = 1; //request code for photo
    String p="";
    int pos=-1;

    int zz;


    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<String>>() {}.getType();

    ArrayList<String> text;
    ArrayList<String> imgs;
    ArrayList<String> alarm;

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
        setContentView(R.layout.activity_notecreate);
        l = findViewById(R.id.fabutton);
        l.post(new Runnable() {
            @Override
            public void run() {
                new SpotlightView.Builder(NoteCreate.this)
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .headingTvColor(Color.parseColor("#00bcd4"))
                        .headingTvSize(32)
                        .headingTvText("Your Note")
                        .subHeadingTvColor(Color.parseColor("#e91e63"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText("Type in your note and tap for more")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(l)
                        .lineAnimDuration(400)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(true)
                        .dismissOnBackPress(true)
                        .enableDismissAfterShown(true)
                        .usageId("2") //UNIQUE ID
                        .show();


            }
        });

        fab_plus=findViewById(R.id.fab);
        fab_image=findViewById(R.id.img);
        fab_alarm=findViewById(R.id.time);
        fab_close=findViewById(R.id.addclose);
        fab_save=findViewById(R.id.addsave);
        textimage=findViewById(R.id.imgnote);
        textcancel=findViewById(R.id.cnote);
        textsave=findViewById(R.id.savenote);
        textalarm=findViewById(R.id.alarmnote);



        open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.open);
        close= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.close);
        clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        anticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anti_rotate);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopen) {
                    fab_alarm.startAnimation(close);
                    fab_image.startAnimation(close);
                    fab_save.startAnimation(close);
                    fab_close.startAnimation(close);//fab_plus.startAnimation(anticlockwise);
                    textcancel.startAnimation(close);
                    textsave.startAnimation(close);
                    textimage.startAnimation(close);
                    textalarm.startAnimation(close);
                    fab_plus.animate().rotation(0).start();
                    fab_image.setClickable(false);
                    fab_alarm.setClickable(false);
                    fab_save.setClickable(false);
                    fab_close.setClickable(false);
                    textcancel.setClickable(false);
                    textsave.setClickable(false);
                    textimage.setClickable(false);
                    textalarm.setClickable(false);
                    isopen=false;

                } else {
                    fab_alarm.startAnimation(open);
                    fab_image.startAnimation(open);
                    fab_save.startAnimation(open);
                    fab_close.startAnimation(open);//fab_plus.startAnimation(clockwise);
                    textcancel.startAnimation(open);
                    textsave.startAnimation(open);
                    textimage.startAnimation(open);
                    textalarm.startAnimation(open);
                    fab_plus.animate().rotation(45).start();
                    fab_image.setClickable(true);
                    fab_alarm.setClickable(true);
                    fab_save.setClickable(true);
                    textcancel.setClickable(true);
                    textsave.setClickable(true);
                    textimage.setClickable(true);
                    textalarm.setClickable(true);
                    isopen=true;

                }
            }
        });


        pos=getIntent().getIntExtra("p",-1);
        zz=pos;
        if(pos>-1){
            SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            text= gson.fromJson(preferences.getString("note", null), type);
            imgs=gson.fromJson(preferences.getString("image", null), type);
            EditText editText =findViewById(R.id.editText);
            ImageView imageView = findViewById(R.id.pic);
            editText.setText(text.get(pos));
            if(!imgs.get(pos).equals("")) {
                p=imgs.get(pos);
                Glide.with(NoteCreate.this)
                        .load(p)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);
            }
        }
    }

    public void close(View view) {
        finish();
    }

    public void save(View view) {
        update();
        finish();
           //back to NoteList
    }

    private void update() {
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        if(pos>-1){
            EditText editText =findViewById(R.id.editText);
            text.set(pos,editText.getText().toString().trim());
            imgs.set(pos,p);
            editor.putString("note", gson.toJson(text));
            editor.putString("image", gson.toJson(imgs));
            alarm = gson.fromJson(preferences.getString("alarm", null), type);
            editor.apply();
        }
        else {
            String json = preferences.getString("note", null);
            if (json == null)
                text = new ArrayList<>();
            else
                text = gson.fromJson(json, type);

            json = preferences.getString("image", null);
            if (json == null)
                imgs = new ArrayList<>();
            else
                imgs = gson.fromJson(json, type);

            json = preferences.getString("alarm", null);
            if (json == null)
                alarm = new ArrayList<>();
            else
                alarm = gson.fromJson(json, type);


            EditText editText = findViewById(R.id.editText);
            String s = editText.getText().toString().trim();
            if (s.equals("") && p.equals(""))
                Toast.makeText(this, "No text and Image", Toast.LENGTH_SHORT).show();
            else {
                text.add(s);
                imgs.add(p);
                alarm.add("");
                zz=text.size()-1;
                json = gson.toJson(text);
                editor.putString("note", json);
                json = gson.toJson(imgs);
                editor.putString("image", json);
                json = gson.toJson(alarm);
                editor.putString("alarm", json);
                editor.apply();
            }
        }
    }

    public void setalarm(View view) {
        EditText editText = findViewById(R.id.editText);
        String s = editText.getText().toString().trim();
        if (s.equals("") && p.equals(""))
            Toast.makeText(this, "No text and Image", Toast.LENGTH_SHORT).show();
        else {
            update();
            Intent i = new Intent(getApplicationContext(), SetAlarm.class);
            i.putExtra("note",text.get(zz));
            i.putExtra("img",imgs.get(zz));
            i.putExtra("alarm",alarm.get(zz));
            i.putExtra("position", zz);
            startActivity(i);
            finish();
        }
    }

    public void addimage(View view) {
        Intent photo =new Intent(Intent.ACTION_GET_CONTENT);
        photo.setType("image/jpeg");
        photo.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(photo,"Complete action using"),RC_PHOTO_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.pic);

        if(requestCode==RC_PHOTO_PICKER && resultCode==RESULT_OK)
        {
            Uri i=data.getData();
            p=i.toString();
            Glide.with(NoteCreate.this)
                    .load(p)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }



    }
}
