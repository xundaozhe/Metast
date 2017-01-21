package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/14 15:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/14.
 */
@RouteAttr(url = "/Project/ProjectSearch", hostType = RouteAttr.HOST_TYPE.Default)
public class ProProjectSearchRequest extends BaseEntity {
    private double Latitude;
    private double Longitude;
    /********搜索半径,为0则使用服务器端的默认配置********/
    private double Radius;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getRadius() {
        return Radius;
    }

    public void setRadius(double radius) {
        Radius = radius;
    }

    public static class ProProjectSearchResponse extends BaseEntity{
        private List<ProjectInfoBean> Projects;

        public List<ProjectInfoBean> getProjects() {
            return Projects;
        }

        public void setProjects(List<ProjectInfoBean> projects) {
            Projects = projects;
        }
    }
}
