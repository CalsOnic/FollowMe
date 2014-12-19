package com.idyll.follow.common.exception;

import android.util.Log;

import com.idyll.follow.common.logging.PrintLog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class MainExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String getStackTrace(Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        Throwable cause = throwable;

        while (null != cause) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        return result.toString();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        PrintLog.error(getStackTrace(throwable));
        System.exit(0);
    }


}
