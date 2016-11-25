package com.iuunited.myhome.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

import java.lang.ref.WeakReference;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/26 21:50
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/26.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {

    /********
     * 进行耗时操作时加载页面的dialog
     ********/
    protected LoadingDialog mLoadingDialog;
    protected Handler mUiHandler = new UiHandler(this) {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (getActivityReference() != null && getActivityReference().get() != null) {
                getActivityReference().get().handleUiMessage(msg);
            }
        }
    };
    private boolean isShowing;
    private boolean background = false;
    private ActivityManager mActivityManager;
    private PowerManager powerManager;
    private String mPackageName;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        FragmentActivityManager.getActivityManager().pushActivity(this);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FragmentActivityManager.getActivityManager().endActivity(this);
        ActivityCollector.removeActivity(this);
        mUiHandler.removeMessages(5000);//清除5秒内MessageQueu中的message
    }

    /**
     * 处理更新UI任务
     *
     * @param msg
     */
    protected void handleUiMessage(Message msg) {
    }

    /**
     * 发送UI更新操作
     *
     * @param msg
     */
    protected void sendUiMessage(Message msg) {
        mUiHandler.sendMessage(msg);
    }

    protected void sendUiMessageDelayed(Message msg, long delayMillis) {
        mUiHandler.sendMessageDelayed(msg, delayMillis);
    }

    /**
     * 发送UI更新操作
     *
     * @param what
     */
    protected void sendEmptyUiMessage(int what) {
        mUiHandler.sendEmptyMessage(what);
    }

    protected void sendEmptyUiMessageDelayed(int what, long delayMillis) {
        mUiHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    /**
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(BaseFragmentActivity.this, msg);
            }
        });
    }

    /**
     * 显示通知
     *
     * @param strResId 字符串资源id
     */
    public void showToast(final int strResId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLongToast(BaseFragmentActivity.this, getResources()
                        .getString(strResId));
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        isShowing = true;
        if (background) {
            background = false;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        isShowing = false;
        mActivityManager = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        mPackageName = getPackageName();
        powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(Context context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * @param it
     * @return void
     * @Title: startAnimationActivity
     * @Description: 开始动画跳转
     */
    public void startAnimationActivity(Intent it) {
        startActivity(it);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    /**
     * @param it
     * @param requestCode
     * @return void
     * @Title: startAnimationActivityForResult
     * @Description: 开始动画跳转
     */
    public void startAnimationActivityForResult(Intent it, int requestCode) {
        startActivityForResult(it, requestCode);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    /**
     * @return void
     * @Title: finishAnimationActivity
     * @Description: 动画方式结束页面
     */
    public void finishAnimationActivity() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    private static class UiHandler extends Handler {
        private final WeakReference<BaseFragmentActivity> mActivityReference;

        public UiHandler(BaseFragmentActivity activity) {
            mActivityReference = new WeakReference<BaseFragmentActivity>(
                    activity);
        }

        public WeakReference<BaseFragmentActivity> getActivityReference() {
            return mActivityReference;
        }
    }

}
