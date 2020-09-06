package com.example.calorieapp.SearchPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.ApiConnection.apiMethodsController;
import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.History.HistoryActivity;
import com.example.calorieapp.MainActivity;
import com.example.calorieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SearchActivity extends AppCompatActivity  {
    ListView listView;
    ListViewAdopter adapter;
    LayoutInflater inflater;
    Dialog dialog;
    public static  Context context = null;
    BottomNavigationView navigationView;
    DataBaseHelper dataBaseHelper;
    ViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        setContentView(R.layout.search_activity);
        inflater = LayoutInflater.from(context);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Find your product...");


        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.menu_details);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
        navigationView.getItemTextAppearanceActive();
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
                            openHistoryActivity();
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

    private void openHistoryActivity()
    {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }

    private void openHomePage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    public void openAddDialog(final Food_ food, View view)
    {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.nameView);
        text.setText(food.getLabel());
        ImageView productImage = (ImageView) dialog.findViewById(R.id.foodView);
        Picasso.get().load(food.getImage()).into(productImage);

        final EditText editText = dialog.findViewById(R.id.editText);
        final Editable textt = editText.getText();
        Button positiveButton = dialog.findViewById(R.id.positiveButton);
        Button negativeButton = dialog.findViewById(R.id.negativeButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textt.toString().isEmpty())
                {
                    int weight = Integer.parseInt(editText.getText().toString());
                    double caloriesOfProductDouble = food.getNutrients().getENERCKCAL();
                    float caloriesOfProduct= (float) caloriesOfProductDouble;
                    float sumCalories = calculateCalories(weight, caloriesOfProduct);

                    //tworzenie modeu do bazy danych
                   viewModel = new ViewModel( -1 ,food.getLabel(), food.getNutrients().getENERCKCAL(),
                           weight, sumCalories, food.getNutrients().getPROCNT(),food.getNutrients().getFAT(),
                           food.getNutrients().getCHOCDF(),food.getNutrients().getFIBTG());

                    dataBaseHelper = new DataBaseHelper(context);
                    boolean success = dataBaseHelper.addData(viewModel);

                    alertShow("The product has been added", null, "Ok");
                    dialog.dismiss();
                }
                else
                {
                    alertShow("Please enter valid data", "Błąd", "Ok");
                }
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public float calculateCalories(float count, float calories)
    {
        return count * (calories/100);
    }

    private void alertShow(String text, String title, String buttonOption){
        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.context);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<String> popularFood = Arrays.asList(new String[]{"apple","bread","coffe","egg","milk","orange","water","potato","banana","tea","cheese","meat","pasta","cucumber","carrot","chicken"});
        String rand =null;
        Random r=new Random();
        int randomNumber=r.nextInt(popularFood.size());
        set_foodList(popularFood.get(randomNumber));
        set_foodList(popularFood.get(randomNumber));
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                public List<Food_> food_list = null;


                @Override
                public boolean onQueryTextSubmit(String s) {

                    try {
                        food_list = new apiMethodsController().getHints(s);
                        if(food_list.size()>0) {
                            listView = findViewById(R.id.list);
                            adapter = new ListViewAdopter(SearchActivity.this, food_list);
                            listView.setAdapter(adapter);
                        }
                        else
                        {
                            alertShow ("We do not have this product in our database","Product error","OK");
                        }


                    } catch (IOException e) {
                        alertShow("We do not have this product in our database","Product error","OK");
                    } catch (JSONException e) {
                        alertShow("We do not have this product in our database","Product error","OK");
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
//                    if (TextUtils.isEmpty(s)) {
//                    }
//                    else {
//                        set_foodList(s);
//
//                    }
                    if(s.length()==0)
                    {

                    }
                   else
                    {
                        set_foodList(s);
                    }
                 return true;
                }
            });

return true;
    }



    List<Food_> food_list =null;
    public void set_foodList(String rand)
    {
        try {
            food_list = new apiMethodsController().getHints(rand);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.list);
        adapter = new ListViewAdopter(SearchActivity.this, food_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}