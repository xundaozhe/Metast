package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.ProItemDepotAdapter;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 22:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class ProItemDepotActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private ListView lv_pro_item;
    private ProItemDepotAdapter mAdapter;

    private EditCallBack mCallBack;
    private Button btn_item_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_item);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        lv_pro_item = (ListView)findViewById(R.id.lv_pro_item);
        btn_item_edit = (Button)findViewById(R.id.btn_item_edit);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("科目库");
        iv_share.setVisibility(View.GONE);

        mCallBack = new EditCallBack() {
            @Override
            public void click(View v) {

            }
        };
        setAdapter();
        btn_item_edit.setOnClickListener(this);
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new ProItemDepotAdapter(this);
            lv_pro_item.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_item_edit:
                btn_item_edit.setBackgroundResource(R.drawable.btn_red_selector);
                btn_item_edit.setText("删除");
                mAdapter.setVisible(true);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    public interface EditCallBack{
        void click(View v);
    }
}
