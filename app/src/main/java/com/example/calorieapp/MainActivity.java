package com.example.calorieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PieChart piechart;
    Button addPoductButton;
    int caloriesCountPerDay =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Calories counter");

        piechart = findViewById(R.id.pieView);

        initiallizePieView();
        onAddProductButtonClick();

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
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    private void initiallizePieView()
    {

        piechart.setRotationEnabled(true);
        //piechart.setHoleRadius(24f);
        List<PieEntry> valueOfCalories = new ArrayList<>();
        valueOfCalories.add(new PieEntry(346, "A"));
        valueOfCalories.add(new PieEntry(900, "B"));

        PieDataSet pieDataSet =new PieDataSet(valueOfCalories, "Calories");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        piechart.setData(pieData);

        piechart.setCenterText("dzisiaj " + caloriesCountPerDay + " kcal");
        piechart.setCenterTextSize(20);
        piechart.getDescription().setEnabled(false);
        piechart.setDrawEntryLabels(false); //usuwa opis na wykresie
        piechart.getLegend().setEnabled(false); //usuwa legende
        piechart.setTouchEnabled(false); // blokuje krazenie grafu
    }
}
