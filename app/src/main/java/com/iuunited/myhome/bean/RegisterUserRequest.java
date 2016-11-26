package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/24 10:40
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册创建用户的实体
 * Created by xundaozhe on 2016/11/24.
 */
@RouteAttr(url = "/Register/RegisterUser", hostType = RouteAttr.HOST_TYPE.Default)
public class RegisterUserRequest extends BaseEntity {
    private String Mobile;
    private String Password;
    private String LogoKey;
    private String Email;
    private int UserType;

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLogoKey() {
        return LogoKey;
    }

    public void setLogoKey(String logoKey) {
        LogoKey = logoKey;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public static class RegisterUserResponse extends BaseEntity{
        private int UserId;
        private String Mobile;
        private String LogoUri;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getLogoUri() {
            return LogoUri;
        }

        public void setLogoUri(String logoUri) {
            LogoUri = logoUri;
        }
    }
}
