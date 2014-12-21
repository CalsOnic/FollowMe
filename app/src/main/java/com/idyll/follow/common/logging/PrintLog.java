package com.idyll.follow.common.logging;

import android.util.Log;

public class PrintLog {
    private static boolean DEBUG = true;
    private static final String tag = "log";

    private PrintLog() {
        throw new AssertionError();
    }

    public static void error(String tag, String message) {
        if (DEBUG) {Log.e(tag, message);}
    }

    public static void error(String message) {
        if (DEBUG) {Log.e(tag, message);}
    }
}
