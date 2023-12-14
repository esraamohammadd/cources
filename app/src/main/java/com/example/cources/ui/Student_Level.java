package com.example.cources.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cources.R;
import com.example.cources.ui.admin.StudentsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Level extends AppCompatActivity {
ProgressBar progressBar;
TextView tv_percent,tv_score;
DatabaseReference databaseReference;
double percent ,score;
    String userName,subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_level);
        progressBar = findViewById(R.id.progress);
        tv_percent = findViewById(R.id.percent);
        tv_score = findViewById(R.id.score);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if (intent != null)
        {
            userName = intent.getStringExtra(StudentsFragment.ARG_USERNAME);
             subject = intent.getStringExtra(StudentsFragment.ARG_SUBJECT);

        }
        getscore();
        getprogress();

    }

    private void getprogress() {
        databaseReference.child("students_level").child(userName).child("progress").child("percent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              percent =  snapshot.getValue(Double.class);
                progressBar.setProgress((int) percent);
                tv_percent.setText("%"+(int)percent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getscore() {
        databaseReference.child("students_level").child(userName).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 score = snapshot.getValue(Double.class);

                tv_score.setText(""+"درجة الامتحان  "+score);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}