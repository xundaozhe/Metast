package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/13 16:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/13.
 */

public class InitProjectFailureEvent {
    public int state;

    public InitProjectFailureEvent(int state) {
        this.state = state;
    }
}
