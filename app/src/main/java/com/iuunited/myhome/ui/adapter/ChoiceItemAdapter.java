package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.iuunited.myhome.bean.OptionsBean;
import com.iuunited.myhome.view.ChoiceItemView;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/28 15:21
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/28.
 */

public class ChoiceItemAdapter extends BaseAdapter {

    private List<OptionsBean> options;
    private Context mContext;

    public ChoiceItemAdapter(Context context,List<OptionsBean> beans){
        mContext = context;
        options = beans;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true; //getCheckedItemIds()方法要求此处返回为true
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChoiceItemView choiceItemView;
        if(convertView == null) {
            choiceItemView  = new ChoiceItemView(mContext, null);
        }else{
            choiceItemView = (ChoiceItemView) convertView;
        }
        OptionsBean optionsBean = options.get(position);
        String optionText = optionsBean.getOptionText();
        choiceItemView.setName(optionText);
        return choiceItemView;
    }
}
