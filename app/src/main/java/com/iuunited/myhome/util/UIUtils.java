package com.iuunited.myhome.util;

import android.content.Context;
import android.content.res.Resources;

import com.iuunited.myhome.base.MyApplication;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/26 22:11
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/26.
 */
public class UIUtils {

    /**得到上下文*/
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**得到Resource对象*/
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**得到string.xml中的字符*/
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**得到string.xml中的字符数组*/
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**得到color.xml中的颜色值*/
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**得到应用程序的包名*/
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**得到主线程的id*/
    public static long getMainThreadId() {
        return MyApplication.getMainThreadId();
    }

}
