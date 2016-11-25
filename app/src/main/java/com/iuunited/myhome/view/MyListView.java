package com.iuunited.myhome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/1 14:29
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 自定义可以与ScrollView嵌套使用的listView
 * Created by xundaozhe on 2016/11/1.
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
