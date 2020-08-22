package com.example.calorieapp.SearchPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.ApiConnection.apiMethodsController;
import com.example.calorieapp.CaloriesChange;
import com.example.calorieapp.CategoriesActivity;
import com.example.calorieapp.DataBase.DataBaseHelper;
import com.example.calorieapp.DataBase.ViewModel;
import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.HistoryActivity;
import com.example.calorieapp.MainActivity;
import com.example.calorieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {
    ListView listView;
    ListViewAdopter adapter;
    LayoutInflater inflater;
    Dialog dialog;
    public static  Context context = null;
    BottomNavigationView navigationView;
    DataBaseHelper dataBaseHelper;
    MainActivity m = new MainActivity();
    ViewModel viewModel;

    private static final String TAG ="SearcActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        setContentView(R.layout.search_activity);
        inflater = LayoutInflater.from(context);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Products List");

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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
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
                    int countCaloriesOfProduct = Integer.parseInt(editText.getText().toString());
                    double caloriesOfProductDouble = food.getNutrients().getENERCKCAL();
                    float caloriesOfProductInt = (float) caloriesOfProductDouble;
                    float sumCalories = countCaloriesOfProduct * (caloriesOfProductInt/1000);
                    CaloriesChange.addCalories(sumCalories);
                  //  m.initiallizePieView();

                    //tworzenie modeu do bazy danych
                   viewModel = new ViewModel( -1 ,food.getLabel() , food.getNutrients().getENERCKCAL(), countCaloriesOfProduct, sumCalories );

//                    dataBaseHelper = new DataBaseHelper(context);
//                    boolean success = dataBaseHelper.addData(viewModel);
                    addToDataBase();

                    alertShow("Produkt został dodany", null, "Ok");
                    dialog.dismiss();
                }
                else
                {
                    alertShow("Wprowadź poprawne dane", "Błąd", "Ok");
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

    private void addToDataBase(){
        dataBaseHelper = new DataBaseHelper(context);
        boolean success = dataBaseHelper.addData(viewModel);
    }

    private void alertShow(String text, String title, String buttonOption){
        AlertDialog.Builder alert = new AlertDialog.Builder(dialog.getContext());
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
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            List<Food_> food_list= null;
            @Override
            public boolean onQueryTextSubmit(String s)
            {

                try {
                    food_list =  new apiMethodsController().getHints(s);
                    listView = findViewById(R.id.listView);
                    adapter = new ListViewAdopter(SearchActivity.this, food_list);
                    listView.setAdapter(adapter);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){ }
                else { }
                return true;
            }
        });
        return true;
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