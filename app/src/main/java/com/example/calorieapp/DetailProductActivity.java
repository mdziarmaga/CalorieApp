package com.example.calorieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.History.HistoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    ListView list;
    DataBaseHelper dataBaseHelper;
    ArrayAdapter<ViewModel> productAdapter;
    ViewModel clickedProduct;
    Button deleteDataButton;
    Button editDataButton;
    Button detailDataButoon;
    int checkedProduct = 0;
    public static Context context = null;
    Dialog dialog;
    SimpleDateFormat dateFormat ;
    Date date ;
    String todayDate ;
    TextView todayDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        list =  (ListView) findViewById(R.id.product_list);
        deleteDataButton = findViewById(R.id.button_delete);
        editDataButton = findViewById(R.id.button_Edit);
        detailDataButoon = findViewById(R.id.button_detail);
        todayDateTextView = findViewById(R.id.textView12);
        dataBaseHelper = new DataBaseHelper(DetailProductActivity.this);


        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date() ;
        todayDate = dateFormat.format(date);

        todayDateTextView.setText("Today " + todayDate );
        navigationView = findViewById(R.id.navigation);
        addDataToList();

        listEvent();
        deleteData();
        editData();
        detailData();
        navigation();

    }
    public void navigation()
    {
        navigationView.setSelectedItemId(R.id.menu_details);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }
    public void listEvent()
    {
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedProduct = (ViewModel) parent.getItemAtPosition(position);
                checkedProduct = clickedProduct.getId();
            }
        });
    }

    public void detailData()
    {
        detailDataButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedProduct !=0) {
                    dialog = new Dialog(DetailProductActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.detail_dialog);

                    TextView nameTV = dialog.findViewById(R.id.name_productTV);
                    nameTV.setText(clickedProduct.getProductName());

                    TextView weightTV = dialog.findViewById(R.id.weight_productTV);
                    weightTV.setText(String.valueOf(Math.round(clickedProduct.getWeight())));

                    TextView energyTV = dialog.findViewById(R.id.energyTV);
                    energyTV.setText(String.valueOf(Math.round(clickedProduct.getEnergy())));

                    TextView proteinTV = dialog.findViewById(R.id.proteinTV);
                    proteinTV.setText(String.valueOf(Math.round(clickedProduct.getProtein())));

                    TextView carbsTV = dialog.findViewById(R.id.carbsTV);
                    carbsTV.setText(String.valueOf(Math.round(clickedProduct.getCarbs())));

                    TextView fatTV = dialog.findViewById(R.id.fatTV);
                    fatTV.setText(String.valueOf(Math.round(clickedProduct.getFat())));

                    TextView fiberTV = dialog.findViewById(R.id.fiberTv);
                    fiberTV.setText(String.valueOf(Math.round(clickedProduct.getFiber())));

                    Button positiveButton = dialog.findViewById(R.id.positiveButton);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    public void deleteData()
    {
        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedProduct != 0) {
                    dataBaseHelper.deleteData(clickedProduct);
                    refreshList(dataBaseHelper);
                    Toast.makeText(DetailProductActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editData()
    {
        editDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedProduct != 0) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailProductActivity.this);
                    final EditText editText = new EditText(getApplicationContext());
                    alertDialog.setTitle("  Edit ");
                    alertDialog.setMessage("\nNew weight");
                    alertDialog.setView(editText);
                    alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (!editText.getText().toString().isEmpty()) {
                                float count = (Float.parseFloat(editText.getText().toString()));
                                double newSumCalories = count * (clickedProduct.getEnergy()/100);
                                clickedProduct.setSumCalories(newSumCalories);
                                clickedProduct.setWeight(count);

                                boolean isUpdated = dataBaseHelper.upDate(clickedProduct);
                                if (isUpdated == true) {
                                    refreshList(dataBaseHelper);
                                    Toast.makeText(DetailProductActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DetailProductActivity.this, "\n" + "Complete the data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    private void refreshList(DataBaseHelper dataBaseHelper)
    {
        productAdapter = new ArrayAdapter<ViewModel>(getApplicationContext(), android.R.layout.simple_list_item_activated_1 , dataBaseHelper.getData(todayDate)); //getall() //simple_list_item_activated_1 simple_list_item_1
        list.setAdapter(productAdapter);
    }

    public void addDataToList()
    {
        List<ViewModel> productList = dataBaseHelper.getData(todayDate);
        refreshList(dataBaseHelper);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId())
                    {
                        case R.id.menu_details:
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

    private void openDailyProducts()
    {
        Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
        startActivity(intent);
    }

    private void openHistoryPage()
    {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }
}