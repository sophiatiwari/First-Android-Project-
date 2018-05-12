package com.example.sophie.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    boolean isopen=false;

    private static final int RC_PHOTO_PICKER = 1; //request code for photo
    String p="";
    int pos=-1;



    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<String>>() {}.getType();

    ArrayList<String> text;
    ArrayList<String> imgs;
    ArrayList<String> alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notecreate);
        fab_plus=findViewById(R.id.fab);
        fab_image=findViewById(R.id.img);
        fab_alarm=findViewById(R.id.time);
        fab_close=findViewById(R.id.addclose);
        fab_save=findViewById(R.id.addsave);

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
                    fab_plus.animate().rotation(0).start();
                    fab_image.setClickable(false);
                    fab_alarm.setClickable(false);
                    fab_save.setClickable(false);
                    fab_close.setClickable(false);
                    isopen=false;

                } else {
                    fab_alarm.startAnimation(open);
                    fab_image.startAnimation(open);
                    fab_save.startAnimation(open);
                    fab_close.startAnimation(open);
                    //fab_plus.startAnimation(clockwise);
                    fab_plus.animate().rotation(45).start();
                    fab_image.setClickable(true);
                    fab_alarm.setClickable(true);
                    fab_save.setClickable(true);
                    fab_close.setClickable(true);
                    isopen=true;

                }
            }
        });


        pos=getIntent().getIntExtra("p",-1);
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
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        if(pos>-1){
            EditText editText =findViewById(R.id.editText);
            text.set(pos,editText.getText().toString().trim());
            imgs.set(pos,p);
            editor.putString("note", gson.toJson(text));
            editor.putString("image", gson.toJson(imgs));
            editor.apply();
            finish();
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
                json = gson.toJson(text);
                editor.putString("note", json);
                json = gson.toJson(imgs);
                editor.putString("image", json);
                json = gson.toJson(alarm);
                editor.putString("alarm", json);
                editor.apply();
                finish();
            }
        }
           //back to NoteList
    }

    public void setalarm(View view) {
        Intent i = new Intent(getApplicationContext(), SetAlarm.class);
        startActivity(i);
        finish();
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
        Uri i=data.getData();
        p=i.toString();
        if(requestCode==RC_PHOTO_PICKER && resultCode==RESULT_OK)
        {
            Glide.with(NoteCreate.this)
                    .load(p)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }



    }
}
