package com.minhtetoo.asartaline.mvp.views;

import com.minhtetoo.asartaline.data.vos.MealVO;

import java.util.List;

public interface MealListView {
    void displayMeals(List<MealVO> meals);

    void displayError();

    void showLoading();

    void setAllMeals(List<MealVO> allMeals);

    void showWelcomeMessage(String morning,String timeForWhat);

    void navigateToDetails(MealVO mealVO);
}
