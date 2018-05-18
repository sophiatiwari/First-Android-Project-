package com.example.sophie.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mcontext;
    private List<Model> mData;
    deletion del;


    public RecyclerViewAdapter(deletion del, List<Model> mData) {
        this.mData = mData;
        this.del=del;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        mcontext=parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(mcontext);
        view = inflator.inflate(R.layout.row,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        SharedPreferences pref=mcontext.getSharedPreferences("Settings",MODE_PRIVATE);
        int size = pref.getInt("size",0);
        int style=pref.getInt("style",0);

        //Bring xml array to java array
        String[] sizep= mcontext.getResources().getStringArray(R.array.fontsize);

        if(style==0)
        holder.note.setTextAppearance(mcontext,R.style.cookie);
        else  if (style==1)
            holder.note.setTextAppearance(mcontext,R.style.courge);
        else if (style==2)
            holder.note.setTextAppearance(mcontext,R.style.indie);
        else if(style==3)
            holder.note.setTextAppearance(mcontext,R.style.raves);
        else if(style==4)
            holder.note.setTextAppearance(mcontext,R.style.cinzel);


        holder.note.setTextSize(Float.parseFloat(sizep[size]));





        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mcontext);
                dialog.setContentView(R.layout.row_dialog);
                dialog.setTitle("WHAT TO DO");
                dialog.show();

                dialog.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setCancelable(true);
                        builder.setTitle("Are you sure you want to delete?");
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {

                                dialog.dismiss();
                                      del.delete(position);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                });
                dialog.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(mcontext,NoteCreate.class);
                        intent.putExtra("p",position);
                        mcontext.startActivity(intent);

                    }
                });
                dialog.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(mcontext,SetAlarm.class);
                        intent.putExtra("position",position);
                        intent.putExtra("note",mData.get(position).getText());
                        intent.putExtra("img",mData.get(position).getImgs());
                        intent.putExtra("alarm",mData.get(position).getAlarm());
                        mcontext.startActivity(intent);
                    }
                });
            }
        });
        if(mData.get(position).getAlarm().length()>0) {
            holder.time.setVisibility(View.VISIBLE);
            long ts=Long.parseLong(mData.get(position).getAlarm());
            String d= new SimpleDateFormat("hh:mm a, dd MMM yyyy").format(new Date(ts));

            holder.time.setText(d);
        }
        else
            holder.time.setVisibility(View.GONE);



        Toast.makeText(mcontext, ""+mData.get(position).getAlarm(), Toast.LENGTH_SHORT).show();

        String image = mData.get(position).getImgs();
        //Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
        if (image.equals("") || image.equals(null))
            holder.image.setVisibility(View.GONE);
        else {
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(mcontext)
                    .load(image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .thumbnail(0.1f)
                    .dontAnimate()  //specially for MI phones
                    .into(holder.image);
        }
        if(mData.get(position).getText().length()>0)
            holder.note.setVisibility(View.VISIBLE);
        else
            holder.note.setVisibility(View.GONE);

        holder.note.setText(mData.get(position).getText());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.row;
    }
}



