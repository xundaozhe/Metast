package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @time 2017/1/20 14:56
 * Created by xundaozhe on 2017/1/20.
 */
@RouteAttr(url = "/Project/ChangeProjectQuoteItems", hostType = RouteAttr.HOST_TYPE.Default)
public class ChangeProjectQuoteRequest extends BaseEntity {

    private int Id;
    private int ProjectId;
    private double SubTotal;
    private double DisCount;
    private double TaxTotal;
    private double Total;
    private String Description;
    private List<QuoteItemBean> Items;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public double getDisCount() {
        return DisCount;
    }

    public void setDisCount(double disCount) {
        DisCount = disCount;
    }

    public double getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        TaxTotal = taxTotal;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<QuoteItemBean> getItems() {
        return Items;
    }

    public void setItems(List<QuoteItemBean> items) {
        Items = items;
    }

    public static class ChangeProjectQuoteResponse extends BaseEntity{
        private boolean IsSuccessful;

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
