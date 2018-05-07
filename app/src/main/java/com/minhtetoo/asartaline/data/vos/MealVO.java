package com.minhtetoo.asartaline.data.vos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MealVO {
    @SerializedName("warDeeId")
    private String mealId;

    @SerializedName("name")
    private String mealName;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("generalTaste")
    private List<GeneralTasteVO> tasteVOList;

    @SerializedName("priceRangeMin")
    private int price;

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setTasteVOList(List<GeneralTasteVO> tasteVOList) {
        this.tasteVOList = tasteVOList;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMealId() {
        return mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public List<String> getImages() {
        return images;
    }

    public List<GeneralTasteVO> getTasteVOList() {
        if (tasteVOList == null){
            tasteVOList = new ArrayList<>();
        }
        return tasteVOList;
    }

    public int getPrice() {
        return price;
    }
}
