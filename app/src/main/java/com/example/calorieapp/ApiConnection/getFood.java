package com.example.calorieapp.ApiConnection;


import com.example.calorieapp.Entities.Example;
import com.example.calorieapp.Entities.Food;
import com.example.calorieapp.Entities.Food_;
import com.example.calorieapp.Entities.Hint;
import com.example.calorieapp.Entities.Parsed;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


public class getFood {
    public static List<Food> getfood(String param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        List<Food> foodList = new ArrayList<>();
        Example example = null;
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("https://api.edamam.com/api/food-database/parser").newBuilder();
        urlBuilder.addQueryParameter("ingr", param);
        urlBuilder.addQueryParameter("fbclid","IwAR1lE7Oyntt_ZNO1RKT1asJxYQozWXMs73c_jVMWYJDcdcxfJfutO5i4IXc");
        urlBuilder.addQueryParameter("app_key","80fcb49b500737827a9a23f7049653b9");
        urlBuilder.addQueryParameter("app_id","07d50733");
        String url = urlBuilder.build().toString();
        URL urll = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() == 200) {
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            Gson gson = new Gson();
            example = gson.fromJson(is, Example.class);
           List<Parsed> parsedList =   example.getParsed();
           for(Parsed element: parsedList)
           {
                foodList.add(element.getFood());
           }
            connection.disconnect();
    }
        return foodList;
    }
    public static List<Food_> getHints(String param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        List<Food_> foodList = null;
        List<Hint> hintList = null;
        Example example = null;
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("https://api.edamam.com/api/food-database/parser").newBuilder();
        urlBuilder.addQueryParameter("ingr", param);
        urlBuilder.addQueryParameter("fbclid","IwAR1lE7Oyntt_ZNO1RKT1asJxYQozWXMs73c_jVMWYJDcdcxfJfutO5i4IXc");
        urlBuilder.addQueryParameter("app_key","80fcb49b500737827a9a23f7049653b9");
        urlBuilder.addQueryParameter("app_id","07d50733");
        String url = urlBuilder.build().toString();
        URL urll = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() == 200) {
            InputStreamReader is = new InputStreamReader(connection.getInputStream());

            Gson gson = new Gson();
            example = gson.fromJson(is, Example.class);
            foodList = new ArrayList<>();
           hintList = example.getHints();
            for(Hint element: hintList)
            {
                foodList.add(element.getFood());
            }
            connection.disconnect();
        }
        return foodList;
    }
}
