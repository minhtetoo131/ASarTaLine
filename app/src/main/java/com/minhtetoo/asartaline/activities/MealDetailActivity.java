package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.utils.AppConstants;

public class MealDetailActivity extends BaseActivity {
    private ASarTaLineModel mModel;
    private MealVO mMeal;

    private String mealId;

    public static Intent newIntent(Context context,String mealId){
        Intent intent = new Intent(context,MealDetailActivity.class);
        intent.putExtra(AppConstants.MEAL_ID,mealId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        mealId = getIntent().getStringExtra(AppConstants.MEAL_ID);

        mModel = ViewModelProviders.of(this).get(ASarTaLineModel.class);
        mModel.initDatabase(getApplicationContext());
        mMeal = mModel.getMeal(mealId);

        if (mMeal != null) {
            bindData(mMeal);
        }
    }


    private void bindData(MealVO meal) {
        //TODO bind data to UI
    }
}
