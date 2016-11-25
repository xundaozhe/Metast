package com.iuunited.myhome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/1 16:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/1.
 */
public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyGridView(Context context) {

        super(context);

    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(

                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}