package com.example.calorieapp.MyProfile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {


    public static Context context = null;
    Spinner genderSpinner;
    Spinner goalSpinner;
    EditText growthEdit;
    EditText weightEdit;
    TextView caloriesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MyProfile.this;
        setContentView(R.layout.activity_my_profile);
        genderSpinner = findViewById(R.id.genderSpinner);
        goalSpinner = findViewById(R.id.goalSpinner);
        growthEdit = findViewById(R.id.growthEdit);
        weightEdit = findViewById(R.id.weightEdit);
        caloriesView = findViewById(R.id.caloriesView);
        List<String> genderStrings = new ArrayList<>();
        genderStrings.add("Male");
        genderStrings.add("Female");

        ArrayAdapter<String> adopter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,genderStrings);
        genderSpinner.setAdapter(adopter);

        List<String> goalStrings = new ArrayList<>();
        goalStrings.add("Loss weight");
        goalStrings.add("Gain weight");
        goalStrings.add("Keep weight");

        ArrayAdapter<String> goalAdopter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,goalStrings);
        goalSpinner.setAdapter(goalAdopter);
        setCaloricRequirement();
    }
    private void setCaloricRequirement()
    {
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isAllFulfiled()==true)
                {
                    int growth = Integer.parseInt(String.valueOf(growthEdit.getText()));
                    int weight =Integer.parseInt(String.valueOf(weightEdit.getText()));
                    String calories = String.valueOf(calculateBMR(weight,growth));
                    caloriesView.setText(calories+"kcal");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        goalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isAllFulfiled()==true)
                {
                    int growth = Integer.parseInt(String.valueOf(growthEdit.getText()));
                    int weight =Integer.parseInt(String.valueOf(weightEdit.getText()));
                    String calories = String.valueOf(calculateBMR(weight,growth));
                    caloriesView.setText(calories+"kcal");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        growthEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isAllFulfiled()==true)
                {
                    int growth = Integer.parseInt(String.valueOf(growthEdit.getText()));
                    int weight =Integer.parseInt(String.valueOf(weightEdit.getText()));
                    String calories = String.valueOf(calculateBMR(weight,growth));
                    caloriesView.setText(calories+"kcal");
                }
            }
        });

        weightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isAllFulfiled()==true)
                {
                    int growth = Integer.parseInt(String.valueOf(growthEdit.getText()));
                    int weight =Integer.parseInt(String.valueOf(weightEdit.getText()));
                    String calories = String.valueOf(calculateBMR(weight,growth));
                    caloriesView.setText(calories+"kcal");
                }
            }
        });
    }
    private boolean isAllFulfiled()
    {
        if( growthEdit.getText().length()<1|| weightEdit.getText().length()<1)
        {
        return false;
        }
        else
        {
            return true;
        }
    }

    private int calculateBMR(int weight, int growth)
    {
        if(genderSpinner.getSelectedItem()=="Male")
        {
            int calories = (int) ((9.99*weight+6.25*growth-145)*1.5);
            int finalCalories = 0;
            String goal = (String) goalSpinner.getSelectedItem();
            switch(goal)
            {
                case "Loss weight":
                    finalCalories = calories - 300;
                    break;
                case "Gain weight":
                    finalCalories = (int) (calories +300);
                    break;
                case "Keep weight":
                    finalCalories = calories;
                    break;
            }
            return finalCalories;
        }
        else
        {
            int calories = (int) ((9.99*weight+6.25*growth-305)*1.5);
            int finalCalories = 0;
            String goal = (String) goalSpinner.getSelectedItem();
            switch(goal)
            {
                case "Loss weight":
                    finalCalories = calories - 300;
                    break;
                case "Gain weight":
                    finalCalories = (int) (calories +300);
                    break;
                case "Keep weight":
                    finalCalories = calories;
                    break;
            }
            return finalCalories;
        }
    }
}

