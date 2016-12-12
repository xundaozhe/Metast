package com.iuunited.myhome.ui.login;

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
 * @time 2016/11/26 18:28
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/26.
 */

public class ReactTwoActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private ImageView iv_share;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react_one);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back.setOnClickListener(this);
        iv_share.setVisibility(View.GONE);
        tv_title.setText("MyHome隐私协议");
    }

    private void initData() {

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
