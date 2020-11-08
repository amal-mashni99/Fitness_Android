package com.cs.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String Height="Height";
    public static final String Name="Name";
    public static final String Weight="Weight";
    public static final String Gender="Gender";
    public static final String FLAG="FLAG";

    private EditText editName;
    private EditText editHeight;
    private EditText editWeight;
    private Spinner editGender;
    private TextView bmiCalc;
    private Button timer;
private SharedPreferences pref;
private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        addToSpinner();
        setupSharedPref();
        checkPref();
    }

    private void addToSpinner(){
        String[] arraySpinner = new String[] {
                "Male", "Female"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGender.setAdapter(adapter);
    }

    private void setupViews() {
        editName=findViewById(R.id.name);
        editHeight=findViewById(R.id.height);
        editWeight=findViewById(R.id.weight);
        editGender=findViewById(R.id.gender);
        bmiCalc=   findViewById(R.id.bmi);
        timer=findViewById(R.id.timer);
    }

    private void setupSharedPref() {
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
    }

    private void checkPref() {
        boolean flag= pref.getBoolean(FLAG,false);
        if(flag)
        {
            String name= pref.getString(Name,"");
            String height=pref.getString(Height,"");
            String weight=pref.getString(Weight,"");

            int gender=pref.getInt(Gender,-1);

            editName.setText(name);
            editHeight.setText(height);
            editWeight.setText(weight);
            editGender.setSelection(gender);

        }
    }

    public void onClickSave(View view) {
        String name=editName.getText().toString();
        String weight =editWeight.getText().toString();
        String height=editHeight.getText().toString();
        int userChoice = editGender.getSelectedItemPosition();

        //bmi calculate
        double kg= Double.valueOf(weight);
        double m= Double.valueOf(height)/100;
        double bmi=kg/(m*m);

        bmiCalc.setText("BMI="+ String.format("%.1f",bmi));


        editor.putString(Name,name);
        editor.putString(Weight,weight);
        editor.putString(Height,height);
        editor.putInt(Gender,userChoice);
        editor.putBoolean(FLAG,true);
editor.commit();
    }

    public void onClickTimer(View view) {
        Intent n= new Intent( this,Timer.class);

        startActivity(n);
    }
}