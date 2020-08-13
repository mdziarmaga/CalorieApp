package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    ListView productList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        productList = findViewById(R.id.product_list);

        addDataToList();

        productList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckedTextView checkedTextView = (CheckedTextView) view;
                //boolean isChecked = checkedTextView.isChecked();

                //Podmienic tylko konkretna klase
               // Class c = productList.getItemAtPosition(position);
               // c.setWybrana(isChecked);
            }
        });



        navigationView = findViewById(R.id.navigation);
        navigationView.setSelected(false);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    public void addDataToList()
    {
        //progressDialog = ProgressDialog.show(getApplicationContext(), "Pobieranie produktów.. ", "Prosze czekać", true);
        List<String> lista = new ArrayList<>();
        lista.add("Jablko");
        lista.add("Buka");
        lista.add("Jablko");
        lista.add("Buka");
        lista.add("Jablko");
        lista.add("Buka");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1 , lista);
        productList.setAdapter(arrayAdapter);
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
                            openHomePage();
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    private void openHomePage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
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
}