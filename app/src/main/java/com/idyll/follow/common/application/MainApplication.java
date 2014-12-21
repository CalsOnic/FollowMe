package com.idyll.follow.common.application;

import android.app.Application;
import com.idyll.follow.common.exception.MainExceptionHandler;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        Thread.setDefaultUncaughtExceptionHandler(new MainExceptionHandler());
    }
}
