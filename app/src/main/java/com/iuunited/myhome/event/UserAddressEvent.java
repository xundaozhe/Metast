package com.iuunited.myhome.event;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/6 12:00
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 定位成功后想上级页面返回用户的地址
 * Created by xundaozhe on 2016/12/6.
 */
public class UserAddressEvent {
    public String address;
//    public String markerAddress;

    public UserAddressEvent(String address) {
        this.address = address;
//        this.markerAddress = markerAddress;
    }
}
