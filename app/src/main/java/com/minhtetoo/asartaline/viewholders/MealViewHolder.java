package com.minhtetoo.asartaline.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.controllers.MealItemController;
import com.minhtetoo.asartaline.data.vos.MealVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealViewHolder extends BaseViewHolder<MealVO> {
    @BindView(R.id.iv_meal)ImageView ivMeal;
    @BindView(R.id.tv_meal_name)TextView tvMealName;
    @BindView(R.id.tv_meal_type)TextView tvMealType;
    @BindView(R.id.tv_meal_cost)TextView tvMealPrice;



    private MealItemController mMealItemController;

    public MealViewHolder(View itemView, MealItemController itemController) {
        super(itemView);
        mMealItemController = itemController;
    }

    @Override
    public void setData(MealVO data) {
        mData = data;
        tvMealName.setText(data.getMealName());
        if (data.getTasteVOList().size() >= 1){
            tvMealType.setText(data.getTasteVOList().get(0).getTaste());
        }
        tvMealPrice.setText(data.getPrice()+ "");

        if (data.getImages() != null && data.getImages().size() > 0){
            Glide.with(ivMeal.getContext())
                    .load(data.getImages().get(0))
                    .into(ivMeal);
        }
    }

    @Override
    public void onClick(View view) {
        mMealItemController.onTapMeal(mData);
    }
}
