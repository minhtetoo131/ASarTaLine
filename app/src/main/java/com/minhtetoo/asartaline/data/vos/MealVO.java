package com.minhtetoo.asartaline.data.vos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.minhtetoo.asartaline.data.db.GeneralTasteTypeConverter;
import com.minhtetoo.asartaline.data.db.ImagesTypeConverter;
import com.minhtetoo.asartaline.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = AppConstants.MEAL_TABLE_NAME)
public class MealVO {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "meal_id")
    @SerializedName("warDeeId")
    private String mealId;

    @SerializedName("name")
    private String mealName;

    @TypeConverters(ImagesTypeConverter.class)
    @SerializedName("images")
    private List<String> images;

    @TypeConverters(GeneralTasteTypeConverter.class)
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
        if (images == null){
            images = new ArrayList<>();
        }
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
