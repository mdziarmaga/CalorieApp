package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.History.ListActivity;
import com.example.calorieapp.SearchPage.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    CalendarView calendar;
    DataBaseHelper dataBaseHelper;
    String choosenDate;
    TextView t;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        calendar = findViewById(R.id.calendarView);
        t = findViewById(R.id.textView10);
        //listView = findViewById(R.id.list);

        dataBaseHelper = new DataBaseHelper(HistoryActivity.this);

        calendarEvent();
        //showDetail();

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.menu_history);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    public void calendarEvent()
    {


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {



            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               // final SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy");


                //Date date = new Date(dayOfMonth, month,year);
               // choosenDate = dateFormat.format(date);
                //long date = calendar.;
                //calendar.getDate();
               // choosenDate = dateFormat.format(date);


                choosenDate= dayOfMonth+ "-"+ month+ "-" +year;
                Intent intent = new Intent(HistoryActivity.this, ListActivity.class);
                intent.putExtra("date", choosenDate);
                startActivity(intent);
            }
        });
    }

    public void showDetail()
    {
        ArrayAdapter adapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.simple_list_item_1,dataBaseHelper.getData(choosenDate));
        listView.setAdapter(adapter);
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
