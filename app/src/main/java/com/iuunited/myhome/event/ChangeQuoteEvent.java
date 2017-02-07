package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @time 2017/1/24 16:32
 * Created by xundaozhe on 2017/1/24.
 */

public class ChangeQuoteEvent {
    public int projectId;

    public ChangeQuoteEvent(int id){
        this.projectId = id;
    }
}
