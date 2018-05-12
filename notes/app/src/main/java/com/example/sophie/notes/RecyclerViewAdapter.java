package com.example.sophie.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

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
            }
        });
        if(mData.get(position).getAlarm().length()>0)
            holder.time.setVisibility(View.VISIBLE);
        else
            holder.time.setVisibility(View.GONE);
        holder.time.setText(mData.get(position).getAlarm());

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



