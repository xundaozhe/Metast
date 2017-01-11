package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/2 15:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/2.
 */
@RouteAttr(url = "/Project/CreateProject", hostType = RouteAttr.HOST_TYPE.Default)
public class CreateProjectRequest extends BaseEntity {

    private String Name;
    private String Telephone;
    private String Address;
    private String PostCode;
    private double Latitude;
    private double Longitude;
    private String Description;
    private List<AnswerBean> Answers;
    private List<String> Urls;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<AnswerBean> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        Answers = answers;
    }

    public List<String> getUrls() {
        return Urls;
    }

    public void setUrls(List<String> urls) {
        Urls = urls;
    }

    public static class CreateProjectResponse extends BaseEntity {
        private int ProjectId;

        public int getProjectId() {
            return ProjectId;
        }

        public void setProjectId(int projectId) {
            ProjectId = projectId;
        }
    }
}
