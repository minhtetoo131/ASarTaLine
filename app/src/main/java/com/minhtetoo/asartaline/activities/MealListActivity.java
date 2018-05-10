package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.minhtetoo.asartaline.R;
import com.minhtetoo.asartaline.adapters.MealListAdapter;
import com.minhtetoo.asartaline.components.EmptyViewPod;
import com.minhtetoo.asartaline.components.rvset.SmartRecyclerView;
import com.minhtetoo.asartaline.components.rvset.SmartScrollListener;
import com.minhtetoo.asartaline.controllers.MealItemController;
import com.minhtetoo.asartaline.data.model.ASarTaLineModel;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.mvp.presenters.MealListPresenter;
import com.minhtetoo.asartaline.mvp.views.MealListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MealListActivity extends BaseActivity implements MealListView {

    @BindView(R.id.rv_meal_list)SmartRecyclerView rvMealList;
    @BindView(R.id.vp_empty_restaurant)EmptyViewPod emptyViewPod;
    @BindView(R.id.tv_afternoon)TextView tvAfternoon;
    @BindView(R.id.tv_time_for_lunch)TextView tvTimeForLunch;
    @BindView(R.id.et_search)EditText etSearch;
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;

    private ASarTaLineModel mModel;

    private MealListAdapter mealListAdapter;
    private List<MealVO> mAllMeals;
    private SmartScrollListener mSmartScrollListener;

    private MealListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        ButterKnife.bind(this, this);

        mModel = ViewModelProviders.of(this).get(ASarTaLineModel.class);
        mModel.initDatabase(getApplicationContext());
        mPresenter = new MealListPresenter(this, mModel);
        mPresenter.onCreate(this);

        rvMealList.setEmptyView(emptyViewPod);
        rvMealList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mealListAdapter = new MealListAdapter(getApplicationContext(), mPresenter);
        rvMealList.setAdapter(mealListAdapter);
        mAllMeals = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh();
            }
        });
        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                mPresenter.onJobListEndReach();
            }
        });
        rvMealList.addOnScrollListener(mSmartScrollListener);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    mealListAdapter.setNewData(mAllMeals);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


    @OnClick(R.id.btn_search)
    public void onTapSearch(View view){
        final String searchKeyword = etSearch.getText().toString();
        if (!TextUtils.isEmpty(searchKeyword)){
           mPresenter.onTapSearch(searchKeyword);
        }
    }

    @Override
    public void displayMeals(List<MealVO> meals) {
            mealListAdapter.setNewData(meals);
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayError() {
        Snackbar.make(rvMealList, "No data can't be loaded, plz try again!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setAllMeals(List<MealVO> allMeals) {
        mAllMeals.clear();
        mAllMeals.addAll(allMeals);
    }

    @Override
    public void showWelcomeMessage(String morning, String timeForWhat) {
        tvAfternoon.setText(morning);
        tvTimeForLunch.setText(timeForWhat);
    }

    @Override
    public void navigateToDetails(MealVO mealVO) {
        startActivity(MealDetailActivity.newIntent(getApplicationContext(), mealVO.getMealId()));
    }
}
