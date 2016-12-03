package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_assess);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
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
