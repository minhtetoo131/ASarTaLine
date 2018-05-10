package com.minhtetoo.asartaline.dagger;

import com.minhtetoo.asartaline.ASarTaLineApp;
import com.minhtetoo.asartaline.activities.MealDetailActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class})
@Singleton
public interface ASTLAppComponent {
    void inject(ASarTaLineApp app);

    void inject(MealDetailActivity mealDetailActivity);
}
