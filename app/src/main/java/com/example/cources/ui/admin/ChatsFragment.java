package com.example.cources.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.Chat_Adapter;
import com.example.cources.pojo.ChatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {
EditText et_message;
ImageView btn_send;
String message;
ArrayList<ChatModel>chatModels = new ArrayList<>();
Chat_Adapter chat_adapter;
DatabaseReference databaseReference;
RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBJECTNAME = "subjectName";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String subjectName;
    private String mParam2;

    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subjectName Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatsFragment newInstance(String subjectName, String param2) {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECTNAME, subjectName);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subjectName = getArguments().getString(ARG_SUBJECTNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_chats, container, false);
        et_message=view.findViewById(R.id.messageEditTxt);
        btn_send = view.findViewById(R.id.sendBtn);
        recyclerView = view.findViewById(R.id.chattingRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // show chating
        getChats();
        chat_adapter = new Chat_Adapter(getActivity(),chatModels);
        recyclerView.setAdapter(chat_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        // send message
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = et_message.getText().toString();

                if (!(message.isEmpty()))
                {


                    databaseReference.child("folders").child(subjectName).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String chatKey = String.valueOf(snapshot.getChildrenCount()+1);
                            ChatModel chatModel = new ChatModel("teacher",message);
                            databaseReference.child("folders").child(subjectName).child("chats").child(chatKey+"teacher").setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    et_message.setText("");
                                    recyclerView.smoothScrollToPosition(chat_adapter.getItemCount()-1);

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

       return view;

    }

    private void getChats() {

        databaseReference.child("folders").child(subjectName).child("chats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ChatModel chatModel = snapshot.getValue(ChatModel.class);
                    chatModels.add(chatModel);
                chat_adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chat_adapter.getItemCount()-1);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chat_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}