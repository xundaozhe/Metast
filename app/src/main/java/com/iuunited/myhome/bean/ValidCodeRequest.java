package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 13:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */
@RouteAttr(url = "/Project/CheckPostCode", hostType = RouteAttr.HOST_TYPE.Default)
public class ValidCodeRequest extends BaseEntity {
    private String PostCode;

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public static class ValidCodeResponse extends BaseEntity{
        private boolean IsValid;

        public boolean getIsValid() {
            return IsValid;
        }

        public void setIsValid(boolean valid) {
            IsValid = valid;
        }
    }
}
