package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;

import java.util.ArrayList;


public class PrivateChats_Adapter extends RecyclerView.Adapter<PrivateChats_Adapter.Chat_ViewHolder> {

    Context context;
    ArrayList<String>messages;
 ArrayList < String> userName;





    public PrivateChats_Adapter(Context context, ArrayList<String> messages, ArrayList<String> userName) {
        this.context = context;
        this.messages = messages;
        this.userName = userName;


    }


    @NonNull
    @Override
    public Chat_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_layout,parent,false);
        return new Chat_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_ViewHolder holder, int position)
    {
        // if userMsg == user >> msg = send.visible
        holder.msg_send.setText(messages.get(position));
        holder.messagereceived.setText(messages.get(position));

        if (userName.get(position).contains("teacher"))
        {

            holder.messagereceived.setVisibility(View.GONE);
            holder.msg_send.setVisibility(View.VISIBLE);
        }else
        {
            holder.messagereceived.setVisibility(View.VISIBLE);
            holder.msg_send.setVisibility(View.GONE);

        }




        }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class Chat_ViewHolder extends RecyclerView.ViewHolder {


            TextView messagereceived,msg_send;

        public Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);
            messagereceived = itemView.findViewById(R.id.tv_msg_receive);
            msg_send = itemView.findViewById(R.id.tv_msg_send);





        }


    }

public  void  chekUser(Chat_ViewHolder holder,String userName,String snapKey)
{
    if (snapKey.contains(userName))
    {

    }

}


}
