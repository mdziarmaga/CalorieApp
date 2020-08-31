package com.example.calorieapp.DataBase;

import androidx.annotation.NonNull;

public class ViewModel {
    private int id;
    private String productName;
    private double energy;
    private double weight;
    private double sumCalories;
    //private boolean choosenProduct = false;
    private double protein;
    private double fat;
    private double carbs;
    private double fiber;

    public ViewModel(int id, String productName, double energy, double weight, double sumCalories, double protein, double fat, double carbs, double fiber) {
        this.id = id;
        this.productName = productName;
        this.energy = energy;
        this.weight = weight;
        this.sumCalories = sumCalories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSumCalories() {
        return sumCalories;
    }

    public void setSumCalories(double sumCalories) {
        this.sumCalories = sumCalories;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s \namount of calories %.0f kcal\nweight %.0f g ", productName, sumCalories, weight);

    }
}
