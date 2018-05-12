package com.example.sophie.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
    ListView list;
   //String [] titles;
   //String [] description;
   //int [] imgs={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
   ArrayList<String> text;
    ArrayList<String> imgs;
    ArrayList<String> alarm;

    Gson gson = new Gson();
    Type type = new TypeToken<ArrayList<String>>() {}.getType();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        //getSupportActionBar().setTitle("Your Notes");




    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
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
