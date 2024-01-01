package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.pojo.StudentChatsModel;
import com.example.cources.pojo.StudentModel;

import java.util.ArrayList;


public class PrivateChat_Adapter extends RecyclerView.Adapter<PrivateChat_Adapter.PrivateChat_ViewHolder> {

    Context context;
    ArrayList<StudentChatsModel>studentChatsModels;
    OnItemListener listener;





    public PrivateChat_Adapter(Context context, ArrayList<StudentChatsModel> studentChatsModels,OnItemListener listener) {
        this.context = context;
        this.studentChatsModels = studentChatsModels;
        this.listener = listener;


    }


    @NonNull
    @Override
    public PrivateChat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_layout,parent,false);
        return new PrivateChat_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateChat_ViewHolder holder, int position)
    {

     StudentChatsModel studentChatsModel = studentChatsModels.get(holder.getAdapterPosition());

       holder.name.setText(studentChatsModel.getName());
        holder.phone.setText(studentChatsModel.getPhone());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 listener.onItemClick(holder.getAdapterPosition());
             }
         });
    }



    @Override
    public int getItemCount() {
        return studentChatsModels.size();
    }

    public class PrivateChat_ViewHolder extends RecyclerView.ViewHolder {


            TextView name, phone;

        public PrivateChat_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_std_name);
            phone = itemView.findViewById(R.id.tv_phone);





        }


    }

public  void  chekUser(PrivateChat_ViewHolder holder, String userName, String snapKey)
{
    if (snapKey.contains(userName))
    {

    }

}
public interface OnItemListener {
       void onItemClick(int position);
}

}
