package com.iuunited.myhome.bean;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/16 15:56
 * @des 加税的实体类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 * Created by xundaozhe on 2017/1/16.
 */

public class QuoteTaxItemBean implements Serializable{
    private int Id;
    private String Name;
    private double TaxRate;
    private double TaxAmount;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(double taxRate) {
        TaxRate = taxRate;
    }

    public double getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        TaxAmount = taxAmount;
    }
}
