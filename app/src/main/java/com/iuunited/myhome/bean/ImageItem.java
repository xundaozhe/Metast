package com.iuunited.myhome.bean;

import java.io.Serializable;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 17:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 一个用户附加图片对象
 * Created by xundaozhe on 2016/10/27.
 */
public class ImageItem implements Serializable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public boolean isSelected = false;
}