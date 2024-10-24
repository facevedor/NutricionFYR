package com.example.gestionnutricionfr;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import  android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class ThreadActivity extends AppCompatActivity{

    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        textView= findViewById(R.id.texto1);
        imageView = findViewById(R.id.imagenPrueba);
        progressBar = findViewById(R.id.progressBar);

        Thread thread = new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        textView.setText("Alimento del dia.");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.silhouette);
                    }
                });

            }
        });
      thread.start();
    }

}
