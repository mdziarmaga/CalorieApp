package com.example.calorieapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DetailProductActivity;
import com.example.calorieapp.MainActivity;
import com.example.calorieapp.R;
import com.example.calorieapp.SearchPage.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    CalendarView calendar;
    DataBaseHelper dataBaseHelper;
    String choosenDate;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        calendar = findViewById(R.id.calendarView);

        listView = findViewById(R.id.list);

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

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                try {
                    SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = dayOfMonth+ "-"+ (month+1)+ "-" +year;
                    Date date = dateFormat.parse(dateString);
                    Date dateCurent = new Date();
                    String currentDate = dateFormat.format(dateCurent);

                    choosenDate = dateFormat.format(date);

                    Intent intent;
                    if(choosenDate.equals(currentDate) == true) //choosenDate.equals(currentDate)
                    {
                        intent = new Intent(HistoryActivity.this, DetailProductActivity.class);
                        startActivity(intent);
                    }
                    else {
                        intent = new Intent(HistoryActivity.this, ListActivity.class);
                        intent.putExtra("date", choosenDate);
                        startActivity(intent);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//
//    public void showDetail()
//    {
//        ArrayAdapter adapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.simple_list_item_1,dataBaseHelper.getData(choosenDate));
//        listView.setAdapter(adapter);
//    }

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
