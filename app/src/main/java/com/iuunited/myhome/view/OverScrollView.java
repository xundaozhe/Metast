package com.iuunited.myhome.view;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/4 12:14
 * @des  仿Ios有弹性的ScrollView
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/4.
 */

public class OverScrollView extends ScrollView {
    /**
     * 动画执行时间
     */
    private static final int ANIM_DURING = 300;

    /**
     * 最大可拖拽距离
     */
    private static final int MAX_SPAN = 500;

    private View mContentView;
    private TimeInterpolator mInterpolator;
    /**
     * 是否可上下拉
     */
    private boolean canPullDown;
    private boolean canPullUp;
    private float mDownY;
    private boolean isMove;

    public OverScrollView(Context context)
    {
        this(context, null);
    }

    public OverScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        if (getChildCount() > 0)
        {
            mContentView = getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                float deltaY = moveY - mDownY;
                if (deltaY > 10 && canPullDown)
                {
                    if (deltaY >= MAX_SPAN)
                    {
                        deltaY = MAX_SPAN;
                    }
                    mContentView.setTranslationY(deltaY);
                    isMove = true;
                }
                if (deltaY < -10 && canPullUp)
                {
                    if (deltaY <= -MAX_SPAN)
                    {
                        deltaY = -MAX_SPAN;
                    }
                    mContentView.setTranslationY(deltaY);
                    isMove = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                /**
                 * 利用动画回到原位置
                 */
                if (isMove)
                {
                    scrollToOrginial();
                }
                isMove = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 利用属性动画滚回原位置
     */
    private void scrollToOrginial()
    {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mContentView, "translationY", mContentView.getTranslationY(), 0);
        anim.setDuration(ANIM_DURING);
        if (mInterpolator != null)
        {
            anim.setInterpolator(mInterpolator);
        }
        anim.start();
    }

    /**
     * 设置动画差值器
     */
    public void setInterpolator(TimeInterpolator interpolator)
    {
        this.mInterpolator = interpolator;
    }

    private boolean isCanPullDown()
    {
        return getScrollY() == 0 || mContentView.getHeight() < getHeight() + getScrollY();
    }

    private boolean isCanPullUp()
    {
        return mContentView.getHeight() <= getHeight() + getScrollY();
    }
}
