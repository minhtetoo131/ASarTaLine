package com.minhtetoo.asartaline.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.minhtetoo.asartaline.data.db.AppDatabase;
import com.minhtetoo.asartaline.data.vos.MealVO;
import com.minhtetoo.asartaline.network.ASarTaLineAPI;
import com.minhtetoo.asartaline.network.responses.GetMealListRespose;
import com.minhtetoo.asartaline.network.responses.GetSearchMealListResponse;
import com.minhtetoo.asartaline.utils.AppConstants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ASarTaLineModel extends ViewModel {
    private ASarTaLineAPI theApi;
    private MutableLiveData<List<MealVO>> mLiveDataMealVOs;
    private MutableLiveData<List<MealVO>> mLiveDataSearchMeals;
    private AppDatabase mAppDatabase;

    public ASarTaLineModel() {
        initASarTaLineAPI();
        mLiveDataMealVOs = new MutableLiveData<>();
        mLiveDataSearchMeals = new MutableLiveData<>();
    }

    private void initASarTaLineAPI() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(ASarTaLineAPI.class);
    }

    public void initDatabase(Context context) {
        mAppDatabase = AppDatabase.getInMemoryDatabase(context);
    }

    public LiveData<List<MealVO>> getMealList() {
        return mAppDatabase.mealDao().getAllMeals();
    }

    public MealVO getMeal(String mealId) {
        return mAppDatabase.mealDao().getMeal(mealId);
    }

    public List<MealVO> getMeals(List<String> mealIds) {
        return mAppDatabase.mealDao().getMealWithIds(mealIds);
    }

    public long insertMeal(MealVO meal) {
        return mAppDatabase.mealDao().insertMeal(meal);
    }

    public LiveData<List<MealVO>> loadMealList() {
        theApi.getMealList(AppConstants.ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetMealListRespose>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetMealListRespose getMealListRespose) {
                        if (getMealListRespose.getMealVOList() != null && getMealListRespose.getMealVOList().size() > 0) {
//                            mLiveDataMealVOs.setValue(getMealListRespose.getMealVOList());
                            long[] insertedJobs = mAppDatabase.mealDao().insertMeals(getMealListRespose.getMealVOList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle no internet connection;
                    }
                });
        return mAppDatabase.mealDao().getAllMeals();
    }

    public LiveData<List<MealVO>> forceRefreshMeals() {
        return loadMealList();
    }

    public MutableLiveData<List<MealVO>> searchMealList(String tasteType,String suited,String minPrice,String maxPrice,String isNear,String currentTownShip,String currentLat,String currentLong) {
        theApi.searchMeal(AppConstants.ACCESS_TOKEN,tasteType,suited,
                minPrice,maxPrice,isNear,currentTownShip
                ,currentLat,currentLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetSearchMealListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(GetSearchMealListResponse searchResponse) {
                        if (searchResponse.getSearchMealList() != null && searchResponse.getSearchMealList().size() > 0) {
                            mLiveDataSearchMeals.setValue(searchResponse.getSearchMealList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle no internet connection;
                        e.toString();
                    }
                });

        return mLiveDataSearchMeals;
    }

    public MutableLiveData<List<MealVO>> loadMoreMeal() {
        //TODO real pagination api call
        return new MutableLiveData<List<MealVO>>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        AppDatabase.destroyInstance();
    }
}
