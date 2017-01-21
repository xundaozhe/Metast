package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @time 2017/1/20 0:58
 * Created by xundaozhe on 2017/1/20.
 * 接受估价
 */
@RouteAttr(url = "/Project/AcceptProjectQuote", hostType = RouteAttr.HOST_TYPE.Default)
public class AcceptProjectQuoteRequest extends BaseEntity {
    private int ProjectId;

    public int getProjectId() {
        return ProjectId;
    }

    public void setProjectId(int projectId) {
        ProjectId = projectId;
    }

    public static class AcceptProjectQuoteResponse extends BaseEntity{
        private boolean IsSuccessful;

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
