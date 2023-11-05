package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.pojo.DocumentModel;
import com.example.cources.pojo.VideoModel;

import java.util.ArrayList;


public class Document_Adapter extends RecyclerView.Adapter<Document_Adapter.Document_ViewHolder> {

    Context context;
    ArrayList<DocumentModel>documentModels;
    Listener listener;



    public Document_Adapter(Context context, ArrayList<DocumentModel> documentModels, Document_Adapter
            .Listener listener) {
        this.context = context;
        this.documentModels = documentModels;
        this.listener = listener;

    }


    @NonNull
    @Override
    public Document_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.document_layout,parent,false);
        return new Document_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Document_ViewHolder holder, int position)
    {
        holder.name.setText(documentModels.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  listener.onClick(holder.getAdapterPosition());
            }
        });

        }



    @Override
    public int getItemCount() {
        return documentModels.size();
    }

    public class Document_ViewHolder extends RecyclerView.ViewHolder {


            TextView name;

        public Document_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_documentname);




        }


    }

    public interface Listener {
        void onClick(int possition);
}


}
