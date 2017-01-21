package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/3 17:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/3.
 */
@RouteAttr(url = "/Project/QueryMyProject", hostType = RouteAttr.HOST_TYPE.Default)
public class QueryMyProjectRequest extends BaseEntity {
    private int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public static class QueryMyProjectResponse extends BaseEntity{
        private List<ProjectInfoBean> Projects;

        private boolean isFirst;

        public boolean getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(boolean first) {
            isFirst = first;
        }

        public List<ProjectInfoBean> getProjects() {
            return Projects;
        }

        public void setProjects(List<ProjectInfoBean> projects) {
            Projects = projects;
        }
    }
}
