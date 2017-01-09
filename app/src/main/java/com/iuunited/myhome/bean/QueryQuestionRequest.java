package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 14:19
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */
@RouteAttr(url = "/Project/QueryQuestions", hostType = RouteAttr.HOST_TYPE.Default)
public class QueryQuestionRequest extends BaseEntity {
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public static class QueryQuestionResponse extends BaseEntity implements Serializable{
        private ArrayList<QuestionsBean> Questions;

        public ArrayList<QuestionsBean> getQuestions() {
            return Questions;
        }

        public void setQuestions(ArrayList<QuestionsBean> questions) {
            Questions = questions;
        }
    }
}
