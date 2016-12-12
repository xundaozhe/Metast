package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.MyGridView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 14:48
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修商---主页---工程---进行中详情页
 * Created by xundaozhe on 2016/11/27.
 */

public class ProUnderWayDetailsActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private LinearLayout ll_add_cost;

    private MyGridView gv_project_details;
    private EditProjectGvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_underway_details);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        ll_add_cost = (LinearLayout)findViewById(R.id.ll_add_cost);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);

        gv_project_details = (MyGridView)findViewById(R.id.gv_project_details);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        ll_add_cost.setOnClickListener(this);

        setAdapter();
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new EditProjectGvAdapter(this);
            gv_project_details.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.ll_add_cost:
                IntentUtil.startActivity(this,ProProjectDetailsActivity.class);
                break;
        }
    }
}
