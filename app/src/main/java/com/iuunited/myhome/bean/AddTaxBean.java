package com.iuunited.myhome.bean;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/29 17:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/29.
 */

public class AddTaxBean implements Serializable{
    private String taxName;
    private String taxValue;

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }
}
