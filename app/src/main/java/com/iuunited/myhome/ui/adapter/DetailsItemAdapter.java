package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.util.TextUtils;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 15:42
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class DetailsItemAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<QuoteItemBean> mDatas;

    public DetailsItemAdapter(Context context,List<QuoteItemBean> lists){
        this.mContext = context;
        mInflater = mInflater.from(context);
        this.mDatas = lists;
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
        QuoteItemBean bean = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_details, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String name = bean.getName();
        if(!TextUtils.isEmpty(name)) {
            holder.tv_quote_name.setText(name);
        }
        double subTotal = bean.getSubTotal();
        if(subTotal!=0.0) {
            holder.tv_price.setText("$"+subTotal);
        }
        int status = bean.getStatus();
       switch (status) {
           case 0 :
               holder.tv_states.setText("(待接受)");
               break;
           case 1:
               holder.tv_states.setText("");
               break;
           case 2:
               int my_location_ring = R.color.light_black;
               holder.tv_states.setTextColor(my_location_ring);
               holder.tv_states.setText("(已拒绝)");
               break;
       }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_states;
        private TextView tv_quote_name;
        private TextView tv_price;

        public ViewHolder(View view){
            tv_states = (TextView) view.findViewById(R.id.tv_states);
            tv_quote_name = (TextView) view.findViewById(R.id.tv_quote_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
        }
    }
}
