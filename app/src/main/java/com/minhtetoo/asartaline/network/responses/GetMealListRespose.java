package com.minhtetoo.asartaline.network.responses;

import com.google.gson.annotations.SerializedName;
import com.minhtetoo.asartaline.data.vos.MealVO;

import java.util.ArrayList;
import java.util.List;

public class GetMealListRespose {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("astlWarDee")
    private List<MealVO> mealVOList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public List<MealVO> getMealVOList() {
        if (mealVOList == null){
            mealVOList = new ArrayList<>();
        }
        return mealVOList;
    }
}
