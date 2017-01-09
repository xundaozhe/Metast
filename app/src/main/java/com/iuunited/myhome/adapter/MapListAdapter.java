package com.iuunited.myhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.MapPoiBean;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/22 18:40
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/22.
 */

public class MapListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MapPoiBean> mDatas;

    public MapListAdapter(Context context,List<MapPoiBean> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = list;
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
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_map_poi, null);
            holder = new ViewHolder();
            holder.mAddressTitle = (TextView) convertView.findViewById(R.id.tv_address_title);
            holder.mAddressDetails = (TextView) convertView.findViewById(R.id.tv_address_details);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MapPoiBean mapPoiBean = mDatas.get(position);
        holder.mAddressTitle.setText(mapPoiBean.getAddress());
        holder.mAddressDetails.setText(mapPoiBean.getPlaceName());
        return convertView;
    }

    static class ViewHolder{
        private TextView mAddressTitle;
        private TextView mAddressDetails;

    }
}
