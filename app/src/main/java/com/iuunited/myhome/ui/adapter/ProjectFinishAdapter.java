package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.iuunited.myhome.R;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 9:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/29.
 */
public class ProjectFinishAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public ProjectFinishAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 3;
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
        if(convertView == null) {
            View view = mInflater.inflate(R.layout.item_home_newly,null);
            convertView = view;
        }
        return convertView;
    }
}
