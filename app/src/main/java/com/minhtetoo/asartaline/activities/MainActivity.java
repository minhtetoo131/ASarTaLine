package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.adapters.MealListAdapter;
import com.minhtetoo.asartaline.components.EmptyViewPod;
import com.minhtetoo.asartaline.components.rvset.SmartRecyclerView;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_restaurant_list)SmartRecyclerView rvRestaurant;
    @BindView(R.id.vp_empty_restaurant)EmptyViewPod emptyViewPod;

    private ASarTaLineModel mModel;

    private MealListAdapter mealListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this,this);

        rvRestaurant.setEmptyView(emptyViewPod);
        rvRestaurant.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        mealListAdapter = new MealListAdapter(getApplicationContext());
        rvRestaurant.setAdapter(mealListAdapter);

        mModel = ViewModelProviders.of(this).get(ASarTaLineModel.class);
//        mModel.initDatabase(getApplicationContext());
//        mJob = mModel.getJob(mJobId);
//        Log.d(OneTimeJobsApp.TAG, "Job Id" + mJobId);
//        if (mJob != null) {
//            Log.d(OneTimeJobsApp.TAG, "Job" + mJob);
//            bindData(mJob);
//        }
    }
}
