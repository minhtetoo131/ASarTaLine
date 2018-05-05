package com.minhtetoo.asartaline.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.adapters.RestaurantListAdapter;
import com.minhtetoo.asartaline.components.EmptyViewPod;
import com.minhtetoo.asartaline.components.rvset.SmartRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_restaurant_list)SmartRecyclerView rvRestaurant;
    @BindView(R.id.vp_empty_restaurant)EmptyViewPod emptyViewPod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this,this);

        rvRestaurant.setEmptyView(emptyViewPod);
        rvRestaurant.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(getApplicationContext());
        rvRestaurant.setAdapter(restaurantListAdapter);


    }
}
