package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/1 10:49
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/1.
 */

public class QuotedPriceActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quoted_price);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        iv_share.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}