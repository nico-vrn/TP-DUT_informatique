package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.db.User;

public class AddUserActivity extends AppCompatActivity {

    // DATA
    private com.example.project.db.DatabaseClient mDb;

    // VIEW
    private EditText editTextTaskView;
    private EditText editTextDescriptionView;
    private EditText editTextFinishByView;
    private Button saveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Récupération du DatabaseClient
        mDb = com.example.project.db.DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        editTextTaskView = findViewById(R.id.editTextTask);
        editTextDescriptionView = findViewById(R.id.editTextDesc);
        saveView = findViewById(R.id.button_save);

        // Associer un événement au bouton save
        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

    private void saveUser() {

        // Récupérer les informations contenues dans les vues
        final String sNom = editTextTaskView.getText().toString().trim();
        final String sPrenom = editTextDescriptionView.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (sNom.isEmpty()) {
            editTextTaskView.setError("Nom required");
            editTextTaskView.requestFocus();
            return;
        }

        if (sPrenom.isEmpty()) {
            editTextDescriptionView.setError("prenom required");
            editTextDescriptionView.requestFocus();
            return;
        }

        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */
        class SaveUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = new User(sNom,sPrenom);

                // adding to database
                long id = mDb.getAppDatabase()
                        .userDao()
                        .insert(user);

                // mettre à jour l'id de la tache
                // Nécessaire si on souhaite avoir accès à l'id plus tard dans l'activité
                user.setId(id);


                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveUser st = new SaveUser();
        st.execute();
    }

}
