package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.TextUtils;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.iuunited.myhome.util.DefaultShared.getStringValue;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/27 14:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/27.
 */

public class AddCostAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<QuoteItemBean> mDatas;
    private String userType;

    public AddCostAdapter(Context context,List<QuoteItemBean> lists){
        this.mContext = context;
        this.mInflater = mInflater.from(context);
        this.mDatas = lists;
        userType =  DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE,"");
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_add_cost, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = bean.getName();
        if(!TextUtils.isEmpty(name)) {
            holder.tv_quote_name.setText(name);
        }
        double subTotal = bean.getSubTotal();
        if(subTotal!=0.0) {
            holder.tv_quote_price.setText("$"+subTotal);
        }
        if (!TextUtils.isEmpty(userType)) {
            if(userType.equals("1")) {
                holder.tv_state.setVisibility(View.GONE);
            }else{
                int status = bean.getStatus();
                switch (status) {
                    case 0 :
                        holder.tv_state.setText("待客户确认");
                        break;
                    case 1:
                        holder.tv_state.setText("客户已确认");
                        break;
                    case 2:
                        holder.tv_state.setText("客户拒绝");
                        break;
                }
            }
        }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_state;
        private TextView tv_quote_name;
        private TextView tv_quote_price;

        public ViewHolder(View view){
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_quote_name = (TextView) view.findViewById(R.id.tv_quote_name);
            tv_quote_price = (TextView) view.findViewById(R.id.tv_quote_price);

        }
    }
}
