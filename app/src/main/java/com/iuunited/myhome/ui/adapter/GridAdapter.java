package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.util.Bimp;
import com.iuunited.myhome.util.UIUtils;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 16:47
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 用户上传附加图像的适配器
 * Created by xundaozhe on 2016/10/27.
 */
public class GridAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private int selectedPosition = -1;
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public GridAdapter (Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = Bimp.bmp.size();
//        if(count == 9) {
        if(count == 6) {
            return count;
        }else{
            return (count+1);
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_publish_pjthree, parent,false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(position == Bimp.bmp.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                    UIUtils.getResources(), R.drawable.icon_addpic_unfocused));
//            holder.image.setImageResource(R.drawable.icon_addpic_unfocused);
//            if(position == 9) {
            if(position == 6) {
                holder.image.setVisibility(View.GONE);
            }else{
                holder.image.setVisibility(View.VISIBLE);
            }
        }else{
            holder.image.setImageBitmap(Bimp.bmp.get(position));
        }
        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }



    public class ViewHolder{
        private ImageView image;
    }

}