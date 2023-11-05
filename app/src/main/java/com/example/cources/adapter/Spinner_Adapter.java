package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.pojo.VideoModel;

import java.util.ArrayList;


public class Spinner_Adapter extends RecyclerView.Adapter<Spinner_Adapter.Spinner_ViewHolder> {

    Context context;
    ArrayList<VideoModel>videoModels;
    Listener listener;
    ArrayList<String>videosNames = new ArrayList<>();


    public Spinner_Adapter(Context context, ArrayList<VideoModel> videoModels, Spinner_Adapter
            .Listener listener) {
        this.context = context;
        this.videoModels = videoModels;
        this.listener = listener;

    }


    @NonNull
    @Override
    public Spinner_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_layout,parent,false);
        return new Spinner_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Spinner_ViewHolder holder, int position)
    {
        holder.name.setText(videoModels.get(position).getName());
        if (holder.name.isChecked())
        {

            videosNames.add( holder.name.getText().toString());
            Toast.makeText(context,holder.name.getText().toString() , Toast.LENGTH_SHORT).show();
        }


        }



    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public class Spinner_ViewHolder extends RecyclerView.ViewHolder {


            CheckBox name;

        public Spinner_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.checkBox);




        }


    }

    public interface Listener {
        void onClick(int position);
}


}
