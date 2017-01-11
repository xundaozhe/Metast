package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/10 16:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/10.
 */

public class AddProjectEvent {
    public int state;

    public AddProjectEvent(int tag){
        this.state = tag;
    }
}
