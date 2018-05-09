package com.minhtetoo.asartaline.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.controllers.MealItemController;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.viewholders.MealViewHolder;

public class MealListAdapter extends BaseRecyclerAdapter<MealViewHolder,MealVO> {
    private MealItemController mItemController;

    public MealListAdapter(Context context,MealItemController itemController) {
        super(context);
        mItemController = itemController;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mealItemView = mLayoutInflater.inflate(R.layout.view_item_food_list,parent,false);
        MealViewHolder mealViewHolder = new MealViewHolder(mealItemView,mItemController);
        return mealViewHolder;
    }
}
