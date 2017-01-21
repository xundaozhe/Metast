package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.HomeNewlyBean;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.util.GlideUtils;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.view.RoundedImageView;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 0:02
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class HomeNewlyAdpter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
//    private List<HomeNewlyBean> mData;
    private List<ProjectInfoBean> mData;

    public HomeNewlyAdpter(Context context,List<ProjectInfoBean> data){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProjectInfoBean bean = mData.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_home_newly, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_project_name = (TextView) convertView.findViewById(R.id.tv_project_name);
            viewHolder.tv_describe = (TextView) convertView.findViewById(R.id.tv_describe);
            viewHolder.iv_project_img = (RoundedImageView) convertView.findViewById(R.id.iv_project_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String projectName = bean.getName();
        if(!TextUtils.isEmpty(projectName)) {
            viewHolder.tv_project_name.setText(projectName);
        }
        String description = bean.getDescription();
        if(!TextUtils.isEmpty(description)) {
            viewHolder.tv_describe.setText(description);
        }
        List<String> urls = bean.getUrls();
        if(urls!=null) {
            if(urls.size()>0) {
                String imageUrl = urls.get(0);
//                Glide.with(mContext).load(imageUrl).into(viewHolder.iv_project_img);
                GlideUtils.setImage(mContext,imageUrl,R.drawable.damen,R.drawable.damen,viewHolder.iv_project_img);
            }
        }
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_project_name;
        public TextView tv_describe;
        public RoundedImageView iv_project_img;
    }
}
