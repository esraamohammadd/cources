package com.example.cources.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.Student_Adapter;
import com.example.cources.classes.Student_Level;
import com.example.cources.interfaces.Listeners;
import com.example.cources.pojo.StudentLevel;
import com.example.cources.pojo.StudentModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StudentsFragment extends Fragment  {

    public static final String ARG_USERNAME = "userName";
    public static final String ARG_SUBJECT = "subject";


    RecyclerView recyclerView;
    ArrayList<StudentModel> studentModels;
    FloatingActionButton btn_add;
    DatabaseReference databaseReference;
    Student_Adapter student_adapter;


    public StudentsFragment() {
        // Required empty public constructor
    }


    public static StudentsFragment newInstance(String param1, String param2) {
        StudentsFragment fragment = new StudentsFragment();
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

     View view = inflater.inflate(R.layout.fragment_students, container, false);
       recyclerView = view.findViewById(R.id.rec_students);
        btn_add = view.findViewById(R.id.fbtn_add);

        databaseReference = FirebaseDatabase.getInstance().getReference();





        studentModels = new ArrayList<>();
          getStudents();
       student_adapter = new Student_Adapter(getActivity(), studentModels, new Student_Adapter.Listener() {
           @Override
           public void onClick(int position) {
               Intent intent = new Intent(getActivity(),SpinnerActivity.class);
               intent.putExtra(ARG_SUBJECT,studentModels.get(position).getSubject());
               intent.putExtra(ARG_USERNAME,studentModels.get(position).getUserName());
               getActivity().startActivity(intent);
           }

           @Override
           public void onItemClick(int position) {
               Intent intent = new Intent(getActivity(),Student_Level.class);
               intent.putExtra(ARG_SUBJECT,studentModels.get(position).getSubject());
               intent.putExtra(ARG_USERNAME,studentModels.get(position).getUserName());
               getActivity().startActivity(intent);
           }
       });
               recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


       btn_add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),AddStudent.class));
           }
       });

       return view;





    }
    public void getStudents(){

        databaseReference.child("students").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StudentModel studentModel = snapshot.getValue(StudentModel.class);
                studentModels.add(studentModel);
                recyclerView.setAdapter(student_adapter);
                student_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                student_adapter.notifyDataSetChanged();
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