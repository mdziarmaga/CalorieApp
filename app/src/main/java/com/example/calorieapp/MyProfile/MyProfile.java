package com.example.calorieapp.MyProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.MainActivity;
import com.example.calorieapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity {


    public static Context context = null;
    Spinner genderSpinner;
    Spinner goalSpinner;
    EditText growthEdit;
    EditText weightEdit;
    TextView caloriesView;
    Button saveButton;
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
        saveButton = findViewById(R.id.saveButton);
        setProfile();
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

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(caloriesView.getText().length()!=0)
                {
                int caloric_demand = Integer.parseInt( (caloriesView.getText()).toString());
                int gender = genderSpinner.getSelectedItemPosition();
                int growth = Integer.parseInt(growthEdit.getText().toString());
                    int weight = Integer.parseInt(weightEdit.getText().toString());
                    int goal = goalSpinner.getSelectedItemPosition();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    pref.edit().putInt("caloricDemand",caloric_demand).apply();
                    pref.edit().putInt("gender",gender).apply();
                    pref.edit().putInt("growth",growth).apply();
                    pref.edit().putInt("weight",weight).apply();
                    pref.edit().putInt("goal",goal).apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MyProfile.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Fulfil all fields");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

    }
    private void setProfile()
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int calories = pref.getInt("caloricDemand",0 );
         if(calories!=0) {
             int calories_demand = pref.getInt("caloricDemand",0 );
             int genderPosition = pref.getInt("gender", 0);
             String growth = String.valueOf(pref.getInt("growth", 0));
             String weight = String.valueOf(pref.getInt("weight", 0));
             int goalPosition = pref.getInt("goal", 0);
             genderSpinner.setSelection(genderPosition);
             growthEdit.setText(growth);
             weightEdit.setText(weight);
             goalSpinner.setSelection(goalPosition);
         }
//        }
//        catch (Exception x)
//        {
//
//        }
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
                    caloriesView.setText(calories);
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
                    caloriesView.setText(calories);
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
                    caloriesView.setText(calories);
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
                    caloriesView.setText(calories);
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

