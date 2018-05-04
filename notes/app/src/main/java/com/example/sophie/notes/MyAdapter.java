package com.example.sophie.notes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends ArrayAdapter<Model> {
    Context context;
    ArrayList<Model> list;


    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Model> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        if (convertView == null)
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        else
            row = convertView;
        ImageView images = row.findViewById(R.id.icon);
        TextView myTitle = row.findViewById(R.id.text1);
        TextView myDescription = row.findViewById(R.id.text2);
        String image = list.get(position).getImgs();
        String not = list.get(position).getText();
        String ala = list.get(position).getAlarm();
        Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        Glide.with(getContext())
                .load(image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(images);
        myTitle.setText(not);
        myDescription.setText(ala);
        return row;

    }
}
