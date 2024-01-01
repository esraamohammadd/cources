package com.example.cources.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.PrivateChat_Adapter;
import com.example.cources.pojo.StudentChatsModel;
import com.example.cources.pojo.StudentModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrivatChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivatChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<StudentChatsModel>studentChatsModels = new ArrayList<>();
    PrivateChat_Adapter privateChat_adapter;
    String userName;
    public PrivatChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrivatChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrivatChatFragment newInstance(String param1, String param2) {
        PrivatChatFragment fragment = new PrivatChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privat_chat, container, false);
        recyclerView = view.findViewById(R.id.rc_students_chats);

        getChats();
         privateChat_adapter = new PrivateChat_Adapter(getActivity(), studentChatsModels, new PrivateChat_Adapter.OnItemListener() {
             @Override
             public void onItemClick(int position) {
                 userName = studentChatsModels.get(position).getUserName();
                 FragmentManager fragmentManager =getParentFragmentManager();
                 FragmentTransaction transaction = fragmentManager.beginTransaction();
                 transaction.replace(R.id.frameLayout,PrivateChatsFragment.newInstance(null,userName));
                 transaction.commit();
             }
         });
        recyclerView.setAdapter(privateChat_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void getChats() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("student_chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                StudentChatsModel studentChatsModel = snapshot.getValue(StudentChatsModel.class);
                studentChatsModels.add(studentChatsModel);
                privateChat_adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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