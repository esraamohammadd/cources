package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.interfaces.Listeners;
import com.example.cources.ui.admin.DocumentsFragment;
import com.example.cources.ui.admin.FoldersFragment;

import java.util.ArrayList;


public class Folder_Adapter extends RecyclerView.Adapter<Folder_Adapter.Folder_ViewHolder> {

    Context context;
    ArrayList<String>folders;
    Listeners listener;



    public Folder_Adapter(Context context, ArrayList<String> folders, Listeners listener) {
        this.context = context;
        this.folders = folders;
        this.listener = listener;

    }


    @NonNull
    @Override
    public Folder_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_layout,parent,false);
        return new Folder_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Folder_ViewHolder holder, int position)
    {
        holder.name.setText(folders.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // listener.onClick(holder.getAdapterPosition());
               listener.onClick(holder.getAdapterPosition());

            }
        });

        }



    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class Folder_ViewHolder extends RecyclerView.ViewHolder {


            TextView name;

        public Folder_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.folder_name);




        }


    }




}
