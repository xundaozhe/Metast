package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iuunited.myhome.R;
import com.iuunited.myhome.util.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 15:38
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class EditProjectGvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public EditProjectGvAdapter(Context context, List<String> urls) {
        mContext = context;
        mInflater = mInflater.from(context);
        this.mDatas = urls;
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
        String url = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_edit_project_gv, null);
            holder.item_grida_image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(url)) {
            Glide.with(mContext).load(url).into(holder.item_grida_image);

        }
        return convertView;
    }

    static class ViewHolder{
        private ImageView item_grida_image;
    }
}
