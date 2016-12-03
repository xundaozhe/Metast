package com.iuunited.myhome.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.mine.SettingActivity;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 18:38
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 工程---全部---用户详情页
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectDetailsActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private LinearLayout ll_quoted_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        ll_quoted_price = (LinearLayout)findViewById(R.id.ll_quoted_price);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("工程");
        ll_quoted_price.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",1);
                intent.setClass(ProjectDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_quoted_price:
                IntentUtil.startActivity(this,QuotedPriceActivity.class);
                break;
        }
    }
}
