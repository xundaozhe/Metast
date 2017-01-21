package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.AddTaxBean;
import com.mapbox.services.commons.models.Position;

import java.util.List;

import static com.iuunited.myhome.R.id.tv_tax_edit;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/27 10:40
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/27.
 */

public class TaxAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ICallBack mCallBack;
    private List<AddTaxBean> mDatas;

    public interface ICallBack{
        void click(View v);
        void getPosition(int position);
    }
    public TaxAdapter(Context context,ICallBack callBack,List<AddTaxBean> lists){
        this.mContext = context;
        this.mInflater = mInflater.from(context);
        this.mCallBack = callBack;
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
        AddTaxBean bean = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tax_adapter,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_tax_name.setText(bean.getTaxName());
        holder.tv_tax_value.setText(bean.getTaxRate() + "%");
        holder.tv_tax_edit.setOnClickListener(new MyListen(position));
        return convertView;
    }

    class ViewHolder{
        private TextView tv_tax_name;
        private TextView tv_tax_value;
        private TextView tv_tax_edit;

        public ViewHolder(View view){
            tv_tax_name = (TextView) view.findViewById(R.id.tv_tax_name);
            tv_tax_value = (TextView) view.findViewById(R.id.tv_tax_value);
            tv_tax_edit = (TextView) view.findViewById(R.id.tv_tax_edit);

        }
    }


    private class MyListen implements View.OnClickListener{

        int position;

        public MyListen(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mCallBack.getPosition(position);
            mCallBack.click(v);
        }
    }
}
