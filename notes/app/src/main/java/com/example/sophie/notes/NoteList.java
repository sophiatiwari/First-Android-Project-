package com.example.sophie.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoteList extends AppCompatActivity implements deletion{
   ArrayList<String> text;
    ArrayList<String> imgs;
    ArrayList<String> alarm;

    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<String>>() {}.getType();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        int theme=pref.getInt("theme",0);
        if(theme==0)
            setTheme(R.style.PinkAppTheme);
        else if(theme==1)
            setTheme(R.style.OrangeAppTheme);
        else if(theme==2)
            setTheme(R.style.GreenAppTheme);
        else if(theme==3)
            setTheme(R.style.LimeAppTheme);
        else if(theme==4)
            setTheme(R.style.PurpleAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        //getSupportActionBar().setTitle("Your Notes");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Notes");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this,Settings.class);
                startActivity(intent);
                finish();
                break;
            case R.id.about:
                Intent intent1 = new Intent(this,Info.class);
                startActivity(intent1);

                break;
            default:
                Toast.makeText(this, "system error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);



        String json = preferences.getString("note", null);
        if(json==null)
            text=new ArrayList<>();
        else
            text= gson.fromJson(json, type);

        json = preferences.getString("image", null);
        if(json==null)
            imgs=new ArrayList<>();
        else
            imgs=gson.fromJson(json,type);

        json = preferences.getString("alarm", null);
        if(json==null)
            alarm=new ArrayList<>();
        else
            alarm=gson.fromJson(json,type);

        ArrayList<Model> listNote = new ArrayList<>();

        for(int i=0;i<text.size();i++){
            listNote.add(i,new Model(text.get(i),imgs.get(i),alarm.get(i)));
        }

       // Model model = new Model(text,imgs,alarm);

        RecyclerView myrv = findViewById(R.id.list1);
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,listNote);
        myrv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        myrv.setAdapter(myadapter);

    }



    public void add(View view) {
        Intent i = new Intent(getApplicationContext(), NoteCreate.class);
        startActivity(i);
    }

    @Override
    public void delete(int p) {
        text.remove(p);
        imgs.remove(p);
        alarm.remove(p);
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("notes", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("note", gson.toJson(text));
        editor.putString("image", gson.toJson(imgs));
        editor.putString("alarm", gson.toJson(alarm));
        editor.apply();
        onResume();
    }

}
