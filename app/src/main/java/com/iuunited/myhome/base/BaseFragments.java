package com.iuunited.myhome.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.iuunited.myhome.util.ToastBlack;

import java.lang.ref.WeakReference;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 22:59
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/26.
 */
public class BaseFragments extends Fragment {

    protected Handler mUiHandler = new UiHandler(this) {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (getFragmentReference() != null
                    && getFragmentReference().get() != null) {
                try {
                    getFragmentReference().get().handleUiMessage(msg);
                } catch (IllegalStateException e) {
                }
            }
        };
    };

    private static class UiHandler extends Handler {
        private final WeakReference<BaseFragments> mFragmentReference;

        public UiHandler(BaseFragments activity) {
            mFragmentReference = new WeakReference<BaseFragments>(activity);
        }

        public WeakReference<BaseFragments> getFragmentReference() {
            return mFragmentReference;
        }
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
     * @param msg
     *            显示的消息
     */
    public void showToast(final String msg) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ToastBlack.showText(getActivity(), msg, false);
                }
            });
        }
    }

    /**
     * 显示通知
     *
     * @param strResId
     *            字符串资源id
     */
    public void showToast(final int strResId) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ToastBlack.showText(getActivity(), getResources()
                            .getString(strResId), false);
                }
            });
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(Context context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // manager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        if (getActivity().getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * 显示软键盘
     */
    protected void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void onReceive(Context context, Intent intent) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mUiHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiHandler.removeCallbacksAndMessages(null);
    }
}
