package com.cem.powerqualityanalyser.tool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class log {
    public static boolean enableLog = true;

    public log() {
    }

    public static String getFileLineMethod() {
        StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
        StringBuffer toStringBuffer = (new StringBuffer("[")).append(traceElement.getFileName()).append(" | ").append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("()").append("]");
        return toStringBuffer.toString();
    }

    public static String file() {
        StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
        return traceElement.getFileName();
    }

    public static String func() {
        StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
        return traceElement.getMethodName();
    }

    public static String line() {
        StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
        return String.valueOf(traceElement.getLineNumber());
    }

    public static String curTime() {
        Date now = new Date(0L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }

    public static void v(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            Log.v(TAG, msg);
        }

    }

    public static void vPosition(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            String position = getLogPosition(traceElement);
            Log.v(TAG, msg + position);
        }

    }

    public static void d(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            Log.d(TAG, msg);
        }

    }

    public static void dPosition(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            String position = getLogPosition(traceElement);
            Log.d(TAG, msg + position);
        }

    }

    public static void i(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            Log.i(TAG, msg);
        }

    }

    public static void iPosition(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            String position = getLogPosition(traceElement);
            Log.i(TAG, msg + position);
        }

    }

    private static String getTag(StackTraceElement traceElement) {
        StringBuffer toStringBuffer = (new StringBuffer("[")).append(traceElement.getFileName()).append(" | ").append(traceElement.getMethodName()).append("() | ").append(traceElement.getLineNumber()).append(" ]");
        String TAG = toStringBuffer.toString();
        return TAG;
    }

    private static String getLogPosition(StackTraceElement traceElement) {
        String logPosition = " [at " + traceElement.getClassName() + "." + traceElement.getMethodName() + "(" + traceElement.getFileName() + ":" + traceElement.getLineNumber() + ")]";
        return logPosition;
    }

    public static void w(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            Log.w(TAG, msg);
        }

    }

    public static void wPosition(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            String position = getLogPosition(traceElement);
            Log.w(TAG, msg + position);
        }

    }

    public static void e(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            Log.e(TAG, msg);
        }

    }

    public static void ePosition(String msg) {
        if (enableLog) {
            StackTraceElement traceElement = (new Exception()).getStackTrace()[1];
            String TAG = getTag(traceElement);
            String position = getLogPosition(traceElement);
            Log.e(TAG, msg + position);
        }

    }
}
