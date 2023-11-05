package com.example.cources.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckVideosAdapter  extends ArrayAdapter {

    public CheckVideosAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
