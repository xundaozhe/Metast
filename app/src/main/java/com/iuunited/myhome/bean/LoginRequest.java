package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/24 11:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/24.
 */
@RouteAttr(url = "/Account/SignOn", hostType = RouteAttr.HOST_TYPE.Default)
public class LoginRequest extends BaseEntity {
    private String Mobile;
    private String Password;
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

    public static class LoginResponse extends BaseEntity {
        private String SessionId;
        private boolean IsSuccessful;
        private int UserType;
        private int UserID;

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int userType) {
            UserType = userType;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String sessionId) {
            SessionId = sessionId;
        }

        public boolean getIsSuccessful() {
            return IsSuccessful;
        }

        public void setIsSuccessful(boolean successful) {
            IsSuccessful = successful;
        }
    }
}
