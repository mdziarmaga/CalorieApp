package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.menu_history);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
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
                            return true;
                        case R.id.menu_homePage:
                             openHomePage();
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    private void openCategoriesActivity()
    {
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        startActivity(intent);
    }

    private void openHomePage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
