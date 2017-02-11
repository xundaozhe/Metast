package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @time 2017/2/10 15:40
 * Created by xundaozhe on 2017/2/10.
 */
@RouteAttr(url = "/Project/RejectProjectQuoteItems", hostType = RouteAttr.HOST_TYPE.Default)
public class RejectProjectQuoteItemsRequest extends BaseEntity {
    private int ProjectId;
    private List<Integer> ItemIds;

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public List<Integer> getItemIds() {
        return ItemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        ItemIds = itemIds;
    }

    public static class RejectProjectQuoteItemsResponse extends BaseEntity{
        private boolean IsSuccessful;

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
