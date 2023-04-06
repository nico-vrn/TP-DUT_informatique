package com.example.project;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TableMultiplication {
    private ArrayList<Multiplication> table = new ArrayList<>();

    public TableMultiplication(int nombre){
        for(int i=0;i<=10;i++){
            table.add(new Multiplication(i,nombre));
        }
    }
    public ArrayList<Multiplication> getMultiplications(){
        return table;
    }

}
