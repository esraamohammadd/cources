package com.example.cources.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.interfaces.Listener;

import java.util.ArrayList;


public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.Video_ViewHolder> {

    Context context;
    ArrayList<String>videos;
    Listener listener;



    public Video_Adapter(Context context, ArrayList<String> videos, Listener listener) {
        this.context = context;
        this.videos = videos;
        this.listener = listener;

    }


    @NonNull
    @Override
    public Video_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.video_layout,parent,false);
        return new Video_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Video_ViewHolder holder, int position)
    {
        holder.name.setText(videos.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  listener.onClick(holder.getAdapterPosition());
            }
        });

        }



    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class Video_ViewHolder extends RecyclerView.ViewHolder {


            TextView name;

        public Video_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_Videoname);




        }


    }




}
