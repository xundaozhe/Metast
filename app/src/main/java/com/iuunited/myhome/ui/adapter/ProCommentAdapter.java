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
 * @time 2016/12/8 14:49
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/8.
 */

public class ProCommentAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public ProCommentAdapter(Context context){
        mContext = context;
        mInflater = mInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pro_comment,null);
        }
        return convertView;
    }
}
