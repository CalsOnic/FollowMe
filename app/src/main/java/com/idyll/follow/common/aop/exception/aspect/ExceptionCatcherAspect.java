package com.idyll.follow.common.aop.exception.aspect;

import android.app.Activity;

import com.google.inject.Inject;
import com.idyll.follow.common.aop.exception.handler.ExceptionHandler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExceptionCatcherAspect {
    @Inject private ExceptionHandler exceptionHandler;

    /**
     * @IgnoreExceptionHandler annotation이 붙은 method 및 ExceptionHandler 클래스에는 적용하지 않는다
     */
    @Pointcut(" @annotation(my.IgnoreExceptionHandler) || within(my.ExceptionHandler+) ")
    public void ignore() {

    }

    /**
     * Activity 하위 클래스의 주요 LifeCycle method들
     * 정확히 일치하지는 않을 수 있으며, onCreate(), onResume() 등 주요 method만 포함됨
     */
    @Pointcut("within(android.app.Activity+) && " +
                "(execution(void onCreate(..)) || execution(void onStart()) || execution(void onResume()) || execution(void onPause()) || execution(void onStop()) || execution(void onRestart()) || execution(void onDestroy()))")
    public void activityLifeCycleMethods() {

    }

    /* TODO public void fragmentLifeCycleMethods() {} */


    /**
     * Event Listener Method들
     * On*Listener 형태의 리스너를 구현한 클래스의 on* 형태의 method
     * 정확히 일치하지는 않을 수 있음
     */
    @Pointcut(" within(android..On*Listener+) && execution(!private !static * on(..) throws !Exception) && !activityLifeCycleMethods() ")
    public void eventListenerMethods() {

    }

    /**
     * Runnable 또는 Thread 및 하위 클래스의 run method
     */
    @Pointcut(" execution(public void java.lang.Runnable.run()) ")
    public void runnableMethod() {}

    /**
     * Activity 생명주기 method에서 발생한 에러처리
     */
    @Around(" this(activity) && !ignore() && activityLifeCycleMethods() ")
    public Object catchActivityException(final Activity activity, final ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch(final Throwable throwable) {
            exceptionHandler.handleActivityException(activity, throwable);

            // 정책에 따라 에러처리 후 Activity를 종료하거나 말거나
            // activity.finish();
            return null;
        }
    }

    /**
     * EventListener method에서 발생한 에러처리
     */
    @Around(" !ignore() && eventListenerMethods() ")
    public Object catchListenerException(final ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch(final Throwable throwable) {
            exceptionHandler.handleListenerException(throwable);
            return null;
        }
    }

    /**
     * Runnable에서 발생한 에러처리
     */
    @Around(" !ignore() && runnableMethod() ")
    public void catchRunnableException(final ProceedingJoinPoint pjp) {
        try {
            pjp.proceed();
        } catch(final Throwable throwable) {
            exceptionHandler.handleBackgroundException(throwable);
        }
    }

    /**
     * @ApplyExceptionHandler annotation이 붙은 method에서 발생한 에러처리
     */
    @Around(" !ignore() && @annotation(my.ApplyExceptionHandler) ")
    public void catchApplyExceptionHandler(final ProceedingJoinPoint pjp) {
        try {
            pjp.proceed();
        } catch (final Throwable throwable) {
            exceptionHandler.handle(throwable);
        }
    }
}
