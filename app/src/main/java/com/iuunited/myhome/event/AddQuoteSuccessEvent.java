package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @time 2017/1/19 10:08
 * Created by xundaozhe on 2017/1/19.
 */

public class AddQuoteSuccessEvent {
    public int state;

    public AddQuoteSuccessEvent(int state) {
        this.state = state;
    }
}
