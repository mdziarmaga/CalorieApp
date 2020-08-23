package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.example.calorieapp.SearchPage.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        calendar = findViewById(R.id.calendarView);

        calendarEvent();

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.menu_history);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    public void calendarEvent()
    {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               // String date =
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
                            openSearchActivity();
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

    private void openSearchActivity()
    {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    private void openHomePage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
