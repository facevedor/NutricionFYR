package com.example.gestionnutricionfr;
import android.content.Intent;
import  android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AccesoActivity extends AppCompatActivity{
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
    }
    public void onClickVentana(View view){
        Intent intent = new Intent(this, VentanaActivity.class);
        startActivity(intent);
    }
    public void onClickVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void onClickUbicacion(View view){
        Intent intent = new Intent(this, MapaActivity.class);
        startActivity(intent);
    }
    public void onClickImagen(View view){
        Intent intent = new Intent(this, ThreadActivity.class);
        startActivity(intent);
    }
}


