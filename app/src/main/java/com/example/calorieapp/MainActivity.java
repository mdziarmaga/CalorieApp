package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.History.HistoryActivity;
import com.example.calorieapp.MyProfile.MyProfile;
import com.example.calorieapp.SearchPage.SearchActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PieChart piechart;
    Button addPoductButton;
    Button myProfile;
    Chip productChip;
    private int maxCountCalories ;
    BottomNavigationView navigationView;
    DataBaseHelper dataBaseHelper;
    SimpleDateFormat dateFormat ;
    Date date ;
    String todayDate ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        piechart = findViewById(R.id.pieView);
        navigationView = findViewById(R.id.navigation);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date() ;
        todayDate = dateFormat.format(date);



        initiallizePieView();
        onAddButtonClick();
        onMyProfileClick();
        navigation();
        if(getCaloricDemand()==0)
        {
            showCreateProfileDialog();
        }

    }

    public void navigation()
    {
        navigationView.setSelectedItemId(R.id.menu_homePage);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.menu_details:
                    OpenDailyProducts();
                    overridePendingTransition(0,0);
                    return true;
                case R.id.menu_history:
                    openHistoryPage();
                    overridePendingTransition(0,0);
                    return true;
                case R.id.menu_homePage:
                    return true;
            }
            return false;
        }
    };
    private void onMyProfileClick()
    {
        myProfile = findViewById(R.id.myProfileButton);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                startActivity(intent);
            }
        });
    }
    private void onAddButtonClick()
    {
        addPoductButton = findViewById(R.id.addProduct);
        addPoductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCaloricDemand()==0)
                {
                    showCreateProfileDialog();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void showCreateProfileDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setPositiveButton("My profile", null)
                .setTitle("My profile")
                .setMessage("Welcome in CaloriesCounter,\nbefore you'll start using app, create your profile")
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                        startActivity(intent);
                    }
                });
            }
        });
        dialog.show();
    }
    private void OpenDailyProducts()
    {
        Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
        startActivity(intent);
    }

    private void openHistoryPage()
    {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }

    private float checkCalories()
    {
        if(dataBaseHelper.getSumCalories(todayDate) > getCaloricDemand())
        {
            final float newMaxCountCalories;
            float different = dataBaseHelper.getSumCalories(todayDate) - getCaloricDemand();
            newMaxCountCalories = getCaloricDemand() + different +500;
            return newMaxCountCalories;
        }
        else
        {
            return getCaloricDemand();
        }
    }
    private int getCaloricDemand()
    {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        maxCountCalories = pref.getInt("caloricDemand",0);
        return maxCountCalories;
    }
    public void initiallizePieView()
    {
        float sumCalories = getSumOfCalories();
        List<PieEntry> valueOfCalories = new ArrayList<>();
        valueOfCalories.add(new PieEntry( sumCalories )); //CaloriesChange.getCountCalories()
        valueOfCalories.add(new PieEntry(checkCalories() - sumCalories));

        PieDataSet pieDataSet =new PieDataSet(valueOfCalories, "Calories");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(Color.rgb(77, 77, 77),Color.rgb(204, 204, 204));
        piechart.setData(pieData);
        piechart.setCenterText( String.format("%.0f", sumCalories)+ "/"+getCaloricDemand()+"\nkcal ");
        piechart.setCenterTextSize(30);
        piechart.getDescription().setEnabled(false);
        piechart.setHoleRadius(90);
        piechart.setRotationEnabled(true);
        piechart.setDrawEntryLabels(false); //usuwa opis na wykresie
        piechart.getLegend().setEnabled(false); //usuwa legende
        piechart.setTouchEnabled(false); // blokuje krazenie graf
    }

    public float getSumOfCalories()
    {
        return Math.round(dataBaseHelper.getSumCalories(todayDate));
    }

}
