package com.example.cources.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cources.R;
import com.example.cources.adapter.Folder_Adapter;
import com.example.cources.interfaces.Listeners;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoldersFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton btn_add;
    ArrayList<String> folders = new ArrayList<>();
    DatabaseReference databaseReference;
    Folder_Adapter folder_adapter;
    public static final String NAME = "name";


    public FoldersFragment() {
        // Required empty public constructor
    }


    public static FoldersFragment newInstance(String param1, String param2) {
        FoldersFragment fragment = new FoldersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_folders, container, false);

       databaseReference = FirebaseDatabase.getInstance().getReference();

       btn_add = view.findViewById(R.id.fbtn_addFolder);
       recyclerView = view.findViewById(R.id.rec_folder);
       getFolders();
        folder_adapter = new Folder_Adapter(getActivity(), folders, new Listeners() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(),SubjectContent.class);
                intent.putExtra(NAME,folders.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(folder_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddFolder.class));
            }
        });

        return view;



    }

    private void getFolders() {
        databaseReference.child("folders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                folders.add(snapshot.getKey());
                folder_adapter.notifyDataSetChanged();
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