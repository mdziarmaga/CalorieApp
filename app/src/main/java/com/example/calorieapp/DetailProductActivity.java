package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    ListView list;
    ProgressDialog progressDialog;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter<ViewModel> productAdapter;
    ViewModel clickedProduct;
    Button deleteDataButton;
    Button editDataButton;
    int checkedProduct =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        list = findViewById(R.id.product_list);
        deleteDataButton = findViewById(R.id.button_delete);
        editDataButton = findViewById(R.id.button_Edit);

        addDataToList();

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 clickedProduct = (ViewModel) parent.getItemAtPosition(position);
                 checkedProduct = position;
            }
        });

        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dataBaseHelper.deleteData(clickedProduct);
                    refreshList(dataBaseHelper);
                    Toast.makeText(DetailProductActivity.this, "Produkt usuniety", Toast.LENGTH_SHORT).show();

            }
        });

        editDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(DetailProductActivity.this);
                final EditText editText= new EditText(getApplicationContext());
                alertDialog.setTitle("Edycja produktu ");
                alertDialog.setMessage("\nWpisz nową ilość produktu");
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!editText.toString().isEmpty()) {
//                            float newCount = Float.parseFloat(editText.getText().toString());
//                            dataBaseHelper.updateDate(clickedProduct, newCount);
//                            refreshList(dataBaseHelper);
                           // Toast.makeText(DetailProductActivity.this, "Produkt został zaktualizowany", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            alertShow("Wprowadź dane", "Błąd", "Ok" );
                            //Toast.makeText(DetailProductActivity.this, "Wprowadź dane", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // finish();
                    }
                });

                alertDialog.show();
            }
        });

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelected(false);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    private void alertShow(String text, String title, String buttonOption){
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setMessage(text);
        alert.setTitle(title);
        alert.setPositiveButton(buttonOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create();
        alert.show();
    }

    private void refreshList(DataBaseHelper dataBaseHelper)
    {
        productAdapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.simple_list_item_activated_1 , dataBaseHelper.getAll()); //simple_list_item_activated_1
        list.setAdapter(productAdapter);
    }

    public void addDataToList()
    {
        dataBaseHelper = new DataBaseHelper(DetailProductActivity.this);
        List<ViewModel> productList = dataBaseHelper.getAll();
        refreshList(dataBaseHelper);
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