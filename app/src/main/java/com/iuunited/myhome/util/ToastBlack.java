package com.iuunited.myhome.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iuunited.myhome.R;


/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/27 11:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/27.
 */
public class ToastBlack extends Toast {
    private static long sToastTime = 0;
    private static String sToastContent = "";

    public ToastBlack(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public static void showText(Context context, String text, boolean isLong) {
        long curTime = System.currentTimeMillis();
        if (curTime - sToastTime < 2 * 1000 && sToastContent.equals(text)) {
            sToastTime = System.currentTimeMillis();
            return;
        }
        sToastTime = System.currentTimeMillis();
        sToastContent = text;

        Toast result = new Toast(context);
        // 获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.widget_toast_black, null);

        TextView textView = (TextView) layout.findViewById(R.id.tv_title);

        textView.setText(text);

        result.setView(layout);
        result.setGravity(Gravity.BOTTOM, 0, ScreenUtil.dip2px(20));
        if (isLong)
            result.setDuration(Toast.LENGTH_LONG);
        else
            result.setDuration(Toast.LENGTH_SHORT);

        result.show();
    }
}
