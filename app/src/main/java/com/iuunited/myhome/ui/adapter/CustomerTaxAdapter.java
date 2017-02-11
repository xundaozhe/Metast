package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.QuoteTaxItemBean;
import com.iuunited.myhome.util.TextUtils;

import java.util.List;

/**
 * @author xundaozhe
 * @time 2017/2/11 16:09
 * Created by xundaozhe on 2017/2/11.
 */

public class CustomerTaxAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<QuoteTaxItemBean> mDatas;

    public CustomerTaxAdapter(Context context,List<QuoteTaxItemBean> lists){
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
        QuoteTaxItemBean bean = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_customer_tax,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String name = bean.getName();
        if(!TextUtils.isEmpty(name)) {
            holder.tax_name.setText(name);
        }
        double taxRate = bean.getTaxRate();
        holder.tax_value.setText(taxRate*100+"%");
        return convertView;
    }

    class ViewHolder{
        private TextView tax_name;
        private TextView tax_value;
        public ViewHolder(View view){
            tax_name = (TextView) view.findViewById(R.id.tax_name);
            tax_value = (TextView) view.findViewById(R.id.tax_value);
        }
    }
}
