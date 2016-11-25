package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/17 16:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册---验证短信验证码实体
 * Created by xundaozhe on 2016/11/17.
 */
@RouteAttr(url = "/Register/VerifyCaptcha", hostType = RouteAttr.HOST_TYPE.Default)
public class RegisterVerifySMSRequest extends BaseEntity {

    private String Captcha;


    public String getCaptcha() {
        return Captcha;
    }

    public void setCaptcha(String captcha) {
        Captcha = captcha;
    }

    public static class RegisterVerifySMSResponse extends BaseEntity{
        private int OperateCode;
        private boolean IsCaptchaMeeted;

        public boolean getIsCaptchaMeeted() {
            return IsCaptchaMeeted;
        }

        public void setIsCaptchaMeeted(boolean captchaMeeted) {
            IsCaptchaMeeted = captchaMeeted;
        }

        public int getOperateCode() {
            return OperateCode;
        }

        public void setOperateCode(int operateCode) {
            OperateCode = operateCode;
        }
    }
}
