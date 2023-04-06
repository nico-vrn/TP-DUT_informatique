package com.example.project;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciceAdditionActivity extends AppCompatActivity {
    public static final String NOMBRE_KEY = "NOMBRE_KEY";
    private TableAddition TableAddition;
    private ArrayList<Addition> TableAdditions;
    private ArrayList<EditText> resultats;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice_addition);
//        System.out.println("coucou");
        TableAdditions = new ArrayList<>();
        resultats = new ArrayList<>();
        LinearLayout linear = findViewById(R.id.calcul_linear);
        int nombreTable = getIntent().getIntExtra(NOMBRE_KEY,0);
        TableAddition = new TableAddition();


        for (Addition addition: TableAddition.getAddition()){
            LinearLayout linearTMP = (LinearLayout) getLayoutInflater().inflate(R.layout.template_calcul,null);

            TextView calcul = (TextView) linearTMP.findViewById(R.id.template_calcul);
            calcul.setText(addition.getOperande1() + "+" + addition.getOperande2() + "= ");
            EditText resultat = linearTMP.findViewById(R.id.template_resultat);
            resultats.add(resultat);
            TableAdditions.add(addition);
            //resultat.setText(Integer.toString(multiplication.getResultat()));
            linear.addView(linearTMP);


        }
    }
    public void checkResultat(View view){
        boolean juste = true;
        int nb_erreurs = 0;
        for (int i=0 ; i < TableAddition.getAddition().size();i++){
            try{
                int valeur = Integer.parseInt(resultats.get(i).getText().toString());
                if(TableAddition.getAddition().get(i).getResultat() != valeur){
                    // Mauvaise réponse
                    juste = false;
                    nb_erreurs++;
                }
            }
            catch (NumberFormatException e){
                System.out.println("Champ vide");
                juste = false;
                nb_erreurs++;

            }


        }
        Intent intent;
        if(juste){
            intent = new Intent(this,FelicitationActivity.class);



        }
        else{
            intent = new Intent(this,ErreurActivity.class);
            intent.putExtra("NB_ERREUR", nb_erreurs);
        }
        startActivity(intent);
    }
}