package com.iuunited.myhome.entity;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/28 19:56
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/28.
 */
public abstract class BaseEntity implements Serializable {
    private int OperateCode;
    //返回码
    public int getOperateCode() {
        return OperateCode;
    }

    public void setOperateCode(int operateCode) {
        OperateCode = operateCode;
    }
}

//@RouteAttr(url = "/security/signon")
//public class LoginRequest extends BaseEntity {
//
//    public static class LoginResponse extends BaseEntity {
//    }
//}


