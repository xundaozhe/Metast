package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.EvaluateItenBean;
import com.iuunited.myhome.util.TextUtils;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/24 14:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/24.
 */

public class ProjectEvaluateAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<EvaluateItenBean> mDatas;

    public ProjectEvaluateAdapter(Context context,List<EvaluateItenBean> lists){
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
        EvaluateItenBean bean = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_project_evaluate, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String quoterName = bean.getQuoterName();
        if(!TextUtils.isEmpty(quoterName)) {
            holder.tv_pro_name.setText(quoterName);
        }
        double total = bean.getTotal();
        holder.tv_pro_price.setText(total+"");
        return convertView;
    }

    class ViewHolder{
        private TextView tv_pro_name;
        private ImageView iv_star_one;
        private ImageView iv_star_two;
        private ImageView iv_star_three;
        private ImageView iv_star_four;
        private ImageView iv_star_five;
        private TextView tv_pro_price;

        public ViewHolder(View view){
            tv_pro_name = (TextView) view.findViewById(R.id.tv_pro_name);
            iv_star_one = (ImageView) view.findViewById(R.id.iv_star_one);
            iv_star_two = (ImageView) view.findViewById(R.id.iv_star_two);
            iv_star_three = (ImageView) view.findViewById(R.id.iv_star_three);
            iv_star_four = (ImageView) view.findViewById(R.id.iv_star_four);
            iv_star_five = (ImageView) view.findViewById(R.id.iv_star_five);
            tv_pro_price = (TextView) view.findViewById(R.id.tv_pro_price);
        }
    }
}
