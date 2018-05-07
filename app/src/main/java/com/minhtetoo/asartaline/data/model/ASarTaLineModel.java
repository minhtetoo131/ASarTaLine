package com.minhtetoo.asartaline.data.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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

    public ASarTaLineModel() {
        initASarTaLineAPI();
        mLiveDataMealVOs = new MutableLiveData<>();
        loadMealList();
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

    public void loadMealList() {
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
                            mLiveDataMealVOs.setValue(getMealListRespose.getMealVOList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle no internet connection;
                    }
                });
    }

    public void searchMealList(String tasteType,String suited,String minPrice,String maxPrice,String isNear,String currentTownShip,String currentLat,String currentLong) {
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
                            //TODO search
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO handle no internet connection;
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        AppDatabase.destroyInstance();
    }
}
