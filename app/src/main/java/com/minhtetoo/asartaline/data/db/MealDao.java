package com.minhtetoo.asartaline.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.utils.AppConstants;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMeal(MealVO meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMeals(List<MealVO> meals);

    @Query("SELECT * FROM " + AppConstants.MEAL_TABLE_NAME)
    LiveData<List<MealVO>> getAllMeals();

    @Query("SELECT * FROM " + AppConstants.MEAL_TABLE_NAME + " WHERE meal_id = :mealId LIMIT 1")
    MealVO getMeal(String mealId);

    @Query("SELECT * FROM " + AppConstants.MEAL_TABLE_NAME + " WHERE meal_id in (:mealIds)")
    List<MealVO> getMealWithIds(List<String> mealIds);

    @Query("DELETE FROM " + AppConstants.MEAL_TABLE_NAME)
    void deleteAllMeal();
}
