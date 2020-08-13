package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.renderscript.Sampler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.calorieapp.ApiConnection.apiMethodsController;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PieChart piechart;
    Button addPoductButton;
    Chip productChip;
    private  final int maxCountCalories = 5000;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        piechart = findViewById(R.id.pieView);
        navigationView = findViewById(R.id.navigation);
        productChip = findViewById(R.id.chip_product);


        initiallizePieView();
        onAddProductButtonClick();
        onChipClick();
        navigationView.setSelectedItemId(R.id.menu_homePage);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
        try {
            apiConnectionExample();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onChipClick()
    {
        productChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId())
            {
                case R.id.menu_addProduct:
                    openCategoriesActivity();
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

    public void addCalories(View view)
    {
        CaloriesChange.addCalories(110);
        initiallizePieView();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    piechart.invalidate();
                }
            });
    }

    private void onAddProductButtonClick()
    {
        addPoductButton = findViewById(R.id.addProduct);
        addPoductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoriesActivity();
            }
        });
    }

    private void openCategoriesActivity()
    {
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);

        startActivity(intent);
    }

    private void openHistoryPage()
    {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }

    private void initiallizePieView()
    {
        List<PieEntry> valueOfCalories = new ArrayList<>();
        valueOfCalories.add(new PieEntry(CaloriesChange.getCountCalories(), "A"));
        valueOfCalories.add(new PieEntry(maxCountCalories - CaloriesChange.getCountCalories(), "B"));
       // valueOfCalories.add(new Slice(15, "ss"));
       // valueOfCalories.add(new );


        PieDataSet pieDataSet =new PieDataSet(valueOfCalories, "Calories");

        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        piechart.setData(pieData);



        piechart.setCenterText("dzisiaj \n " + CaloriesChange.getCountCalories() + " kcal"); //caloriesCountPerDay
        piechart.setCenterTextSize(20);
        piechart.getDescription().setEnabled(false);
        piechart.setHoleRadius(80);
        piechart.setRotationEnabled(true);
        piechart.setDrawEntryLabels(false); //usuwa opis na wykresie
        piechart.getLegend().setEnabled(false); //usuwa legende
        piechart.setTouchEnabled(false); // blokuje krazenie grafu
    }
    private void apiConnectionExample() throws IOException, JSONException {
        //getFood zwraca produkt o dokładnie takiej samej nazwie jak param jeżeli takie istnieje w bazie
        System.out.println(new apiMethodsController().getFood("apple"));
        //getHints zwraca produkty których nazwa zawiera param
        // Zawiera też produkt z dokładnym dopasowaniem
        System.out.println(new apiMethodsController().getHints("apple"));
    }
}
