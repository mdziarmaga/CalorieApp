package com.example.calorieapp.DataBase;

import androidx.annotation.NonNull;

public class ViewModel {
    private int id;
    private String productName;
    private double caloriesProduct;
    private double countProduct;
    private boolean choosenProduct = false;
    private float sum;
    private int selectedProduct;


    public ViewModel(int id, String productName, double caloriesProduct, double countProduct, float sum) {
        this.id = id;
        this.productName = productName;
        this.caloriesProduct = caloriesProduct;
        this.countProduct = countProduct;
        this.sum = sum;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s \nkalorie %.0f \nilość %.0f ", productName, caloriesProduct, countProduct);
      //  return productName + countProduct + caloriesProduct;
    }

    public int getSelectedProduct(int position)
    {
        selectedProduct = position;
        return position;
    }
    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public boolean isChoosen()
    {
        return choosenProduct;
    }

    public void setChoosenProduct(boolean choosenProduct)
    {
        this.choosenProduct = choosenProduct;
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

    public double getCaloriesProduct() {
        return caloriesProduct;
    }

    public void setCaloriesProduct(double caloriesProduct) {
        this.caloriesProduct = caloriesProduct;
    }

    public double getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(double countProduct) {
        this.countProduct = countProduct;
    }
}
