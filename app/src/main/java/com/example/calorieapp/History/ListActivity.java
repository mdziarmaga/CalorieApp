package com.example.calorieapp.History;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.HistoryActivity;
import com.example.calorieapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView listView;
    TextView text;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        text = findViewById(R.id.textView11);
        listView = findViewById(R.id.list);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        text.setText(" Produkty z dnia " + date);
        dataBaseHelper = new DataBaseHelper(this);

        addDataToList();

    }

    public void addDataToList()
    {
        List<ViewModel> productList = dataBaseHelper.getData(date);
        if(productList.isEmpty())
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("\nBrak danych w bazie");
            alert.setTitle("Informacja");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   Intent intent = new Intent(ListActivity.this, HistoryActivity.class);
                   startActivity(intent);
                }
            });
            alert.create();
            alert.show();
        }
        else
        {
            ArrayAdapter<ViewModel> adapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.simple_list_item_1, productList); // productList);
            listView.setAdapter(adapter);
        }
    }
}
