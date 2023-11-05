package com.example.cources.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cources.R;
import com.example.cources.student.VideosActivity;
import com.example.cources.ui.admin.VideoesFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowVideo extends AppCompatActivity {


     //

     private WebView webView;
     String videoId = "V2KCAfHjySQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        getSupportActionBar().hide();
        webView = findViewById(R.id.webview);

        @SuppressLint("setJavaScriptEnabled")

        Intent intent = getIntent();

        if(intent !=null) {

           // String videoUrl = intent.getStringExtra(VideoesFragment.ARG_VIDE_LINK);
            String videoUrl = intent.getStringExtra(VideosActivity.ARG_LINK);

            //*******
            String videoID = extractVideoID(videoUrl);
            String youtubeUrl = "https://youtube.com/embed/"+videoID;

            String frameVideo = "<html><body><div style='position: fixed; top: 0; left: 0; width: 100%; height: 100%;'><iframe width='100%' height='100%' src='" + youtubeUrl + "' frameborder='0' allowfullscreen></iframe></div></body></html>";
           //*******
            String regexYoutube  = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
            if (youtubeUrl.matches(regexYoutube))
            {
                //setting web clint
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return false;
                    }
                });
                // web setting for javascript mode
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webView.loadData(frameVideo,"text/html","utf-8");
                Toast.makeText(this, videoID, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "this other video", Toast.LENGTH_SHORT).show();
            }





        }
else Toast.makeText(this, "hhhhhhhhhhh", Toast.LENGTH_SHORT).show();






    }



    public String extractVideoID(String youtubeURL) {
        String videoID = videoId;
         if (youtubeURL.contains("v="))
            {int begin =youtubeURL.indexOf("v=");
              int end = youtubeURL.indexOf("&");
                return youtubeURL.substring(begin+2,end);
            } else
         {
             Toast.makeText(this, "هذا الفيديو غير متاح", Toast.LENGTH_SHORT).show();
         }


        return videoID;
    }
}