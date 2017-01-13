package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/12 17:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/12.
 */
@RouteAttr(url = "/Project/UpdateProjectAnswer", hostType = RouteAttr.HOST_TYPE.Default)
public class UpdateProjectAnswerRequest extends BaseEntity {
    private int Id;
    private List<AnswerBean> Answers;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<AnswerBean> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        Answers = answers;
    }

    public static class UpdateProjectAnswerResponse extends BaseEntity{
        private boolean IsSuccessful;

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
