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
