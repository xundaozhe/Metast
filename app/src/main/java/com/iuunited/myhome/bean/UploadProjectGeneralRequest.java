package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/11 12:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/11.
 */
@RouteAttr(url = "/Project/UpdateProjectGeneral", hostType = RouteAttr.HOST_TYPE.Default)
public class UploadProjectGeneralRequest extends BaseEntity {
    private int Id;
    private String Name;
    private String Address;
    private String PostCode;
    private String Telephone;

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
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

    public static class UploadProjectGeneralResponse extends BaseEntity{
        private boolean IsSuccessful;

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
