package com.iuunited.myhome.ui.project.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.DetailsItemAdapter;
import com.iuunited.myhome.view.MyListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 15:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class LoocUpDetailsActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private MyListView lv_details_item;
    private DetailsItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_details);
        initView();
        initData();
        initEndDialog();
    }

    private void initEndDialog() {
        ProjectEndDialog projectEndDialog = new ProjectEndDialog();
        projectEndDialog.show(getSupportFragmentManager(),"endDialog");
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        lv_details_item = (MyListView) findViewById(R.id.lv_details_item);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("昵称");
        iv_share.setVisibility(View.GONE);
        setAdapter();
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new DetailsItemAdapter(this);
            lv_details_item.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
        }
    }
}
