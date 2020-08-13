package com.example.calorieapp.ApiConnection;

import com.example.calorieapp.Entities.Food;
import com.example.calorieapp.Entities.Food_;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class apiMethodsController {
    private getFood getFoodClass;
    public apiMethodsController(getFood getFoodClass) {
        this.getFoodClass = getFoodClass;
    }

    public apiMethodsController() {
    }


    //metoda która zwraca dokładne dopasowanie dla podaniego param
    public List<Food> getFood(String param) throws IOException, JSONException {
        return getFoodClass.getfood(param);
    }

    //metoda która zwraca niedokładne dopasowanie dla podaniego param (np. gdy param będzie miał wartość "apple" w hinst może znaleść się np. "apple juice" lub "apple cake" itp.
    // Zawiera też produkt z dokładnym dopasowaniem
   public List<Food_> getHints(String param) throws IOException, JSONException
    {
        return getFoodClass.getHints(param);
    }

}
