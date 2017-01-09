package com.iuunited.myhome.event;

import com.iuunited.myhome.bean.QuestionsBean;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 14:59
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class QuestionEvent {
    public List<QuestionsBean> lists;

    public QuestionEvent(List<QuestionsBean> data){
        this.lists = data;
    }
}
