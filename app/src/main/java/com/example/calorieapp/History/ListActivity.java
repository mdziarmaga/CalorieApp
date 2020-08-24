package com.example.calorieapp.History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.R;

import java.text.SimpleDateFormat;

public class ListActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy");
        String date = intent.getStringExtra("date");
        String da = dateFormat.format(date);

        dataBaseHelper = new DataBaseHelper(ListActivity.this);

        ArrayAdapter adapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.activity_list_item, dataBaseHelper.getData(da));
        listView.setAdapter(adapter);
    }
}
