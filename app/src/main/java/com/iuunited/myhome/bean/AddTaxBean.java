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
    private int Id;
    private String taxName;
    private String taxRate;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }
}
