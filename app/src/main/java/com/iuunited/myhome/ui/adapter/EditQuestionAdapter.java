package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.iuunited.myhome.R;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/24 15:26
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/24.
 */

public class EditQuestionAdapter extends BaseAdapter implements View.OnClickListener{

    private Context mContext;
    private LayoutInflater mInflater;
    private CallBack mCallBack;

    public interface CallBack{
        public void click(View v);
    }

    public EditQuestionAdapter(Context context,CallBack callback){
        mContext = context;
        mInflater = mInflater.from(context);
        mCallBack = callback;
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = mInflater.inflate(R.layout.item_edit_questtion,null);
        }
        Button btn_revise_two = (Button) convertView.findViewById(R.id.btn_revise_two);
        if(position == 2) {
            btn_revise_two.setVisibility(View.VISIBLE);
        }
        btn_revise_two.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        mCallBack.click(v);
    }
}
