package com.minhtetoo.asartaline.network;

import com.minhtetoo.asartaline.network.responses.GetMealListRespose;
import com.minhtetoo.asartaline.network.responses.GetSearchMealListResponse;
import com.minhtetoo.asartaline.utils.AppConstants;


import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ASarTaLineAPI {

    @FormUrlEncoded
    @POST(AppConstants.API_GET_MEAL_LIST)
    Single<GetMealListRespose> getMealList(
            @Field("access_token") String access_token
    );

    @FormUrlEncoded
    @POST(AppConstants.API_SEARCH_MEAL_LIST)
    Single<GetSearchMealListResponse> searchMeal(
            @Field("access_token") String access_token,
            @Field("taste_type") String tasteType,
            @Field("suited") String suited,
            @Field("min_price") String minPrice,
            @Field("max_price") String maxPrice,
            @Field("is_nearby") String isNear,
            @Field("current_township") String currentTownShip,
            @Field("current_lat") String currentLat,
            @Field("current_lng") String currentLong
    );
}
