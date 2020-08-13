
package com.example.calorieapp.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food_ {

    @SerializedName("foodId")
    @Expose
    private String foodId;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("nutrients")
    @Expose
    private Nutrients_ nutrients;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("categoryLabel")
    @Expose
    private String categoryLabel;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("foodContentsLabel")
    @Expose
    private String foodContentsLabel;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Nutrients_ getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients_ nutrients) {
        this.nutrients = nutrients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFoodContentsLabel() {
        return foodContentsLabel;
    }

    public void setFoodContentsLabel(String foodContentsLabel) {
        this.foodContentsLabel = foodContentsLabel;
    }

    @Override
    public String toString() {
        return "Food_{" +
                "foodId='" + foodId + '\'' +
                ", uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", nutrients=" + nutrients +
                ", category='" + category + '\'' +
                ", categoryLabel='" + categoryLabel + '\'' +
                ", image='" + image + '\'' +
                ", brand='" + brand + '\'' +
                ", foodContentsLabel='" + foodContentsLabel + '\'' +
                '}';
    }
}
