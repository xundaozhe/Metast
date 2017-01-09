package com.iuunited.myhome.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.login.LoginActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.util.UIUtils;
import com.iuunited.myhome.view.ProjectCancelDialog;

import cn.jpush.android.api.JPushInterface;

import static com.amap.api.col.c.m;
import static com.iuunited.myhome.util.UIUtils.getPackageName;
import static com.iuunited.myhome.util.UIUtils.getResources;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 10:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 应用开启页面
 * Created by xundaozhe on 2016/10/26.
 */
public class StartActivity extends BaseFragmentActivity {

    private ImageView iv_customer;
    private ImageView iv_professional;

    private TextView tv_version;

    private String userName;
    private String password;
    private String sessionId;
    private String userType;

    private ProjectCancelDialog mCancelDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        ActivityCollector.addActivity(this);
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
        tv_version = (TextView)findViewById(R.id.tv_version);
    }

    private void initData() {
        iv_customer.setOnClickListener(this);
        iv_professional.setOnClickListener(this);
        getVersionName(this);

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
                            IntentUtil.startActivity(this, LoginActivity.class);
                        } else {
                            IntentUtil.startActivity(this, MainActivity.class);
                        }
                    } else {
//                        ToastUtils.showShortToast(this, "请选择合适的身份进行操作!");
                        mCancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                            @Override
                            public void cancelClick(int id, Context activity) {
                                switch (id) {
                                    case R.id.dialog_btn_sure:
                                        DefaultShared.putStringValue(getApplicationContext(),"typeMessage","message");
                                        Intent intent = new Intent();
                                        intent.putExtra("mainFragmentId",4);
                                        intent.setClass(StartActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                }
                            }
                        },"退出登录","您当前身份是装修商,如需切换到用户,请前往我的页面点击退出。是否前往我的页面?");
                        mCancelDialog.show();
                    }
                }else{
                    IntentUtil.startActivity(this, LoginActivity.class);
                }
                break;
            case R.id.iv_professional :
                MyApplication.userType = 2;
                if (!(userType.equals("0"))) {
                if(userType.equals(String.valueOf(MyApplication.userType))) {
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName)) {
                        IntentUtil.startActivity(this, LoginActivity.class);
                    } else {
                        IntentUtil.startActivity(this, MainActivity.class);
                    }

                }else{
//                    ToastUtils.showShortToast(this,"请选择合适的身份进行操作!");
                    mCancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                    @Override
                    public void cancelClick(int id, Context activity) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
//                                        finish();
                                DefaultShared.putStringValue(getApplicationContext(),"typeMessage","message");
                                Intent intent = new Intent();
                                intent.putExtra("mainFragmentId",4);
                                intent.setClass(StartActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                },"退出登录","您当前身份是用户,如需切换到装修商,请前往我的页面点击退出。是否前往我的页面?");
                    mCancelDialog.show();
                }
                }else{
                    IntentUtil.startActivity(this, LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    public void getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            if(!TextUtils.isEmpty(versionName)||versionCode!=0) {
                tv_version.setText("v"+versionName);
            }else{
                tv_version.setText("");
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
