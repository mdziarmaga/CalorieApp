package com.example.calorieapp.SearchPage;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.R;
import com.squareup.picasso.Picasso;


public class AddProductDialog extends AppCompatActivity implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    Food_ food ;
    public AddProductDialog(Activity a, Food_ food) {
    this.food = food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView foodView =  this.findViewById(R.id.foodView);
        Picasso.get().load(food.getImage()).into(foodView);
        System.out.println(food.getImage());
        TextView nameView =  this.findViewById(R.id.nameView);
        nameView.setText(food.getLabel());
        setContentView(R.layout.activity_dialog);


    }

    @Override
    public void onClick(View v) {

    }
}