package com.example.quicknotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList note_id,note_category,note_title,note_content;

    Animation translate_anim;


    public int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



    CustomAdapter(Context context,
                  ArrayList note_id,
                  ArrayList note_category,
                  ArrayList note_title,
                  ArrayList note_content){
        this.context=context;
        this.note_id=note_id;
        this.note_category=note_category;
        this.note_title=note_title;
        this.note_content=note_content;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
       View view = inflater.inflate(R.layout.my_row,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.note_id_txt.setText(String.valueOf(note_id.get(position)));
        holder.note_category_txt.setText(String.valueOf(note_category.get(position)));
        holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_content_txt.setText(String.valueOf(note_content.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(note_id.get(position)));
                intent.putExtra("category",String.valueOf(note_category.get(position)));
                intent.putExtra("title",String.valueOf(note_title.get(position)));
                intent.putExtra("content",String.valueOf(note_content.get(position)));
                context.startActivity(intent);

            }
        });
        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                setPosition(position);
                return false;
            }
        });


            }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView note_id_txt,note_category_txt,note_title_txt,note_content_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_id_txt=itemView.findViewById(R.id.note_id_text);
            note_category_txt=itemView.findViewById(R.id.note_category_text);
            note_title_txt=itemView.findViewById(R.id.note_title_text);
            note_content_txt=itemView.findViewById(R.id.note_content_text);
            //
            translate_anim= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout=itemView.findViewById(R.id.mainLayout);
            mainLayout.setAnimation(translate_anim);
            mainLayout.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(getAdapterPosition(), 121,0, "Set Reminder");//groupId, itemId, order, title
            contextMenu.add(getAdapterPosition(),122,1, "Delete");
        }



    }
}
