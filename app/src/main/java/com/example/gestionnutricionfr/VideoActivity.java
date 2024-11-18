package com.example.gestionnutricionfr;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class VideoActivity extends  AppCompatActivity{
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        WebView webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        String videoUrl = "https://www.youtube.com/embed/6-jOFvn3p5Q";

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(videoUrl);

        findViewById(R.id.reproducir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(VideoActivity.this, R.raw.sonido);
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });

            }
        });

    }

}
