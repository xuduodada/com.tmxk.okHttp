package com.tmxk.android-base.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public  class PropSetter {
    public static void setProp(String key, String value) {
        String Value = value;
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c,key,value);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key, String defaultValue) {
        String value = defaultValue;
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) get.invoke(c,key,"unknown");
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
