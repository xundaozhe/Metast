package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 12:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class QuestionNameEvent {
    public int id;
    public String name;

    public QuestionNameEvent(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
