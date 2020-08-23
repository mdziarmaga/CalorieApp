package com.example.calorieapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.Nullable;

import com.example.calorieapp.CaloriesChange;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "ID";
    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_ENERGY = "PRODUCT_ENERGY";
    public static final String COLUMN_PRODUCT_WEIGHT = "PRODUCT_WEIGHT";
    public static final String COLUMN_PRODUCT_SUMCALORIES = "PRODUCT_SUMCALORIES";
    public static final String COLUMN_PRODUCT_PROTEIN = "PRODUCT_PROTEIN";
    public static final String COLUMN_PRODUCT_FAT = "PRODUCT_FAT";
    public static final String COLUMN_PRODUCT_CARBS = "PRODUCT_CARBS";
    public static final String COLUMN_PRODUCT_FIBER = "PRODUCT_FIBER";

    //public static final String COLUMN_DATE = "PRODUCT_DATE";

    public boolean upDate(ViewModel viewModel )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, viewModel.getId());
        contentValues.put(COLUMN_PRODUCT_NAME, viewModel.getProductName());
        contentValues.put(COLUMN_PRODUCT_ENERGY,viewModel.getEnergy());
        contentValues.put(COLUMN_PRODUCT_WEIGHT, viewModel.getWeight());

        contentValues.put(COLUMN_PRODUCT_SUMCALORIES ,viewModel.getSumCalories() );
        contentValues.put(COLUMN_PRODUCT_PROTEIN, viewModel.getProtein());
        contentValues.put(COLUMN_PRODUCT_FAT,viewModel.getFat());
        contentValues.put(COLUMN_PRODUCT_CARBS ,viewModel.getCarbs() );
        contentValues.put(COLUMN_PRODUCT_FIBER ,viewModel.getFiber() );


        db.update(PRODUCT_TABLE, contentValues, "ID = ?", new String[]{Integer.toString(viewModel.getId())}); //"viewModel.getId()"
        return true;
    }

    public DataBaseHelper(@Nullable Context context) {
        super(context, "calorie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "
                + PRODUCT_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_ENERGY + " DOUBLE, "
                + COLUMN_PRODUCT_WEIGHT + " DOUBLE, "
                + COLUMN_PRODUCT_SUMCALORIES + " DOUBLE, "
                + COLUMN_PRODUCT_PROTEIN + " DOUBLE, "
                + COLUMN_PRODUCT_FAT + " DOUBLE, "
                + COLUMN_PRODUCT_CARBS + " DOUBLE, "
                + COLUMN_PRODUCT_FIBER + " DOUBLE )"; // )";

               // + COLUMN_DATE + " DATE )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//      if(oldVersion < 3)
//          db.execSQL("ALTER TABLE " + PRODUCT_TABLE + " ADD COLUMN " + COLUMN_PRODUCT_SUMCALORIES + " DOUBLE");
    }

    public boolean addData(ViewModel viewModel)
    {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();

         contentValues.put(COLUMN_PRODUCT_NAME, viewModel.getProductName());
         contentValues.put(COLUMN_PRODUCT_WEIGHT, viewModel.getWeight());
         contentValues.put(COLUMN_PRODUCT_ENERGY,viewModel.getEnergy());
         contentValues.put(COLUMN_PRODUCT_SUMCALORIES ,viewModel.getSumCalories() );

        contentValues.put(COLUMN_PRODUCT_PROTEIN, viewModel.getProtein());
        contentValues.put(COLUMN_PRODUCT_FAT, viewModel.getFat());
        contentValues.put(COLUMN_PRODUCT_CARBS,viewModel.getCarbs());
        contentValues.put(COLUMN_PRODUCT_FIBER ,viewModel.getFiber() );
        //contentValues.put(COLUMN_DATE, );
        long insert = db.insert(PRODUCT_TABLE, null, contentValues);
        if(insert == -1)
        {
            return false;
        }
        else
        {
            return  true;
        }
    }



    public List<ViewModel> getAll()
    {
        List<ViewModel> getList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PRODUCT_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(queryString, null);

        if(data.moveToFirst())
        {
            do{
                int productId = data.getInt(0);
                String productName = data.getString(1);
                double energy = data.getDouble(2);
                double weight = data.getDouble(3);
                double sumCalories = data.getDouble(4);
                double protein = data.getDouble(5);
                double fat = data.getDouble(6);
                double carbs = data.getDouble(7);
                double fiber = data.getDouble(8);

                //CaloriesChange.setCountCalories((float)sumCalories);
                ViewModel newModel = new ViewModel(productId, productName, energy, weight, sumCalories, protein,fat, carbs, fiber);
                getList.add(newModel);
            }while(data.moveToNext());
        }
        else
        {

        }
        data.close();
        db.close();
        return  getList;
    }

    public boolean deleteData(ViewModel viewModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + PRODUCT_TABLE + " WHERE " + COLUMN_ID + " = " + viewModel.getId();
        Cursor dataToDelete = db.rawQuery(queryString, null);

        if(dataToDelete.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
