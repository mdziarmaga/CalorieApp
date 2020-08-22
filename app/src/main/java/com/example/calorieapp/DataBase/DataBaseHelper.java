package com.example.calorieapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COLUMN_PRODUCT_CALORIE = "PRODUCT_CALORIE";
    public static final String COLUMN_PRODUCT_COUNT = "PRODUCT_COUNT";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_SUM_CALORIES = "SUM_CALORIES";



    public DataBaseHelper(@Nullable Context context) {
        super(context, "product.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "
                + PRODUCT_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_COUNT + " DOUBLE, "
                + COLUMN_PRODUCT_CALORIE + " INT, "
                + COLUMN_SUM_CALORIES + " FLOAT )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

      if(oldVersion < 3) {
          db.execSQL("ALTER TABLE " + PRODUCT_TABLE + " ADD COLUMN " + COLUMN_SUM_CALORIES + " FLOAT ");
      }
    }

    public boolean addData(ViewModel viewModel)
    {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();

         contentValues.put(COLUMN_PRODUCT_NAME, viewModel.getProductName());
         contentValues.put(COLUMN_PRODUCT_COUNT, viewModel.getCountProduct());
         contentValues.put(COLUMN_PRODUCT_CALORIE,viewModel.getCaloriesProduct());
         contentValues.put(COLUMN_SUM_CALORIES,viewModel.getSum() );

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
                double countProduct = data.getDouble(2);
                double caloriesProduct = data.getDouble(3);
                float sumOfCalories = data.getFloat(4);

                ViewModel newModel = new ViewModel(productId, productName, countProduct, caloriesProduct, sumOfCalories);
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

    public boolean updateDate(ViewModel viewModel, double count)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + PRODUCT_TABLE + " SET COLUMN_PRODUCT_COUNT = " + count + " WHERE " + COLUMN_ID  + " = " + viewModel.getId() );
        return  true;

//        String query = "UPDATE " + PRODUCT_TABLE + " SET COLUMN_PRODUCT_COUNT=" + viewModel.getCountProduct() + " WHERE " + COLUMN_ID + " = " + viewModel.getId();
//        Cursor updateData = db.rawQuery(query, null);
//
//        if(updateData.moveToFirst())
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues =new ContentValues();
//        contentValues.put("COLUMN_PRODUCT_COUNT", count);
//        db.update("PRODUCT_TABLE", contentValues, "COLUMN_ID = ?", new String[]{"1"}  );
//
//        return true;
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

//    public Cursor getData()
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + PRODUCT_TABLE;
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
}
