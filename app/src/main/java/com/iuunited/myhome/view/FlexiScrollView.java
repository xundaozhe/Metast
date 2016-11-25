package com.iuunited.myhome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 12:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 仿Ios的有弹性的scrollView
 * Created by xundaozhe on 2016/10/29.
 */
public class FlexiScrollView extends ScrollView {
    // ��ʼ������Y�᷽�����
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;
    // �����Ļ���
    private Context mContext;
    // ʵ�ʿ���������Y���ϵľ���
    private int mMaxYOverscrollDistance;

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;

    public FlexiScrollView(Context context) {
        super(context);
        mContext = context;
        initBounceListView();
    }

    public FlexiScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
        mContext = context;
        initBounceListView();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }


    class YScrollDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY)
        {
            if (Math.abs(distanceY) > Math.abs(distanceX))
            {
                return true;
            }
            return false;
        }
    }

    public FlexiScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView() {
        final DisplayMetrics metrics = mContext.getResources()
                .getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // ʵ�ֵı��ʾ��������ﶯ̬�ı���maxOverScrollY��ֵ
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX,
                mMaxYOverscrollDistance, isTouchEvent);
    }

}
