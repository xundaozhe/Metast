package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.AddCostAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.MyListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/27 14:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/27.
 */

public class AddCostActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private MyListView lv_details_item;
    private AddCostAdapter mAdapter;

    private Button btn_item_depot;
    private Button btn_new_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        btn_item_depot = (Button)findViewById(R.id.btn_item_depot);
        btn_new_item = (Button) findViewById(R.id.btn_new_item);

        lv_details_item = (MyListView) findViewById(R.id.lv_details_item);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("昵称");
        iv_share.setVisibility(View.GONE);

        btn_item_depot.setOnClickListener(this);
        btn_new_item.setOnClickListener(this);
        setAdapter();
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new AddCostAdapter(this);
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
            case R.id.btn_item_depot:
                IntentUtil.startActivity(this,ProItemDepotActivity.class);
                break;
            case R.id.btn_new_item:
                IntentUtil.startActivity(this,EditNewProjectActivity.class);
                break;
        }
    }
}
