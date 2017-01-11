package com.iuunited.myhome.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.iuunited.myhome.Helper.AlbumHelper;
import com.iuunited.myhome.R;
import com.iuunited.myhome.adapter.ImageGridAdapter;
import com.iuunited.myhome.bean.ImageItem;
import com.iuunited.myhome.ui.login.RegisterEmailActivity;
import com.iuunited.myhome.util.Bimp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.iuunited.myhome.base.BaseFragmentActivity.setColor;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 17:28
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 附加图片子目录中图片的预览及选择
 * Created by xundaozhe on 2016/10/27.
 */
public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    private TextView title_text=null;
    private TextView cnacel=null;
    // ArrayList<Entity> dataList;
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    Toast.makeText(ImageGridActivity.this, "最多选择9张图片", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ImageGridActivity.this, "最多选择6张图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_grid);
        setColor(this,getResources().getColor(R.color.myHomeBlue));

        ReleaseProjectActivity.addActivity(ImageGridActivity.this);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        Intent intent=getIntent();
        dataList = (List<ImageItem>) intent.getSerializableExtra(
                EXTRA_IMAGE_LIST);
        initView();
        title_text.setText(intent.getStringExtra(TestPicActivity.EXTRA_IMAGE_Name));//每一个相册集的名字
        //点击取消事件
        cnacel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ReleaseProjectActivity.clearOneAcitvity(ImageGridActivity.this);
                ImageGridActivity.this.finish();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext();) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
//					Intent intent = new Intent(ImageGridActivity.this,
//							PublishedActivity.class);
//					startActivity(intent);
//					Bimp.act_bool = false;
                }
                for (int i = 0; i < list.size(); i++) {
//                    if (Bimp.drr.size() < 9) {
                    if (Bimp.drr.size() < 6) {
                        Bimp.drr.add(list.get(i));
                    }
                }
                //finish();
                ReleaseProjectActivity.clearActivity();
            }

        });
    }


    /**
     * 鍒濆鍖杤iew瑙嗗浘
     */
    private void initView() {
        title_text=(TextView)super.findViewById(R.id.title_text);
        bt = (Button) findViewById(R.id.bt);
        cnacel=(TextView)super.findViewById(R.id.cancel);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 鏍规嵁position鍙傛暟锛屽彲浠ヨ幏寰楄窡GridView鐨勫瓙View鐩哥粦瀹氱殑瀹炰綋绫伙紝鐒跺悗鏍规嵁瀹冪殑isSelected鐘舵
                 * �锛� 鏉ュ垽鏂槸鍚︽樉绀洪�涓晥鏋溿� 鑷充簬閫変腑鏁堟灉鐨勮鍒欙紝涓嬮潰閫傞厤鍣ㄧ殑浠ｇ爜涓細鏈夎鏄�
                 */
                // if(dataList.get(position).isSelected()){
                // dataList.get(position).setSelected(false);
                // }else{
                // dataList.get(position).setSelected(true);
                // }
                /**
                 * 閫氱煡閫傞厤鍣紝缁戝畾鐨勬暟鎹彂鐢熶簡鏀瑰彉锛屽簲褰撳埛鏂拌鍥�
                 */
                adapter.notifyDataSetChanged();
            }

        });

    }
}