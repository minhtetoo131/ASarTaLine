package com.minhtetoo.asartaline.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.data.vos.RestaurantVO;
import com.minhtetoo.asartaline.viewholders.RestaurantViewHolder;

public class RestaurantListAdapter extends BaseRecyclerAdapter<RestaurantViewHolder,RestaurantVO> {

    public RestaurantListAdapter(Context context) {
        super(context);
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View restaurantItemView = mLayoutInflater.inflate(R.layout.view_item_food_list,parent,false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(restaurantItemView);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {

    }
}
