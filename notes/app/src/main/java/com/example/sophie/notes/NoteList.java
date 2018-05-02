package com.example.sophie.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NoteList extends AppCompatActivity {
    ListView list;
    String [] titles;
    String [] description;
    int [] imgs={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        Resources res = getResources();

        titles=res.getStringArray(R.array.titles);
        description=res.getStringArray(R.array.descriptions);

        list=(ListView)findViewById(R.id.list1);
        MyAdapter adapter =  new MyAdapter(this,titles,imgs,description);
        list.setAdapter(adapter);
    }
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        int[] imgs;
        String myTiltles[];
        String myDescription[];

        MyAdapter(Context c, String[] titles, int[] imgs, String[] description) {
            super(c, R.layout.row, R.id.text1, titles);
            this.context = c;
            this.imgs = imgs;
            this.myDescription = description;
            this.myTiltles = titles;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = (ImageView) row.findViewById(R.id.icon);
            TextView myTitle = (TextView) row.findViewById(R.id.text1);
            TextView myDescription = (TextView) row.findViewById(R.id.text2);
            images.setImageResource(imgs[position]);
            myTitle.setText(titles[position]);
            myDescription.setText(description[position]);
            return row;

        }

    }
    public void add(View view) {
        Intent i = new Intent(getApplicationContext(), NoteCreate.class);
        startActivity(i);
    }
}
