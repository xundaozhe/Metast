package com.iuunited.myhome.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.MyGridView;
import com.iuunited.myhome.view.ProjectCancelDialog;

import static com.amap.api.col.v.p;
import static com.iuunited.myhome.util.UIUtils.getResources;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 17:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 首页---装修工工程详情页面
 * Created by xundaozhe on 2016/11/27.
 */

public class ItemProjectDetailsActivity extends BaseFragmentActivity {

    private LinearLayout ll_no_interest;
    private LinearLayout ll_prepare_assess;
    private RelativeLayout iv_back;

    private ProjectCancelDialog mCancelDialog;

    private MyGridView gv_project_details;
    private EditProjectGvAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_project_details);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        ll_no_interest = (LinearLayout)findViewById(R.id.ll_no_interest);
        ll_prepare_assess = (LinearLayout) findViewById(R.id.ll_prepare_assess);
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);

        gv_project_details = (MyGridView)findViewById(R.id.gv_project_details);
    }

    private void initData() {
        ll_no_interest.setOnClickListener(this);
        ll_prepare_assess.setOnClickListener(this);
        iv_back.setOnClickListener(this);
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
            case R.id.ll_no_interest :
                mCancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                    @Override
                    public void cancelClick(int id, Activity activity) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
                                finish();
                                break;
                        }
                    }
                },"拒绝工程","您确定要拒绝这个工程吗?");
                mCancelDialog.show();
                break;
            case R.id.ll_prepare_assess:
                //跳转到估价页面
                IntentUtil.startActivity(this,ProjectAssessActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
