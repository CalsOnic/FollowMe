package com.idyll.follow.common.aop.exception.handler.impl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.idyll.follow.common.aop.exception.handler.ExceptionHandler;

/**
 * 에러처리 핸들러(인터페이스와 구현체로 분리해도 됨)
 */
@Singleton
public class TestExceptionHandlerImpl implements ExceptionHandler {
    @Inject protected Context applicationContext;

    @Override
    public void handle(Context context, Throwable throwable) {
        String message = "error";

        // Exception 타입에 따라 아래 작업도 선택적으로 수행 가능
        doLogging(context, throwable, message);

        // 그 외 에러처리 작업 수행
        // 예를들어 Exception 타입이 AuthenticationException과 같은 것이라면
        // 여기서 자동으로 로그인 Activity로 이동하게 할 수 있음
    }

    @Override
    public void handle(Throwable throwable) {
        handle(applicationContext, throwable);
    }

    @Override
    public void handleActivityException(Activity activity, Throwable throwable) {
        handle(activity, throwable);
        // 여기서 Activity를 종료하는 것도 가능
    }

    @Override
    public void handleListenerException(Throwable throwable) {
        handle(throwable);
        // 기타작업
    }

    @Override
    public void handleBackgroundException(Throwable throwable) {
        handle(throwable);
        // 기타 작업
    }

    @Override
    public void doLogging(Context context, Throwable throwable, String message) {
        Log.d("TestException", message);
    }

    @Override
    public void doMessage(Context context, Throwable throwable, String message) {

    }

    @Override
    public void doReport(Context context, Throwable throwable, String message) {

    }
}
