package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/15 17:51
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册验证手机号请求实体
 * Created by xundaozhe on 2016/11/15.
 */
@RouteAttr(url = "/Register/VerifyMobile", hostType = RouteAttr.HOST_TYPE.Default)
public class RegisterMobileRequest extends BaseEntity {

    private String Mobile;

//    private int OperateCode;
//
//    public int getOperateCode() {
//        return OperateCode;
//    }
//
//    public void setOperateCode(int operateCode) {
//        OperateCode = operateCode;
//    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public static class RegisterMobileResponse extends BaseEntity{
        private String SessionId;
        private int OperateCode;

        public int getOperateCode() {
            return OperateCode;
        }

        public void setOperateCode(int operateCode) {
            OperateCode = operateCode;
        }

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String sessionId) {
            SessionId = sessionId;
        }
    }
}
