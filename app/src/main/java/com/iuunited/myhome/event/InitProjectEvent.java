package com.iuunited.myhome.event;

import com.iuunited.myhome.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/10 15:28
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/10.
 */

public class InitProjectEvent {
    public ProjectInfoBean mDatas;

    public int states;

    public InitProjectEvent(ProjectInfoBean lists,int state){
        this.mDatas = lists;
        this.states = state;
    }
}
