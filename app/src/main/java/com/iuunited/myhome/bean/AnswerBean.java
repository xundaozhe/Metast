package com.iuunited.myhome.bean;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/2 15:33
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/2.
 */

public class AnswerBean implements Serializable{
    private int Question_Id;
    private int QuestionType;
    private String QuestionName;
    private String OptionIds;
    private String OptionCodes;
    private String OptionTexts;
    private String OptionValues;

    public String getOptionTexts() {
        return OptionTexts;
    }

    public void setOptionTexts(String optionTexts) {
        OptionTexts = optionTexts;
    }

    public int getQuestion_Id() {
        return Question_Id;
    }

    public void setQuestion_Id(int question_Id) {
        Question_Id = question_Id;
    }

    public int getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(int questionType) {
        QuestionType = questionType;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public String getOptionIds() {
        return OptionIds;
    }

    public void setOptionIds(String optionIds) {
        OptionIds = optionIds;
    }

    public String getOptionCodes() {
        return OptionCodes;
    }

    public void setOptionCodes(String optionCodes) {
        OptionCodes = optionCodes;
    }

    public String getOptionValues() {
        return OptionValues;
    }

    public void setOptionValues(String optionValues) {
        OptionValues = optionValues;
    }
}
