package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.mine.MineFragment;
import com.iuunited.myhome.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/1 17:18
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/1.
 */
public class TerritoryLoreAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<HashMap<Integer,String>> mData;
    private HashMap<Integer, String> data;


    public TerritoryLoreAdapter(Context context,List<HashMap<Integer,String>> data){
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_territorylore, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
            data = mData.get(position);
            String text = data.get(position);
            holder.tv_territory.setText(text);
        final ViewHolder finalHolder = holder;
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    HashMap<Integer, String> map = mData.get(position);
                    mData.remove(data);
                    notifyDataSetChanged();

            }
        });
        holder.tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.rl_bg.setBackgroundResource(R.color.check_lore_bg);
                String text = finalHolder.tv_territory.getText().toString();
                finalHolder.tv_territory.setVisibility(View.GONE);
                finalHolder.edit_territory.setVisibility(View.VISIBLE);
                finalHolder.edit_territory.setText(text);
                finalHolder.tv_change.setVisibility(View.GONE);
                finalHolder.tv_ok.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                finalHolder.tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = finalHolder.edit_territory.getText().toString();
                        finalHolder.rl_bg.setBackgroundResource(R.color.textWhite);
                        finalHolder.edit_territory.setVisibility(View.GONE);
                        HashMap<Integer, String> integerStringHashMap = mData.get(position);
                        integerStringHashMap.put(position,text);
                        finalHolder.tv_territory.setText(text);
                        finalHolder.tv_territory.setVisibility(View.VISIBLE);
                        finalHolder.tv_change.setVisibility(View.VISIBLE);
                        finalHolder.tv_ok.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder{
        private TextView tv_territory;
        private RelativeLayout rl_bg;
        private EditText edit_territory;
        private TextView tv_delete;
        private TextView tv_change;
        private TextView tv_ok;

        ViewHolder(View view){
            tv_territory = (TextView) view.findViewById(R.id.tv_territory);
            rl_bg = (RelativeLayout) view.findViewById(R.id.rl_bg);
            edit_territory = (EditText) view.findViewById(R.id.edit_territory);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            tv_change = (TextView) view.findViewById(R.id.tv_change);
            tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        }
    }


}
