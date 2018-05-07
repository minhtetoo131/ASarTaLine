package com.minhtetoo.asartaline.network.responses;

import com.google.gson.annotations.SerializedName;
import com.minhtetoo.asartaline.data.vos.MealVO;

import java.util.ArrayList;
import java.util.List;

public class GetSearchMealListResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("tasteType")
    private String tasteType;

    @SerializedName("suited")
    private String suited;

    @SerializedName("minPrice")
    private String price;

    @SerializedName("isNearBy")
    private String isNear;

    @SerializedName("currentTownship")
    private String currentTownShip;

    @SerializedName("currentTLat")
    private String currentLat;

    @SerializedName("currentLng")
    private String currentLong;

    @SerializedName("searchResults")
    private List<MealVO> searchMealList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getTasteType() {
        return tasteType;
    }

    public String getSuited() {
        return suited;
    }

    public String getPrice() {
        return price;
    }

    public String getIsNear() {
        return isNear;
    }

    public String getCurrentTownShip() {
        return currentTownShip;
    }

    public String getCurrentLat() {
        return currentLat;
    }

    public String getCurrentLong() {
        return currentLong;
    }

    public List<MealVO> getSearchMealList() {
        if (searchMealList == null){
            searchMealList = new ArrayList<>();
        }
        return searchMealList;
    }
}
