package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/11 15:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/11.
 */

public class UploadGeneralEvent {
    public String uploadTelePhone;
    public String uploadAddress;
    public String uploadZipCode;

    public UploadGeneralEvent(String telePhone ,String address,String zipCode){
        this.uploadTelePhone = telePhone;
        this.uploadAddress = address;
        this.uploadZipCode = zipCode;
//
    }
}
