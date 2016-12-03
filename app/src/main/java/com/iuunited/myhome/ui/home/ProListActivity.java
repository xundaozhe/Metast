package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.ProjectNewAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 19:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修人员选择工作界面
 * Created by xundaozhe on 2016/11/27.
 */
public class ProListActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener {

    private FlexiListView flv_project_new;
    private ProjectNewAdapter mAdapter;
    private RelativeLayout iv_back;
    private TextView tv_map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_list);
        initView();
        initData();
    }

    private void initView() {
        flv_project_new = (FlexiListView) findViewById(R.id.flv_project_new);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_map = (TextView) findViewById(R.id.tv_map);
    }

    private void initData() {
        if(mAdapter == null) {
            mAdapter = new ProjectNewAdapter(this);
            flv_project_new.setAdapter(mAdapter);
        }
        flv_project_new.setOnItemClickListener(this);
        iv_back.setOnClickListener(this);
        tv_map.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.tv_map:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentUtil.startActivity(this,ItemProjectDetailsActivity.class);
    }
}
