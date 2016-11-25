package com.iuunited.myhome.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/24 16:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/24.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
