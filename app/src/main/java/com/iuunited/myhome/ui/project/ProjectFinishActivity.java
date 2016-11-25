package com.iuunited.myhome.ui.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 9:54
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/29.
 */
public class ProjectFinishActivity extends BaseFragmentActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    
    private LinearLayout ll_finish_grade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_finish);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        ll_finish_grade = (LinearLayout)findViewById(R.id.ll_finish_grade);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("工程");
        ll_finish_grade.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",1);
                intent.setClass(ProjectFinishActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_finish_grade:
                IntentUtil.startActivity(this,ProjectGradeActivity.class);
                break;
        }
    }
}
