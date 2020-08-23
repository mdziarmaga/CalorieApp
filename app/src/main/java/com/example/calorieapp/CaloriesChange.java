package com.example.calorieapp;

public class CaloriesChange {
    public static float countCalories = 0;

    public static float getCountCalories() {
        return countCalories;
    }

    public static void setCountCalories(float countCalories) {
        CaloriesChange.countCalories = countCalories;
    }

    public static float addCalories(float calories )
    {
        countCalories += calories;
        return countCalories;
    }
}
