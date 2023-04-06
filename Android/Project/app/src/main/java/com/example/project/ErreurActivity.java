package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ErreurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreur);
        TextView nombres = findViewById(R.id.exo5_nb);
        int nb_erreur = getIntent().getIntExtra("NB_ERREUR",0);
        nombres.setText("Nombres erreurs: "  + nb_erreur);

    }
    public void autreExo(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void corrigerRep(View view){
        finish();
    }
}