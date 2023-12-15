package com.example.cources.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.ui.admin.VideoesYouTubeFragment;

public class ShowYuoTubeVideo extends AppCompatActivity {


     //

     private WebView webView;
     String videoId = "V2KCAfHjySQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //!# prevent screenshot
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_show_video);
        getSupportActionBar().hide();
        webView = findViewById(R.id.webview);

        @SuppressLint("setJavaScriptEnabled")

        Intent intent = getIntent();

        if(intent != null) {

           // String videoLink = intent.getStringExtra(VideoesYouTubeFragment.ARG_VIDE_LINK);
            String videoLink = intent.getStringExtra(VideoesYouTubeFragment.ARG_VIDE_LINK);

           diplayVideo(videoLink);



        }
else Toast.makeText(this, "hhhhhhhhhhh", Toast.LENGTH_SHORT).show();






    }

    private void diplayVideo(String videoLink) {

        //*******
      //  String videoID = extractVideoID(videoLink);

        String youtubeUrl = "https://youtube.com/embed/"+videoLink;

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
            Toast.makeText(this, videoLink, Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "this other video", Toast.LENGTH_SHORT).show();
        }


    }


//    public String extractVideoID(String youtubeURL) {
//        String videoID = videoId;
//         if (youtubeURL.contains("si="))
//            {int begin =youtubeURL.indexOf("v=");
//              int end = youtubeURL.indexOf("&");
//                return youtubeURL.substring(begin+3);
//            } else
//         {
//             Toast.makeText(this, "هذا الفيديو غير متاح", Toast.LENGTH_SHORT).show();
//         }
//
//
//        return videoID;
//    }

}