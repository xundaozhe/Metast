package com.iuunited.myhome.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/26 10:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/26.
 */

public class DefaultShared {
    public final static String SETTING = "MyHome";

    /**
     * �洢���(Long)
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putLongValue(Context context, String key, long value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.putLong(key, value);
        sp.commit();
    }

    /**
     * �洢���(Int)
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putIntValue(Context context, String key, int value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.putInt(key, value);
        sp.commit();
    }

    /**
     * �洢���(String)
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putStringValue(Context context, String key, String value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.putString(key, value);
        sp.commit();
    }

    /**
     * �洢���(boolean)
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBooleanValue(Context context, String key,
                                       boolean value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.putBoolean(key, value);
        sp.commit();
    }

    /**
     * ȡ����ݣ�Long��
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLongValue(Context context, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);
        long value = sp.getLong(key, defValue);
        return value;
    }

    /**
     * ȡ����ݣ�int��
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * ȡ����ݣ�boolean��
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBooleanValue(Context context, String key,
                                          boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    /**
     * ȡ����ݣ�String��
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getStringValue(Context context, String key,
                                        String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    /**
     * ����������
     *
     * @param context
     * @return
     */
    public static void clear(Context context) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.clear();
        sp.commit();
    }

    /**
     * ����������
     *
     * @param context
     * @param key
     * @return
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)
                .edit();
        sp.remove(key);
        sp.commit();
    }
}
