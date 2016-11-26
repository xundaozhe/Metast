package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/25 14:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/25.
 */
@RouteAttr(url = "/QiNiu/UploadToken", hostType = RouteAttr.HOST_TYPE.Default)
public class UpLoadHeadRequest extends BaseEntity{
    private int FileType;

    public int getFileType() {
        return FileType;
    }

    public void setFileType(int fileType) {
        FileType = fileType;
    }

    public static class UpLoadHeadResponse extends BaseEntity{
        private String Token;

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }
    }
}
