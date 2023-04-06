package com.example.project.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project.db.AppDatabase;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MyToDos").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "EcoleDesLoustics").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //
            db.execSQL("INSERT INTO user (nom, prenom) VALUES(\"Nicolas\", \"Lefranc\");");

            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la France ?\", \"Paris\", \"Londres\", \"Berlin\", \" Rome\", \"1\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Belgique ?\", \"Londres\", \"Bruxelles\", \"Berlin\", \"Rome\", \"2\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Suisse ?\", \"Bruxelles\", \"Londres\", \"Zurich\", \"Rome\", \"3\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Allemagne ?\", \"Berlin\", \"Londres\", \"Zurich\", \"Rome\", \"1\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Italie ?\", \"Paris\", \"Londres\", \"Zurich\", \"Rome\", \"4\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Pologne ?\", \"Varsovie\", \"Londres\", \"Zurich\", \"Rome\", \"1\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Roumanie ?\", \"Paris\", \"Londres\", \"Zurich\", \"Bucarest\", \"4\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Grèce ?\", \"Paris\", \"Athène\", \"Zurich\", \"Rome\", \"2\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Croatie ?\", \"Paris\", \"Athène\", \"Zagreb\", \"Rome\", \"3\");");
            db.execSQL("INSERT INTO capital (question, reponseA, reponseB, reponseC, reponseD, idGood) VALUES(\"Quelle est la capitale de la Bosnie ?\", \"Paris\", \"Athène\", \"Zagreb\", \"Sarajevo\", \"4\");");

        }

    };
}
