package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.calorieapp.History.HistoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoriesActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.menu_addProduct);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId())
                    {
                        case R.id.menu_addProduct:
                            return true;
                        case R.id.menu_history:
                            openHistoryPage();
                            //startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.menu_homePage:
                            openHomePage();
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            };

    private void openHistoryPage()
    {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }

    private void openHomePage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
