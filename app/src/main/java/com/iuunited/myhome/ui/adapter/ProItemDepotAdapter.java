package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.ui.project.professional.ProItemDepotActivity;

import static com.iuunited.myhome.R.id.iv_select_delete;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 22:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class ProItemDepotAdapter extends BaseAdapter implements ProItemDepotActivity.EditCallBack{

    private Context mContext;
    private LayoutInflater mInflater;

    private ImageView iv_select_delete;

    private boolean isVisible;

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public ProItemDepotAdapter(Context context){
        this.mContext = context;
        mInflater = mInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
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
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.pro_item_depot,null);
        }
        iv_select_delete = (ImageView) convertView.findViewById(R.id.iv_select_delete);
        if(isVisible) {
            iv_select_delete.setVisibility(View.VISIBLE);
            iv_select_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_select_delete.setImageResource(R.drawable.react_yes);
                }
            });
        }
        return convertView;
    }

    @Override
    public void click(View v) {
        iv_select_delete.setVisibility(View.VISIBLE);
    }
}
