package com.minhtetoo.asartaline.mvp.presenters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.minhtetoo.asartaline.controllers.MealItemController;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.mvp.views.MealListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealListPresenter extends BasePresenter<MealListView> implements MealItemController {
    private LifecycleOwner mLifecycleOwner;
    private ASarTaLineModel mModel;
    private List<MealVO> allMeals;

    public MealListPresenter(LifecycleOwner lifecycleOwner, ASarTaLineModel model) {
        mLifecycleOwner = lifecycleOwner;
        mModel = model;
        allMeals = new ArrayList<>();
    }
    @Override
    public void onCreate(MealListView view) {
        super.onCreate(view);
        showWelcomeMessage();
        startLoadingMeals();
    }

    @Override
    public void onStart() {
        startLoadingMeals();
    }

    @Override
    public void onStop() {
    }

    private void showWelcomeMessage() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            mView.showWelcomeMessage("Good Morning,Anish","It's time for Breakfast");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            mView.showWelcomeMessage("Good AfterNoon,Anish","It's time for Lunch");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            mView.showWelcomeMessage("Good Evening,Anish","It's time for Dinner");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            mView.showWelcomeMessage("Good Night,Anish","It's time for Dinner");
        }
    }

    public void startLoadingMeals() {
        mModel.loadMealList().observe(mLifecycleOwner, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> mealVOS) {
                if (mealVOS != null) {
                    mView.setAllMeals(mealVOS);
                    mView.displayMeals(mealVOS);
                }
            }
        });
    }
    public void onForceRefresh() {
        mModel.loadMealList().observe(mLifecycleOwner, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> meals) {
                if (meals != null) {
                    mView.displayMeals(meals);
                } else {
                    mView.displayError();
                }
            }
        });
    }
    public void onJobListEndReach() {
        mModel.loadMoreMeal().observe(mLifecycleOwner, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> mealNextPage) {
                if (mealNextPage != null && mealNextPage.size() > 0) {
                    mView.displayMeals(mealNextPage);
                } else {
                    mView.displayError();
                }
            }
        });

    }

    public void onTapSearch(final String searchKeyword) {
        mModel.searchMealList(searchKeyword,"","",
                "","","","",
                "")
                .observe(mLifecycleOwner,
                        new Observer<List<MealVO>>() {
                            @Override
                            public void onChanged(@Nullable List<MealVO> searchResponse) {
                                List<MealVO>  matchMeals = new ArrayList<>();
                                if (searchResponse != null && searchResponse.size() > 0){
                                    for (int i = 0; i <searchResponse.size(); i++){
                                        if (searchResponse.get(i).getMealName().contains(searchKeyword)){
                                            matchMeals.add(searchResponse.get(i));
                                        }
                                    }
                                }
                                mView.displayMeals(matchMeals);
                            }
                        });
    }

    @Override
    public void onTapMeal(MealVO mealVO) {
        mView.navigateToDetails(mealVO);
    }
}
