package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/4 15:01
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/4.
 */
@RouteAttr(url = "/Project/QueryProjectDetails", hostType = RouteAttr.HOST_TYPE.Default)
public class QueryProjectDetailsRequest extends BaseEntity {

    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public static class QueryProjectDetailsResponse extends BaseEntity {
        private int Id;
        private int CategoryId;
        private String Name;
        private String Telephone;
        private String Address;
        private String PostCode;
        private double Latitude;
        private double Longitude;
        private String Description;

        private int Status;
        private List<AnswerBean> Answers;
        private List<String> Urls;

        public int getCategoryId() {
            return CategoryId;
        }

        public void setCategoryId(int categoryId) {
            CategoryId = categoryId;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
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
    }
}
