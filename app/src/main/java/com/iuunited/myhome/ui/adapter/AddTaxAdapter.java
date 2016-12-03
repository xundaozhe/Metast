package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AddTaxBean;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/29 17:10
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/29.
 */

public class AddTaxAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<AddTaxBean> mDatas;

    public AddTaxAdapter(Context context, List<AddTaxBean> lists) {
        mContext = context;
        mInflater = mInflater.from(mContext);
        mDatas = lists;
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
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_add_tax, null);
            holder = new ViewHolder();
            holder.tv_tax_name = (TextView) convertView.findViewById(R.id.tv_tax_name);
            holder.tv_tax_value = (TextView) convertView.findViewById(R.id.tv_tax_value);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AddTaxBean addTaxBean = mDatas.get(position);
        holder.tv_tax_name.setText(addTaxBean.getTaxName()+"-");
        holder.tv_tax_value.setText(addTaxBean.getTaxValue());
        return convertView;
    }

    public class ViewHolder{
        private TextView tv_tax_name;
        private TextView tv_tax_value;
    }
}
