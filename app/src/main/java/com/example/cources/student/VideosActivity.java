package com.example.cources.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cources.R;
import com.example.cources.interfaces.Listeners;
import com.example.cources.student.adapter.Video_Adapter;
import com.example.cources.student.ui.MainActivity;
import com.example.cources.ui.ShowVideo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {
RecyclerView recyclerView;
TextView tv_name;
ArrayList<String> videosNames = new ArrayList<>();
DatabaseReference databaseReference;
Video_Adapter video_adapter;
 public final static String ARG_LINK = "link";
 String name,username,phone,subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        recyclerView= findViewById(R.id.rec_videos);
        tv_name = findViewById(R.id.name_st);

        Intent intent = getIntent();
        if (intent != null)
        {
            name = intent.getStringExtra(MainActivity.ARG_NAME);
            username = intent.getStringExtra(MainActivity.ARG_USERNAME);
            subject = intent.getStringExtra(MainActivity.ARG_SUBJECT);
            phone = intent.getStringExtra(MainActivity.ARG_PHONE);

        }
        tv_name.setText("أهلاً  "+name);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("spinnerVideos").child(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 String name = snapshot.getValue(String.class);
                 videosNames.add(name);
                 video_adapter.notifyDataSetChanged();

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


      video_adapter = new Video_Adapter(this, videosNames, new Listeners() {
          @Override
          public void onClick(int position) {
              // get link of video
              databaseReference.child("folders").child(subject).child("videos").child(videosNames.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      String link = snapshot.child("link").getValue(String.class);
                     // Toast.makeText(VideosActivity.this, link, Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(VideosActivity.this, ShowVideo.class);
                      intent.putExtra(ARG_LINK,link);
                      startActivity(intent);
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
          }
      });
      video_adapter.notifyDataSetChanged();
        recyclerView.setAdapter(video_adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}