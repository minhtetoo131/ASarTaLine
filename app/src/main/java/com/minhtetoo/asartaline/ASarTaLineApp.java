package com.minhtetoo.asartaline;

import android.app.Application;
import android.content.Context;

import com.minhtetoo.asartaline.dagger.ASTLAppComponent;
import com.minhtetoo.asartaline.dagger.AppModule;
import com.minhtetoo.asartaline.dagger.DaggerASTLAppComponent;


import javax.inject.Inject;

public class ASarTaLineApp extends Application {

    public static final String LOG_TAG = "ASarTaLineApp";

    private ASTLAppComponent mASTLAppComponent;

    @Inject
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mASTLAppComponent = initDagger(); //dagger init
        mASTLAppComponent.inject(this); //register consumer
    }


    private ASTLAppComponent initDagger() {
//        return null;
        return DaggerASTLAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }


    public ASTLAppComponent getASTLAppComponent() {
        return mASTLAppComponent;
    }
}
