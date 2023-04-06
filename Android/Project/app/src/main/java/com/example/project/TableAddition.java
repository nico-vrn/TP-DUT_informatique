package com.example.project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class TableAddition {
    private ArrayList<Addition> table = new ArrayList<>();

    public TableAddition(){
        for(int i=0;i<=10;i++){
            int nombre = new Random().nextInt(11);
            int nombre2 = new Random().nextInt(11);
            table.add(new Addition(nombre,nombre2));
        }
    }
    public ArrayList<Addition> getAddition(){
        return table;
    }
    }

