package com.example.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.db.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //
    private static final int REQUEST_CODE_ADD = 0;

    // DATA
    private com.example.project.db.DatabaseClient mDb;
    private UsersAdapter adapter;

    // VIEW
    private Button buttonAdd;
    private ListView listUser;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du DatabaseClient
        mDb = com.example.project.db.DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        listUser = findViewById(R.id.listTask);
        buttonAdd = findViewById(R.id.button_add);

        // Lier l'adapter au listView
        adapter = new UsersAdapter(this, new ArrayList<User>());
        listUser.setAdapter(adapter);

        // Ajouter un événement au bouton d'ajout
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        // EXEMPLE : Ajouter un événement click à la listView
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                user = adapter.getItem(position);

                // Message
                Toast.makeText(MainActivity.this, "Click : " + user.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Exerciceano.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        // EXEMPLE : Ajouter un événement long click à la listView
        listUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                User user = adapter.getItem(position);

                // Message
                Toast.makeText(MainActivity.this, "LongClick : " + user.getNom(), Toast.LENGTH_SHORT).show();
                deleteUser(user);
                return false;
            }
        });

        /**
         *
         *
         */
    }

    public void onExerciceano(View view) {
        Intent Anointent = new Intent(this, Exerciceano.class);
        Anointent.putExtra("user", user);
        startActivity(Anointent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des taches
        getTasks();

    }

    private void getTasks() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetTasks extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = mDb.getAppDatabase()
                        .userDao()
                        .getAll();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(users);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetTasks gt = new GetTasks();
        gt.execute();


    }

    private void deleteUser(User user) {
        class DeleteUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {


                // adding to database
                mDb.getAppDatabase()
                        .userDao()
                        .delete(user);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Toast.makeText(MainActivity.this, "Supprimer", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        DeleteUser st = new DeleteUser();
        st.execute();
        // Mise à jour des taches
        //getTasks();
    }
}
