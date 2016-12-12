package com.iuunited.myhome.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.iuunited.myhome.Helper.AlbumHelper;
import com.iuunited.myhome.R;
import com.iuunited.myhome.adapter.ImageBucketAdapter;
import com.iuunited.myhome.bean.ImageBucket;

import java.io.Serializable;
import java.util.List;

import static com.iuunited.myhome.base.BaseFragmentActivity.setColor;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 17:13
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class TestPicActivity extends Activity {
    List<ImageBucket> dataList;//把相册列表集存起来
    GridView gridView;
    ImageBucketAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static final String EXTRA_IMAGE_Name = "imagelist_name";
    public static Bitmap bimap;//添加图片按钮的“十字图标”
    private TextView cnacel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        ReleaseProjectActivity.addActivity(TestPicActivity.this);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        initData();
        initView();
        //点击取消事件
        cnacel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ReleaseProjectActivity.clearOneAcitvity(TestPicActivity.this);
                TestPicActivity.this.finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // /**
        // * 这里，我们假设已经从网络或者本地解析好了数据，所以直接在这里模拟了10个实体类，直接装进列表中
        // */
        // dataList = new ArrayList<Entity>();
        // for(int i=-0;i<10;i++){
        // Entity entity = new Entity(R.drawable.picture, false);
        // dataList.add(entity);
        // }
        dataList = helper.getImagesBucketList(false);
        //添加图片按钮的“十字图标”
        bimap= BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
        cnacel=(TextView)super.findViewById(R.id.cancel);
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageBucketAdapter(TestPicActivity.this, dataList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
                 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
                 */
                // if(dataList.get(position).isSelected()){
                // dataList.get(position).setSelected(false);
                // }else{
                // dataList.get(position).setSelected(true);
                // }
                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                // adapter.notifyDataSetChanged();
                Intent intent = new Intent(TestPicActivity.this,
                        ImageGridActivity.class);
                ImageBucket imageBucket=dataList.get(position);
                intent.putExtra(TestPicActivity.EXTRA_IMAGE_LIST,
                        (Serializable) imageBucket.imageList);
                intent.putExtra(TestPicActivity.EXTRA_IMAGE_Name,
                        imageBucket.bucketName);
                startActivity(intent);
                //finish();
            }

        });
    }


}

