package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailActivity extends BaseActivity {
    @BindView(R.id.iv_hero_detail)ImageView ivHeroDetail;
    @BindView(R.id.tv_meal_brief)TextView tvBrief;
    @BindView(R.id.tv_meal_price)TextView tvPrice;
    @BindView(R.id.tv_meal_detail)TextView tvDetail;
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
        ButterKnife.bind(this,this);

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
        if (meal.getImages() != null && meal.getImages().size() > 0){
            Glide.with(getApplicationContext())
                    .load(meal.getImages().get(0))
                    .into(ivHeroDetail);
        }
        tvPrice.setText(meal.getPrice()+ "");
        if (meal.getTasteVOList() != null && meal.getTasteVOList().size() > 0){
            tvBrief.setText(meal.getTasteVOList().get(0).getTaste());
            tvDetail.setText(meal.getTasteVOList().get(0).getDesc());
        }


    }
}
