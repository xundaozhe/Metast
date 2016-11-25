package com.iuunited.myhome.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.BitmapCache;
import com.iuunited.myhome.bean.ImageBucket;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 17:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 用户附加图片适配器
 * Created by xundaozhe on 2016/10/27.
 */
public class ImageBucketAdapter extends BaseAdapter {
    final String TAG = getClass().getSimpleName();

    Activity act;
    /**
     * 相册集列表
     */
    List<ImageBucket> dataList;
    BitmapCache cache;
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals((String) imageView.getTag())) {
                    ((ImageView) imageView).setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "callback, bmp not match");
                }
            } else {
                Log.e(TAG, "callback, bmp null");
            }
        }
    };

    public ImageBucketAdapter(Activity act, List<ImageBucket> list) {
        this.act = act;
        dataList = list;
        cache = new BitmapCache();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView name;
        private TextView count;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = View.inflate(act, R.layout.item_image_bucket, null);
            holder.iv = (ImageView) arg1.findViewById(R.id.image);
//			holder.selected = (ImageView) arg1.findViewById(R.id.isselected);
            holder.name = (TextView) arg1.findViewById(R.id.name);
            holder.count = (TextView) arg1.findViewById(R.id.count);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        ImageBucket item = dataList.get(arg0);
        holder.count.setText("共" + item.count+"张图片");
        holder.name.setText(item.bucketName);
//		holder.selected.setVisibility(View.GONE);
        if (item.imageList != null && item.imageList.size() > 0) {
            String thumbPath = item.imageList.get(0).thumbnailPath;
            String sourcePath = item.imageList.get(0).imagePath;
            holder.iv.setTag(sourcePath);
            cache.displayBmp(holder.iv, thumbPath, sourcePath, callback);
        } else {
            holder.iv.setImageBitmap(null);
            Log.e(TAG, "no images in bucket " + item.bucketName);
        }
        return arg1;
    }

}