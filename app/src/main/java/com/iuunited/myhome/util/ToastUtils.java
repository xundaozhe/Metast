package com.iuunited.myhome.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/26 22:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/26.
 */
public class ToastUtils {
    public static void showLongToast(Context context , String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context , String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showShortToast(Context context , int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
