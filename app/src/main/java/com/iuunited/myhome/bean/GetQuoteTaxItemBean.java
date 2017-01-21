package com.iuunited.myhome.bean;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @time 2017/1/19 10:41
 * Created by xundaozhe on 2017/1/19.
 * 估价明细---税项明细
 */

public class GetQuoteTaxItemBean implements Serializable {
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
