package com.iuunited.myhome.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author xundaozhe
 * @time 2017/1/19 10:44
 * Created by xundaozhe on 2017/1/19.
 * 估价明细---每一个科目项的明细
 */

public class GetQuoteItemBean implements Serializable {

    private int Id;
    private String Name;
    private double UnitPrice;
    private double Quantity;
    private double SubTotal;
    private String Description;
    private List<GetQuoteTaxItemBean> TaxItems;

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

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<GetQuoteTaxItemBean> getTaxItems() {
        return TaxItems;
    }

    public void setTaxItems(List<GetQuoteTaxItemBean> taxItems) {
        TaxItems = taxItems;
    }
}
