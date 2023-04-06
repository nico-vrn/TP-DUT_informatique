package com.example.project.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "capital")
public class Capital {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String question;
    private String reponseA;
    private String reponseB;
    private String reponseC;
    private String reponseD;
    private int idGood;


    public long getId() {
        return id;
    }

    public int getIdGood() {
        return idGood;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponseA() {
        return reponseA;
    }

    public String getReponseB() {
        return reponseB;
    }

    public String getReponseC() {
        return reponseC;
    }

    public String getReponseD() {
        return reponseD;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setIdGood(int goodId) {
        this.idGood = goodId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setReponseA(String reponseA) {
        this.reponseA = reponseA;
    }

    public void setReponseB(String reponseB) {
        this.reponseB = reponseB;
    }

    public void setReponseC(String reponseC) {
        this.reponseC = reponseC;
    }

    public void setReponseD(String reponseD) {
        this.reponseD = reponseD;
    }

}
