package com.example.project;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TableMultiplicationActivity extends AppCompatActivity {
    public static final String NOMBRE_KEY = "NOMBRE_KEY";
    private TableMultiplication tableMultiplication;
    private ArrayList<Multiplication> tableMultiplications;
    private ArrayList<EditText> resultats;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication);
//        System.out.println("coucou");
       tableMultiplications = new ArrayList<>();
        resultats = new ArrayList<>();
        LinearLayout linear = findViewById(R.id.exo5_linear);
        int nombreTable = getIntent().getIntExtra(NOMBRE_KEY,0);
        tableMultiplication = new TableMultiplication(nombreTable);


        for (Multiplication multiplication: tableMultiplication.getMultiplications()){
            LinearLayout linearTMP = (LinearLayout) getLayoutInflater().inflate(R.layout.template_calcul,null);

            TextView calcul = (TextView) linearTMP.findViewById(R.id.template_calcul);
            calcul.setText(multiplication.getOperande1() + "x" + multiplication.getOperande2() + "= ");
            EditText resultat = linearTMP.findViewById(R.id.template_resultat);
            resultats.add(resultat);
            tableMultiplications.add(multiplication);
            //resultat.setText(Integer.toString(multiplication.getResultat()));
            linear.addView(linearTMP);


        }
    }
    public void checkResultat(View view){
        boolean juste = true;
        int nb_erreurs = 0;
        for (int i=0 ; i < tableMultiplication.getMultiplications().size();i++){
            try{
                int valeur = Integer.parseInt(resultats.get(i).getText().toString());
                if(tableMultiplication.getMultiplications().get(i).getResultat() != valeur){
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