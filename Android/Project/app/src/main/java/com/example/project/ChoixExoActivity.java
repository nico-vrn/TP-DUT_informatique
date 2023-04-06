package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoixExoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choixexo);
    }
    public void TabledeMultiplication(View view){
        Intent ExerciceMultViewActivityIntent=new Intent(this,ExerciceMathActivity.class);
        startActivity(ExerciceMultViewActivityIntent);
    }

    public void dixAdditions(View view){
        Intent ExerciceAdditionViewActivityIntent=new Intent(this,ExerciceAdditionActivity.class);
        startActivity(ExerciceAdditionViewActivityIntent);
    }
}