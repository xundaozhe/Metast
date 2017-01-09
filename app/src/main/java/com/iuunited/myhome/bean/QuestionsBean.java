package com.iuunited.myhome.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 14:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class QuestionsBean implements Serializable{
    private int Id;
    private int QuestionCategory_Id;
    private String QuestionName;
    private int QuestionType;
    private int Seq;
    private List<OptionsBean> Options;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(int questionType) {
        QuestionType = questionType;
    }

    public List<OptionsBean> getOptions() {
        return Options;
    }

    public void setOptions(List<OptionsBean> options) {
        Options = options;
    }

    public int getQuestionCategory_Id() {
        return QuestionCategory_Id;
    }

    public void setQuestionCategory_Id(int questionCategory_Id) {
        QuestionCategory_Id = questionCategory_Id;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }
}
