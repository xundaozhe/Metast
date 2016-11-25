package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.ui.message.MessageFragment;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/14 15:14
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/14.
 */

public class MessageLvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflate;

    public MessageLvAdapter(Context context){
        this.mContext = context;
        this.mInflate = mInflate.from(context);
    }

    @Override
    public int getCount() {
        return 8;
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
            convertView = mInflate.inflate(R.layout.item_message_lv,null);
        }
        return convertView;
    }
}
