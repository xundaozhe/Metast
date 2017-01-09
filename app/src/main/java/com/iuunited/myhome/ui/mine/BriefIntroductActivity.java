package com.iuunited.myhome.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.TextUtils;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 14:55
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 用户简介
 * Created by xundaozhe on 2016/10/28.
 */
public class BriefIntroductActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private TextView tv_edit;
    private String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_briefintroduct);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);

        tv_edit = (TextView)findViewById(R.id.tv_edit);
    }

    private void initData() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,0+"");
        iv_back.setOnClickListener(this);
        tv_title.setText("简介");
        iv_share.setVisibility(View.GONE);

        if(!TextUtils.isEmpty(userType)) {
            if(!userType.equals("0")) {
                if(userType.equals("2")) {
                    tv_edit.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",4);
                intent.setClass(BriefIntroductActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
