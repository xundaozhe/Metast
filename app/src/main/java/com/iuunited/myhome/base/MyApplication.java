package com.iuunited.myhome.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.ui.home.GaoDeMapActivity;
import com.iuunited.myhome.util.GDLocationUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 23:01
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/26.
 */
public class MyApplication extends MultiDexApplication {
    private static Context mContext;
    private static long mMainThreadId;
    public static String sessionId;
    public static int userId;
    public static String mobile;
    public static String userLogoUri;
    public static int userType;


    /**
     * @return 获取上下文
     */
    public static Context getContext(){
        return mContext;
    }

    public static long getMainThreadId(){
        return mMainThreadId;
    }

    /**
     * 程序的入口方法,创建应用里面需要用到的一些共有的属性
     */
    @Override
    public void onCreate() {
        super.onCreate();
        /******* 上下文 *********/
        mContext = getApplicationContext();

        /******* 主线程的id *********/
        mMainThreadId = android.os.Process.myTid();
        ServiceClient.initialize(mContext);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        GDLocationUtil.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
