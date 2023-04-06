package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.db.User;

public class Exerciceano extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciceano);
        User user = getIntent().getParcelableExtra("user");

        TextView username=findViewById(R.id.username);
        username.setText("Bonjour "+user.getNom());
    }

    public void onExercice_francais(View view){
        Intent ExercicefrViewActivityIntent=new Intent(this,CapitalActivity.class);
        //faire passer le nombre de la page
        ExercicefrViewActivityIntent.putExtra(CapitalActivity.ERROR_KEY, String.valueOf(0));

        //faire passer le nb erreur
        ExercicefrViewActivityIntent.putExtra(CapitalActivity.NUMBER_PAGE, String.valueOf(0));

        //faire passer la matière
        ExercicefrViewActivityIntent.putExtra(CapitalActivity.THEME_MCQ, String.valueOf("Capital"));
        startActivity(ExercicefrViewActivityIntent);
    }

    public void onExercice_math(View view){
        Intent ExercicemathViewActivityIntent=new Intent(this,ChoixExoActivity.class);
        startActivity(ExercicemathViewActivityIntent);
    }
}