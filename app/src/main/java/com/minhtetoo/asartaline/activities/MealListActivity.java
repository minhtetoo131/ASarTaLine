package com.minhtetoo.asartaline.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealListActivity extends BaseActivity implements MealItemController {

    @BindView(R.id.rv_meal_list)SmartRecyclerView rvMealList;
    @BindView(R.id.vp_empty_restaurant)EmptyViewPod emptyViewPod;
    @BindView(R.id.tv_afternoon)TextView tvAfternoon;
    @BindView(R.id.tv_time_for_lunch)TextView tvTimeForLunch;
    @BindView(R.id.et_search)EditText etSearch;
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;

    private ASarTaLineModel mModel;

    private MealListAdapter mealListAdapter;
    private List<MealVO> allMeals;
    private SmartScrollListener mSmartScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        ButterKnife.bind(this, this);

        rvMealList.setEmptyView(emptyViewPod);
        rvMealList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mealListAdapter = new MealListAdapter(getApplicationContext(), this);
        rvMealList.setAdapter(mealListAdapter);
        allMeals = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ForceRefresh();
            }
        });
        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                onJobListEndReach();
            }
        });
        rvMealList.addOnScrollListener(mSmartScrollListener);

        mModel = ViewModelProviders.of(this).get(ASarTaLineModel.class);
        mModel.initDatabase(getApplicationContext());

        showWelcomeMessage();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("testing",charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("testing",charSequence.toString());
                if (charSequence.equals("")){
                    mealListAdapter.setNewData(allMeals);
                }
                //TODO condition  check charsequence
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("testing",editable.toString());

            }
        });

    }

    private void showWelcomeMessage() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            tvAfternoon.setText("Good Morning");
            tvTimeForLunch.setText("It's time for Breakfast");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            tvAfternoon.setText("Good AfterNoon");
            tvTimeForLunch.setText("It's time for Lunch");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            tvAfternoon.setText("Good Evening");
            tvTimeForLunch.setText("It's time for Dinner");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            tvAfternoon.setText("Good Night");
            tvTimeForLunch.setText("It's time for Dinner");
        }
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
                    allMeals.addAll(mealVOS);
                    mealListAdapter.setNewData(mealVOS);
                }
            }
        });
    }
    public void ForceRefresh() {
        mModel.loadMealList().observe(this, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> meals) {
                if (meals != null) {
                    mealListAdapter.setNewData(meals);
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void onJobListEndReach() {
        mModel.loadMoreMeal().observe(this, new Observer<List<MealVO>>() {
            @Override
            public void onChanged(@Nullable List<MealVO> mealNextPage) {
                if (mealNextPage != null && mealNextPage.size() > 0){
                    mealListAdapter.appendNewData(mealNextPage);
                }
            }
        });

    }



    @Override
    public void onTapMeal(MealVO mealVO) {
        startActivity(MealDetailActivity.newIntent(getApplicationContext(), mealVO.getMealId()));
    }

    @OnClick(R.id.btn_search)
    public void onTapSearch(View view){
        final String searchKeyword = etSearch.getText().toString();
        if (!TextUtils.isEmpty(searchKeyword)){
            mModel.searchMealList(searchKeyword,"","",
                    "","","","",
                    "")
                    .observe(this,
                            new Observer<List<MealVO>>() {
                                @Override
                                public void onChanged(@Nullable List<MealVO> searchResponse) {
                                    List<MealVO>  matchMeals = new ArrayList<>();
                                    if (searchResponse != null && searchResponse.size() > 0){
                                        for (int i = 0; i <searchResponse.size(); i++){
                                            if (searchResponse.get(i).getTasteVOList().get(0).getTaste().contains(searchKeyword)){
                                                matchMeals.add(searchResponse.get(i));
                                            }
                                        }
                                    }
                                    mealListAdapter.setNewData(matchMeals);
                                }
                            });
        }
    }
}
