package com.idyll.follow.common.aop.exception.handler;

import android.app.Activity;
import android.content.Context;
import android.test.UiThreadTest;

public interface ExceptionHandler {

    public void handle(Context context, Throwable throwable);
    public void handle(Throwable throwable);

    /**
     * Activity 생명주기 method에서 던져진 에러 처리
     * 에러타입 throable에 해당하는 사용자 message 선택(ErrorMessageFinder와 같은 별도 클래스 만들기를 권장)
     */
    public void handleActivityException(Activity activity, Throwable throwable);

    /**
     * Event listener method에서 던져진 에러 처리
     */
    public void handleListenerException(Throwable throwable);

    /**
     * Background 작업 method에서 던져진 에러 처리
     */
    public void handleBackgroundException(Throwable throwable);

    /**
     * 에러정보를 로그에 기록한다
     * Exception 타입에 따라 에러정보를 간략히 또는 상세히 로깅하거나 다른 레벨에 로깅
     */
    public void doLogging(Context context, Throwable throwable, String message);

    /**
     * 사용자에게 적절한 Error messsage를 보여준다
     * Exception.getMessage()와 같은 테크니컬한 내용을 보여주지 말 것
     */
    @UiThreadTest
    public void doMessage(Context context, Throwable throwable, String message);

    /**
     * 에러정보를 서버로 전송한다
     * 에러 정보 및 발생 환경(디바이스 정보 등)을 서버로 전송
     */
    public void doReport(Context context, Throwable throwable, String message);
}
