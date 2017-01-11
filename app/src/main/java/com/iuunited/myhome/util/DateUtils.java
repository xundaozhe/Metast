package com.iuunited.myhome.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/10 10:57
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 时间戳转换工具类
 * Created by xundaozhe on 2017/1/10.
 */

public class DateUtils {

    private static SimpleDateFormat sf = null;

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getCurrentDate(){
        Date date = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    /**
     * 将时间戳转换为字符串
     * @param time
     * @return
     */
    public static String getDateToString(long time){
        Date date = new Date(time*1000L);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

    public static long getStringToDate(String time){
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
