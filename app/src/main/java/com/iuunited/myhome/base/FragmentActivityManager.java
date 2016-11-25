package com.iuunited.myhome.base;

import android.app.Activity;

import java.util.Stack;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/26 22:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/26.
 */
public class FragmentActivityManager {

    /**
     * 页面的堆
     */
    private static Stack<Activity> sActivityStack = null;

    /**
     * 单例
     */
    private static FragmentActivityManager sActivityManager;

    private FragmentActivityManager() {
    }

    // *************************************************************************
    /**
     * (初始化activity管理)
     */
    // *************************************************************************
    public static void initActivityManager() {
        if (sActivityManager == null) {
            sActivityManager = new FragmentActivityManager();
        }
    }

    // *************************************************************************
    /**
     * (获取activity管理器的单例)
     *
     * @return
     */
    // *************************************************************************
    public static synchronized FragmentActivityManager getActivityManager() {
        if (sActivityManager == null) {
            sActivityManager = new FragmentActivityManager();
        }
        return sActivityManager;
    }

    // *************************************************************************
    /**
     * (activity出栈)
     *
     * @param activity
     */
    // *************************************************************************
    public void popActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
        }
    }

    // *************************************************************************
    /**
     * (结束activity)
     *
     * @param activity
     */
    // *************************************************************************
    public void endActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            sActivityStack.remove(activity);
            activity = null;
        }
    }

    // *************************************************************************
    /**
     * (获得当前显示的activity)
     *
     * @return
     */
    // *************************************************************************
    public Activity currentActivity() {
        Activity activity = null;
        if (!sActivityStack.empty())
            activity = sActivityStack.lastElement();
        return activity;
    }

    // *************************************************************************
    /**
     * (activity入栈)
     *
     * @param activity
     */
    // *************************************************************************
    public void pushActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<Activity>();
        }
        sActivityStack.add(activity);
    }

    // *************************************************************************
    /**
     * (将所有非指定activity类的activity移除堆栈)
     *
     * @param cls
     */
    // *************************************************************************
    public void popAllActivityExceptOne(Class<Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    // *************************************************************************
    /**
     * (将所有非指定activity类的activity结束并将指定activity移除堆栈)
     *
     * @param cls
     */
    // *************************************************************************
    public void finishAllActivityExceptOne(Class<Activity> cls) {
        while (!sActivityStack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                endActivity(activity);
            }
        }
    }

    // *************************************************************************
    /**
     * (结束所有的activity)
     */
    // *************************************************************************
    public void finishAllActivity() {
        while (!sActivityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }

    // *************************************************************************
    /**
     * (获取activity堆栈的深）
     *
     * @return
     */
    // *************************************************************************
    public int getActivitystacksize() {
        if (sActivityStack != null) {
            return sActivityStack.size();
        } else {
            return 0;
        }
    }
}
