package com.iuunited.myhome.event;

import com.iuunited.myhome.bean.AddTaxBean;

import static android.R.attr.value;

/**
 * @author xundaozhe
 * @time 2017/1/17 16:41
 * Created by xundaozhe on 2017/1/17.
 */

public class AddTaxEvent {
    public AddTaxBean bean;
    public int type;

    public AddTaxEvent(AddTaxBean data, int state) {
        this.bean = data;
        this.type = state;
    }
}
