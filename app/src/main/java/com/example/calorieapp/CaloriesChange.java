package com.example.calorieapp;

public class CaloriesChange {
    public static int countCalories = 0;

    public static int getCountCalories() {
        return countCalories;
    }

    public static int addCalories(int calories )
    {
        countCalories += calories;
        return countCalories;
    }
}
