package com.iuunited.myhome.event;

import com.iuunited.myhome.bean.AnswerBean;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/12 17:50
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/12.
 */

public class UpdateProjectAnswerEvent {
    public  List<AnswerBean> mAnswerBeanList;

    public UpdateProjectAnswerEvent(List<AnswerBean> lists){
        this.mAnswerBeanList = lists;
    }
}
