package com.iuunited.myhome.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.bean.RegisterUserRequest;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.RegisterSuccessDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 15:01
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册设置密码
 * Created by xundaozhe on 2016/10/26.
 */
public class SettingPwdActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private ImageView iv_back;
    private ImageView iv_react_one;
    private ImageView iv_react_two;
    private Button btn_into_main;
    private TextView tv_url_two,tv_url_one;
    private EditText et_user_pwd;

    private boolean isCheckedOne = true;
    private boolean isCheckedTwo = true;
    private RegisterSuccessDialog mDialog;
    private String urlText = "条款和协议";
    private String urlTexts = "隐私协议";
    private String userPwd;

    private String userEmail;
    private String userMobile;

    private TextView tv_title;
    private ImageView iv_share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pwd);
//        mDialog = new RegisterSuccessDialog(this);
        initView();
        initData();
    }

    private void initView() {
        iv_react_one = (ImageView) findViewById(R.id.iv_react_one);
        iv_react_two = (ImageView) findViewById(R.id.iv_react_two);
        btn_into_main = (Button) findViewById(R.id.btn_into_main);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_url_two = (TextView) findViewById(R.id.tv_url_two);
        tv_url_one = (TextView)findViewById(R.id.tv_url_one);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        et_user_pwd = (EditText)findViewById(R.id.et_user_pwd);
    }

    private void initData() {
        userEmail = getIntent().getStringExtra("userEmail");
        userMobile = getIntent().getStringExtra("userMobile");
        iv_react_one.setOnClickListener(this);
        iv_react_two.setOnClickListener(this);
        btn_into_main.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);

        settingUrl();
        settingUrls();
    }

    private void settingUrls() {
        SpannableString spStr = new SpannableString(urlTexts);
        spStr.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);      //设置下划线
                ds.setColor(getResources().getColor(R.color.myHomeBlue));
            }

            @Override
            public void onClick(View widget) {
//                IntentUtil.startActivity(RegisterActivity.this,LoginActivity.class);
                ToastUtils.showLongToast(SettingPwdActivity.this,"暂未添加该功能!");
            }
        }, 0, urlTexts.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_url_two.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv_url_two.append(spStr);
        tv_url_two.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    private void settingUrl() {
        SpannableString spStr = new SpannableString(urlText);
        spStr.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);      //设置下划线
                ds.setColor(getResources().getColor(R.color.myHomeBlue));
            }

            @Override
            public void onClick(View widget) {
//                IntentUtil.startActivity(RegisterActivity.this,LoginActivity.class);
                ToastUtils.showLongToast(SettingPwdActivity.this, "暂未添加该功能!");
            }
        }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_url_one.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv_url_one.append(spStr);
        tv_url_one.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_react_one :
                if(isCheckedOne) {
                    iv_react_one.setImageResource(R.drawable.react_yes);
                    isCheckedOne = false;
                    return;
                }else{
                    iv_react_one.setImageResource(R.drawable.racte);
                    isCheckedOne = true;
                }
                break;
            case R.id.iv_react_two :
                if(isCheckedTwo) {
                    iv_react_two.setImageResource(R.drawable.react_yes);
                    isCheckedTwo = false;
                    return;
                }else{
                    iv_react_two.setImageResource(R.drawable.racte);
                    isCheckedTwo = true;
                }
                break;
            case R.id.btn_into_main :
                userPwd = et_user_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(userPwd)) {
                    ToastUtils.showShortToast(SettingPwdActivity.this, "密码不能为空!");
                }else{
                    registerUser(userPwd);
                }

                break;
            case R.id.iv_back:
                IntentUtil.startActivityAndFinish(this,RegisterEmailActivity.class);
                break;
        }
    }

    /**
     *  在开启一个activity的时候清除前面所有的activity
     *  intent intent = new Intent();
        intent.addFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
     */
    private void registerUser(String userPwd) {
        if(!TextUtils.isEmpty(userPwd)) {
            if(!TextUtils.isEmpty(userMobile)) {
                if (!TextUtils.isEmpty(userEmail)) {
                    RegisterUserRequest request = new RegisterUserRequest();
                    request.setMobile(userMobile);
                    request.setEmail(userEmail);
                    request.setPassword(userPwd);
                    ServiceClient.requestServer(this, "请求中...", request, RegisterUserRequest.RegisterUserResponse.class,
                            new ServiceClient.OnSimpleActionListener<RegisterUserRequest.RegisterUserResponse>() {
                                @Override
                                public void onSuccess(RegisterUserRequest.RegisterUserResponse responseDto) {
                                    if(responseDto.getOperateCode() == 0) {
                                        MyApplication.userId = responseDto.getUserId();
                                        MyApplication.mobile = responseDto.getMobile();
                                                MyApplication.userLogoUri = responseDto.getLogoUri();
                                        ActivityCollector.finishAll();
                                        showSuccessDialog();
                                    }else{
                                        ToastUtils.showShortToast(SettingPwdActivity.this,"网络开小差啦...");
                                    }
                                }

                                @Override
                                public boolean onFailure(String errorMessage) {
                                    ToastUtils.showShortToast(SettingPwdActivity.this,"注册失败,请稍后重试!");
                                    return false;
                                }
                            });
                }
            }
        }
    }

    private void showSuccessDialog(){
        if(mDialog == null) {
            mDialog = new RegisterSuccessDialog(this);
            mDialog.show();
            mDialog.setCheck(this);
        }else{
            mDialog.show();
            mDialog.setCheck(this);
        }
//                mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoadingDialog(String text) {
        
    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showCustomToast(String text) {

    }

    @Override
    public boolean getSuccessful() {
        return false;
    }

    @Override
    public void setSuccessful(boolean isSuccessful) {

    }
}
