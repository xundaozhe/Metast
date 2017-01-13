package com.iuunited.myhome.event;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/12 10:47
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/12.
 */

public class UploadProjectUrlEvent {
    public String description;

    public List<String> imageUrls;

    public UploadProjectUrlEvent(String desc,List<String> urls){
        this.description = desc;
        this.imageUrls = urls;
    }
}
