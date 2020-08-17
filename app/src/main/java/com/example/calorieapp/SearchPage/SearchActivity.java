package com.example.calorieapp.SearchPage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.ApiConnection.apiMethodsController;
import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {
    ListView listView;
    ListViewAdopter adapter;
    LayoutInflater inflater;
    //  public final Activity activity;

    public static  Context context = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        setContentView(R.layout.search_activity);
        inflater = LayoutInflater.from(context);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Products List");

    }

    public void openAddDialog(Food_ food, View view)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.nameView);
        text.setText(food.getLabel());
        ImageView productImage = (ImageView) dialog.findViewById(R.id.foodView);
        Picasso.get().load(food.getImage()).into(productImage);
        dialog.show();
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