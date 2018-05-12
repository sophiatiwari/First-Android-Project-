package com.example.sophie.notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView time;
    ImageView image;
    TextView note;
    CardView layout;


    public MyViewHolder(View itemView) {
        super(itemView);

        layout = itemView.findViewById(R.id.row_layout);


        time = itemView.findViewById(R.id.text2);
        image = itemView.findViewById(R.id.icon);
        note=itemView.findViewById(R.id.text1);
    }
}