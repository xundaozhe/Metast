package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.BadgeBean;
import com.iuunited.myhome.view.smartimage.SmartImage;
import com.iuunited.myhome.view.smartimage.SmartImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 11:08
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BadgeBean> mDatas;

    public BadgeAdapter(Context context,List<BadgeBean> datas){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_horizontal_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mTvTitle = (TextView) view.findViewById(R.id.tv_image_title);
        viewHolder.mSmartIv = (SmartImageView) view.findViewById(R.id.iv_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BadgeBean badgeBean = mDatas.get(position);
        if(badgeBean!=null) {
            holder.mTvTitle.setText(badgeBean.getTitle().replace("\\n", "\n"));
            holder.mSmartIv.setImageResource(badgeBean.getImageId());
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if(convertView == null) {
//            convertView = mInflater.inflate(R.layout.item_horizontal_list,null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//        if(mDatas!=null) {
//            BadgeBean badgeBean = mDatas.get(position);
//            holder.mSmartIv.setImageResource(badgeBean.getImageId());
////            Picasso.with(mContext).load(badgeBean.getImageId()).into(holder.mSmartIv);
//
//            holder.mTvTitle.setText(badgeBean.getTitle().replace("\\n","\n"));
//        }
//        return convertView;
//    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private SmartImageView mSmartIv;
        private TextView mTvTitle;


        public ViewHolder(View itemView) {
            super(itemView);
        }

//        ViewHolder(View view){
//            mSmartIv = (SmartImageView) view.findViewById(R.id.iv_image);
//            mTvTitle = (TextView) view.findViewById(R.id.tv_image_title);
//        }
    }
}
