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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cources.R;
import com.example.cources.adapter.PrivateChats_Adapter;
import com.example.cources.pojo.ChatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrivateChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivateChatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_USER_NAME = "userName";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String userName;

    ArrayList<String> t = new ArrayList<>();
    private  String message ;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> messages = new ArrayList<>();
    PrivateChats_Adapter privateChats_Adapter;

    RecyclerView recyclerView;

    public PrivateChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param userName Parameter 2.
     * @return A new instance of fragment PrivateChatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrivateChatsFragment newInstance(String param1, String userName) {
        PrivateChatsFragment fragment = new PrivateChatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            userName = getArguments().getString(ARG_USER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_private_chats, container, false);
        recyclerView = view.findViewById(R.id.private_chattingRecyclerView);
        ImageView btn_send = view.findViewById(R.id.private_sendBtn);
        EditText et_message = view.findViewById(R.id.private_messageEditTxt);

        getChats();
        privateChats_Adapter = new PrivateChats_Adapter(getActivity(),messages,t);
        recyclerView.setAdapter(privateChats_Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // send message
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = et_message.getText().toString().trim();



                if (!(message.isEmpty()))
                {
                    databaseReference.child("private_chats").child(userName).child((privateChats_Adapter.getItemCount()+1)+"teacher").setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            et_message.setText("");
                            recyclerView.smoothScrollToPosition(privateChats_Adapter.getItemCount()-1);
                        }
                    });
                }
            }
        });


     return view;
    }

    private void getChats() {

        databaseReference.child("private_chats").child(userName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String message = snapshot.getValue(String.class);
                t.add(snapshot.getKey());
                messages.add(message);
                privateChats_Adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(privateChats_Adapter.getItemCount()-1);




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                privateChats_Adapter.notifyDataSetChanged();
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