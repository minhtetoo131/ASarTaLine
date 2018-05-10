package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MealDetailActivity extends BaseActivity {
    @BindView(R.id.iv_hero_detail)ImageView ivHeroDetail;
    @BindView(R.id.tv_meal_brief)TextView tvBrief;
    @BindView(R.id.tv_meal_price)TextView tvPrice;
    @BindView(R.id.tv_meal_detail)TextView tvDetail;
    @BindView(R.id.tv_amount)TextView tvOrderAmount;
    @BindView(R.id.tv_meal_name)TextView tvMealName;
    private ASarTaLineModel mModel;
    private MealVO mMeal;

    private int orderAmount = 0;
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
        tvMealName.setText(meal.getMealName());
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

    @OnClick(R.id.btn_plus_icon)
    public void onTapAmountAdd(View view){
        tvOrderAmount.setText(orderAmount != -1 ? ++orderAmount +"" : "0");
    }
    @OnClick(R.id.btn_minus_icon)
    public void onTapAmountsubtract(View view){
        tvOrderAmount.setText(orderAmount == 0 ? "0" : --orderAmount +"");
    }
}
