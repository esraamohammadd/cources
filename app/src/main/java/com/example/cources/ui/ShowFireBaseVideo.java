package com.example.cources.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.cources.R;

public class ShowFireBaseVideo extends AppCompatActivity {


    VideoView videoView;
    String link;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_fire_base_video);
        getSupportActionBar().hide();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        if (intent != null)
        {
            link = intent.getStringExtra("link");
        }
        MediaController mediaController = new MediaController(this);

        videoView.setVideoURI(Uri.parse(link));
        videoView.setMediaController(mediaController);
        videoView.canSeekBackward();
        videoView.suspend();
        videoView.start();


    }
}