package com.example.cources.ui.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.pojo.VideoModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class SpinnerActivity extends AppCompatActivity  {
    
    boolean[] selectVideo;
    ArrayList<Integer>videoList = new ArrayList<>();
    String[] videoArray ;
    
    DatabaseReference databaseReference;
    ArrayList <String > videoNames = new ArrayList<>();




     TextView textView;
    String userName,subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        textView = findViewById(R.id.sp);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        if (intent!=null) {
            userName = intent.getStringExtra(StudentsFragment.ARG_USERNAME);
            subject = intent.getStringExtra(StudentsFragment.ARG_SUBJECT);


        }


        fillArrayList();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add video from fire base to arrayList "videoNames"


                // convert array "videoArray" to ArrayList"videoNames"
                videoArray = videoNames.toArray(new String[videoNames.size()]);



                AlertDialog.Builder builder = new AlertDialog.Builder(SpinnerActivity.this);
                builder.setTitle(R.string.videoNo);

                selectVideo = new boolean[videoArray.length];
                builder.setCancelable(false);
                videoList.clear();
                builder.setMultiChoiceItems(videoArray, selectVideo, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if (b)
                        {
                            // when checkbox selected add position in videoList

                            videoList.add(i);
                            // sort list
                            Collections.sort(videoList);

                        }else
                        {
                            // when checkbox unselected remove position from videoList
                            videoList.remove(i);
                        }


                    }
                });
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // initialise string builder
                        StringBuilder stringBuilder =new StringBuilder();
                        for (int j = 0; j < videoList.size(); j++) {

                            // concat array value
                            stringBuilder.append(videoArray[videoList.get(j)]);

                          // add selected videos to firebase

                             databaseReference.child("spinnerVideos").child(userName).child(videoArray[videoList.get(j)]).setValue(videoArray[videoList.get(j)]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     Toast.makeText(SpinnerActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                                 }
                             });


                        }

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

               builder.show();
            }
        });









    }

    private ArrayList<String> fillArrayList() {

        // git videos from firebase
        databaseReference.child("folders").child(subject).child("videos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                VideoModel videoModel = snapshot.getValue(VideoModel.class);
                videoNames.add(videoModel.getName());


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

   return  videoNames;
    }


}