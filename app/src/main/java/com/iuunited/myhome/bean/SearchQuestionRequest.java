package com.iuunited.myhome.bean;

import com.iuunited.myhome.Helper.RouteAttr;
import com.iuunited.myhome.entity.BaseEntity;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/29 10:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/29.
 */
@RouteAttr(url = "/Project/QueryQuestionCategory", hostType = RouteAttr.HOST_TYPE.Default)
public class SearchQuestionRequest extends BaseEntity {

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public static class SearchQuestionResponse extends BaseEntity{
        private List<FilteredItems> FilteredItems;

        public List<com.iuunited.myhome.bean.FilteredItems> getFilteredItems() {
            return FilteredItems;
        }

        public void setFilteredItems(List<com.iuunited.myhome.bean.FilteredItems> filteredItems) {
            FilteredItems = filteredItems;
        }
    }
}
