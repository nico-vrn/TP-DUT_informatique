package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.project.db.Capital;
import com.example.project.db.DatabaseClient;

import java.util.List;

public class CapitalActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    public static final String ERROR_KEY = "0";
    public static final String NUMBER_PAGE = "1";
    public static final String THEME_MCQ=  "Capital";

    int numberError, numbPage = 0;
    RadioGroup radioGroup;
    RadioButton radioButtonA, radioButtonB, radioButtonC, radioButtonD;
    TextView question;
    int goodAnswer;
    String themeMcq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        numberError = Integer.parseInt(getIntent().getStringExtra(ERROR_KEY));
        numbPage = Integer.parseInt(getIntent().getStringExtra(NUMBER_PAGE));
        numbPage++;

        radioGroup = findViewById(R.id.radioGroup);


        question = findViewById(R.id.question);
        question.setText("Question " + numbPage + "/10 : ");

        radioButtonA = findViewById(R.id.BoutonA);
        radioButtonB = findViewById(R.id.BoutonB);
        radioButtonC = findViewById(R.id.BoutonC);
        radioButtonD = findViewById(R.id.BoutonD);


    }


    private void getCapital() {

        class GetCapital extends AsyncTask<Void, Void, List<Capital>> {

            @Override
            protected List<Capital> doInBackground(Void... voids) {
                List<Capital> questionCapital = mDb.getAppDatabase()
                        .capitalDao()
                        .getAll();
                return questionCapital;
            }

            @Override
            protected void onPostExecute(List<Capital> capital) {
                super.onPostExecute(capital);

                question.setText(question.getText() + capital.get(numbPage-1).getQuestion());

                radioButtonA.setText(capital.get(numbPage-1).getReponseA());
                radioButtonB.setText(capital.get(numbPage-1).getReponseB());
                radioButtonC.setText(capital.get(numbPage-1).getReponseC());
                radioButtonD.setText(capital.get(numbPage-1).getReponseD());

                switch (capital.get(numbPage-1).getIdGood()){
                    case 1:
                        goodAnswer = R.id.BoutonA;
                        break;
                    case 2:
                        goodAnswer = R.id.BoutonB;
                        break;
                    case 3:
                        goodAnswer = R.id.BoutonC;
                        break;
                    case 4:
                        goodAnswer = R.id.BoutonD;
                }
            }
        }

        GetCapital gg = new GetCapital();
        gg.execute();
    }



    @Override
    protected void onStart() {
        super.onStart();

        themeMcq = getIntent().getStringExtra(THEME_MCQ);

        if (themeMcq.equals("Capital")){
            getCapital();
        }

    }

    public void validate(View view){
        Button validate = findViewById(R.id.Valider);
        validate.setText("Question suivante");

        validate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                nextQuestion(view);
            }
        });

        TextView answer = findViewById(R.id.answer);
        answer.setVisibility(View.VISIBLE);

        try {
            if(radioGroup.getCheckedRadioButtonId()==goodAnswer){
                answer.setText("Bonne réponse");
            } else {
                numberError++;
                answer.setText("Mauvaise Réponse");
            }
        } catch(Exception error){
            numberError++;
            answer.setText("Mauvaise Réponse");
        }

    }


    public void nextQuestion(View view){
        Intent intent;

        if(numbPage == 10){

            if(numberError == 0){
                intent = new Intent(this, FelicitationActivity.class);

            } else{
                intent = new Intent(this, ErreurActivity.class);

            }

        } else {
            intent = new Intent(this, CapitalActivity.class);

            intent.putExtra(CapitalActivity.ERROR_KEY, String.valueOf(numberError));

            intent.putExtra(CapitalActivity.NUMBER_PAGE, String.valueOf(numbPage));

            intent.putExtra(CapitalActivity.THEME_MCQ, String.valueOf(themeMcq));
        }
        startActivity(intent);
    }
    public void precedent(View view){
        finish();
    }
}

