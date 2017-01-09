package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.project.professional.AddNewProjectActivity;
import com.iuunited.myhome.ui.project.professional.EditNewProjectActivity;
import com.iuunited.myhome.ui.project.professional.ProItemDepotActivity;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 18:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 专修商工程估价页面
 * Created by xundaozhe on 2016/11/27.
 */

public class ProjectAssessActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    

    private Button btn_item_depot;
    private Button btn_new_item;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_assess);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        btn_item_depot = (Button)findViewById(R.id.btn_item_depot);
        btn_new_item = (Button) findViewById(R.id.btn_new_item);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("昵称");
        iv_share.setVisibility(View.GONE);
        btn_item_depot.setOnClickListener(this);
        btn_new_item.setOnClickListener(this);
    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
//                IntentUtil.startActivity(this, AddNewProjectActivity.class);
            case R.id.btn_item_depot:
                IntentUtil.startActivity(this,ProItemDepotActivity.class);
                break;
            case R.id.btn_new_item:
                IntentUtil.startActivity(this,EditNewProjectActivity.class);
                break;
        }
    }
}
