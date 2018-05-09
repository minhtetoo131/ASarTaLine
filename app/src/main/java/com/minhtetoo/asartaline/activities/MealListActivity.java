package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.adapters.MealListAdapter;
import com.minhtetoo.asartaline.components.EmptyViewPod;
import com.minhtetoo.asartaline.components.rvset.SmartRecyclerView;
import com.minhtetoo.asartaline.controllers.MealItemController;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealListActivity extends BaseActivity implements MealItemController {

    @BindView(R.id.rv_meal_list)
    SmartRecyclerView rvMealList;
    @BindView(R.id.vp_empty_restaurant)
    EmptyViewPod emptyViewPod;

    private ASarTaLineModel mModel;

    private MealListAdapter mealListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        ButterKnife.bind(this, this);

        rvMealList.setEmptyView(emptyViewPod);
        rvMealList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mealListAdapter = new MealListAdapter(getApplicationContext(), this);
        rvMealList.setAdapter(mealListAdapter);

        mModel = ViewModelProviders.of(this).get(ASarTaLineModel.class);
        mModel.initDatabase(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoadingMeals();
    }

    public void startLoadingMeals() {
        mModel.loadMealList().observe(this, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> mealVOS) {
                if (mealVOS != null) {
                    mealListAdapter.setNewData(mealVOS);
                }
            }
        });
    }

    @Override
    public void onTapMeal(MealVO mealVO) {
        startActivity(MealDetailActivity.newIntent(getApplicationContext(), mealVO.getMealId()));
    }
}
