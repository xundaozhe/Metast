package com.iuunited.myhome.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.login.LoginActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;

import static com.amap.api.col.c.m;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 10:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/26.
 */
public class StartActivity extends BaseFragmentActivity {

    private ImageView iv_customer;
    private ImageView iv_professional;

    private String userName;
    private String password;
    private String sessionId;
    private String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();
        setAnimation();
        initData();
    }

    private void setAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(.2f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        iv_customer.setAnimation(alphaAnimation);
        iv_professional.setAnimation(alphaAnimation);
    }

    private void initView() {
        iv_customer = (ImageView) findViewById(R.id.iv_customer);
        iv_professional = (ImageView)findViewById(R.id.iv_professional);
    }

    private void initData() {
        iv_customer.setOnClickListener(this);
        iv_professional.setOnClickListener(this);

        userType = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_USERTYPE, 0 + "");
        userName = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_USERNAME, "");
        password = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_PASSWORD, "");
        sessionId = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_SESSIONID, "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_customer :
                MyApplication.userType = 1;
                if(!(userType.equals("0"))) {
                    if (userType.equals(String.valueOf(MyApplication.userType))) {
                        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName)) {
                            IntentUtil.startActivityAndFinish(this, LoginActivity.class);
                        } else {
                            IntentUtil.startActivityAndFinish(this, MainActivity.class);
                        }
                    } else {
                        ToastUtils.showShortToast(this, "请选择合适的身份进行操作!");
                    }
                }else{
                    IntentUtil.startActivityAndFinish(this, LoginActivity.class);
                }
                break;
            case R.id.iv_professional :
                MyApplication.userType = 2;
                if (!(userType.equals("0"))) {
                if(userType.equals(String.valueOf(MyApplication.userType))) {
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName)) {
                        IntentUtil.startActivityAndFinish(this, LoginActivity.class);
                    } else {
                        IntentUtil.startActivityAndFinish(this, MainActivity.class);
                    }

                }else{
                    ToastUtils.showShortToast(this,"请选择合适的身份进行操作!");
                }
                }else{
                    IntentUtil.startActivityAndFinish(this, LoginActivity.class);
                }
                break;
        }
    }
}
