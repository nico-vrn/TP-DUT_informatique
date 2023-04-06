package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ExerciceMathActivity extends AppCompatActivity {
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicemath);
        numberPicker = (NumberPicker) findViewById(R.id.mult_picke);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

    }

    public void choixTable(View view){
        Intent intent = new Intent(this, TableMultiplicationActivity.class);

        intent.putExtra("NOMBRE_KEY", numberPicker.getValue());
        startActivity(intent);
    }

}