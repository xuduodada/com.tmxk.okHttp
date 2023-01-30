package com.tmxk.android-base.utils;

import android.util.Log;

public class LogUtil {
    private static final String TAG= "com.tmxk.android-base ";
    private static final int ELEMENT = 5;
    public static void setIsShowLog(boolean isShowLog) {
        mIsShowLog = isShowLog;
    }
    private static boolean mIsShowLog = true;

    public static void logV() {
        outputLog(Log.VERBOSE,TAG,null,null);
    }

    //Object... args :不定参数,放参数列表最后
    public static void logV(String message, Object... args) {
        outputLog(Log.VERBOSE,TAG,maybeFormat(message, args),null);
    }

    public static void logV(Throwable throwable, String message, Object... args) {
        outputLog(Log.VERBOSE,TAG,maybeFormat(message, args),throwable);
    }

    public static void logD() {
        outputLog(Log.DEBUG, TAG, null, null);
    }

    public static void logD(String message, Object... args) {
        outputLog(Log.DEBUG, TAG, maybeFormat(message, args),null);
    }

    public static void logD(Throwable throwable,String message, Object... args) {
        outputLog(Log.DEBUG, TAG, maybeFormat(message, args),throwable);
    }

    public static void logI() {
        outputLog(Log.INFO, TAG, null, null);
    }

    public static void logI(String message,Object... args) {
        outputLog(Log.INFO, TAG, maybeFormat(message, args), null);
    }

    public static void logI(Throwable throwable,String message,Object... args) {
        outputLog(Log.INFO, TAG, maybeFormat(message, args), throwable);
    }

    public static void logW() {
        outputLog(Log.WARN, TAG, null, null);
    }

    public static void logW(String message, Object... args) {
        outputLog(Log.WARN, TAG, maybeFormat(message, args), null);
    }

    public static void logW(Throwable throwable,String message, Object... args) {
        outputLog(Log.WARN, TAG, maybeFormat(message, args), throwable);
    }

    public static void logE() {
        outputLog(Log.ERROR, TAG, null, null);
    }

    public static void logE(String message, Object args) {
        outputLog(Log.ERROR, TAG, maybeFormat(message, args), null);
    }

    public static void logE(Throwable throwable, String message, Object... args) {
        outputLog(Log.ERROR, TAG, maybeFormat(message, args), throwable);
    }
    private static void outputLog(int logType, String tag, String message, Throwable throwable) {
        if(!mIsShowLog)
            return;
        if(message == null)
            message = getStackTraceInfo();
        else
            message = getStackTraceInfo() + " " + message;

        switch (logType){
            case Log.VERBOSE:
                if (throwable == null) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, throwable);
                }
                break;
            case Log.DEBUG:
                if (throwable == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, throwable);
                }
                break;
            case Log.INFO:
                if (throwable == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, throwable);
                }
                break;
            case Log.WARN:
                if (throwable == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, throwable);
                }
                break;
            case Log.ERROR:
                if (throwable == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, throwable);
                }
                break;
            default:
                break;
        }
    }
    /*StackTraceElement表示StackTrace(堆栈轨迹,存放的就是方法调用栈的信息，每次调用一个方法会产生一个方法栈，
    当前方法调用另外一个方法时会使用栈将当前方法的现场信息保存在此方法栈当中，获取这个栈就可以得到方法调用的详细过程)
    中的一个方法对象，通过这个对象可以获取调用栈当中的调用过程信息，包括方法的类名、方法名、文件名以及调用的行数。
     */
    static String getStackTraceInfo() {
        /*
        获取StackTraceElement对象有如下两种方法:
        StackTraceElement elements[] = Thread.currentThread().getStackTrace();
        StackTraceElement stackElements[] = new Throwable().getStackTrace();

        StackTraceElement[]对象在Java比较有用的是第2个，在Android中比较有用的是第3个,因为在Android中多了一个Dalvik的调用，
        所以实际Java中的第3位向后移动了一个位置，变成第3位。这个比较有用的StackTraceElement保存了实际调用该方法的方法信息。*/
        //此处对StackTraceElement对象封装了三层，所以取elements[5]
        StackTraceElement element= Thread.currentThread().getStackTrace()[ELEMENT];
        //类名前面会带包名
        String fullClassName = element.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
        String methodName = element.getMethodName();
        int LineNumber = element.getLineNumber();

        String str = "<<"+ className + "#" + methodName + ":" + LineNumber + ">>";
        return str;
    }

    public static void printTrackInfo() {
        StringBuilder sb = new StringBuilder("");
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                sb.append(stackElements[i].toString() + "\n");
            }
        }
        outputLog(Log.DEBUG, TAG, sb.toString(), null);
    }

    private static String maybeFormat(String message, Object... args) {
        //如果未提供varargs，则将其视为记录字符串而不进行格式化的请求。
        return args.length == 0 ? message:String.format(message, args);
    }
}
