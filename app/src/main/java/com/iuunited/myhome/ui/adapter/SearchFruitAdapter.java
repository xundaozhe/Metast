package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.FilteredItems;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 10:52
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class SearchFruitAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<FilteredItems> mDatas;

    public SearchFruitAdapter(Context context,List<FilteredItems> lists){
        this.mContext = context;
        this.mInflater = mInflater.from(context);
        this.mDatas = lists;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search_fruit, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_question_name = (TextView) convertView.findViewById(R.id.tv_question_name);
        if(mDatas!=null) {
            FilteredItems items = mDatas.get(position);
            if(items!=null) {
                String name = items.getName();
                if(name!=null) {
                    holder.tv_question_name.setText(name);
                }
            }
        }

        return convertView;
    }

    static class ViewHolder{
        private TextView tv_question_name;
    }
}
