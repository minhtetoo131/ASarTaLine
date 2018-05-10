package com.minhtetoo.asartaline.dagger;

import android.content.Context;

import com.minhtetoo.asartaline.ASarTaLineApp;
import com.minhtetoo.asartaline.mvp.presenters.MealDetailPresenters;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private ASarTaLineApp mApp;

    public AppModule(ASarTaLineApp application) {
        mApp = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    public MealDetailPresenters provideMealDetailPresenter() {
        return new MealDetailPresenters();
    }
}
