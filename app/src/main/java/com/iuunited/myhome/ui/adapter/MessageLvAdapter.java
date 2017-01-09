package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.bean.MessagelvBean;
import com.iuunited.myhome.ui.message.MessageFragment;
import com.iuunited.myhome.view.RoundedImageView;

import java.util.List;

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
    private List<MessagelvBean> mDatas;

    public MessageLvAdapter(Context context,List<MessagelvBean> data){
        this.mContext = context;
        this.mInflate = mInflate.from(context);
        this.mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessagelvBean messagelvBean = mDatas.get(position);
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = mInflate.inflate(R.layout.item_message_lv,null);

            holder = new ViewHolder();
            holder.tv_professional_name = (TextView) convertView.findViewById(R.id.tv_professional_name);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.professional_header = (RoundedImageView) convertView.findViewById(R.id.professional_header);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_professional_name.setText(messagelvBean.getName());
        holder.tv_type.setText(messagelvBean.getType());
        holder.professional_header.setImageResource(messagelvBean.getImageId());
        return convertView;
    }

    static class ViewHolder{
        private RoundedImageView professional_header;
        private TextView tv_professional_name;
        private TextView tv_type;
    }
}
