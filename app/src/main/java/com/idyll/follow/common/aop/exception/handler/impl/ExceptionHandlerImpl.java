package com.idyll.follow.common.aop.exception.handler.impl;

import android.app.Activity;
import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 에러처리 핸들러(인터페이스와 구현체로 분리해도 됨)
 */
@Singleton
public class ExceptionHandlerImpl {
    @Inject protected Context applicationContext;

    protected void handle(Context context, Throwable throwable) {
        // 에러타입 throwable에 해당하는 사용자 message 선택
        // ErrorMessageFinder와 같은 별도 클래스를 만들기 권장
        String message = "message";

        // Exception 타입에 따라 아래 작업도 선택적으로 수행 가능.


        // 그 외 에러처리 작업 수행
        // 예를들어 Exception 타입이 AuthenticationException과 같은 것이라면
        // 여기서 자동으로 로그인 Activity로 이동하게 할 수 있음
    }

    public void handle(Throwable throwable) {
        handle(applicationContext, throwable);
    }

    /**
     * Activity 생명주기 method에서 던져진 에러 처리
     */
    public void handleActivityException(Activity activity, Throwable throwable) {
        handle(throwable);
        // 여기서 Activity를 종료하는 것도 가능
    }

    /**
     * Event listener method에서 던져진 에러 처리
     */
    public void handleListenerException(Throwable throwable) {
        handle(throwable);
        // 기타 작업
    }

    /**
     * Background 작업 method에서 던져진 에러 처리
     */
    public void handleBackgroundException(Throwable throwable) {
        handle(throwable);
        // 기타 작업
    }

    /**
     * 에러정보를 로그에 기록한다
     */
    protected void doLogging(Context context, Throwable throwable, String message) {
        // 에러정보를 로깅
        // Exception 타이
    }

}
