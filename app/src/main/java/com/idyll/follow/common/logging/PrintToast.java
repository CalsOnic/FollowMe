package com.idyll.follow.common.logging;

import android.content.Context;
import android.widget.Toast;

public class PrintToast {
    private static final int TOAST_DURATION =  Toast.LENGTH_SHORT;

    private PrintToast() {
        throw new AssertionError();
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, TOAST_DURATION).show();
    }

    public static void toast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
