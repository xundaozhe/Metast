package com.iuunited.myhome.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.bean.LoginRequest;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 11:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 登录页面
 * Created by xundaozhe on 2016/10/26.
 */
public class LoginActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private TextView tv_register;
    private Button login_btn;
    private EditText et_username;
    private EditText et_password;

    private String userName;
    private String passWord;

    private boolean isChecked = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void initView() {
        tv_register = (TextView) findViewById(R.id.tv_register);
        login_btn = (Button)findViewById(R.id.login_btn);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
    }

    private void initData() {
        tv_register.setOnClickListener(this);
        login_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register :
                IntentUtil.startActivity(this,RegisterMobileActivity.class);
                break;
            case R.id.login_btn :
//                IntentUtil.startActivityAndFinish(this,MainActivity.class);
                if(isChecked) {
                    isChecked = false;
                    userName = et_username.getText().toString().trim();
                    passWord = et_password.getText().toString().trim();
                    if (TextUtils.isEmpty(userName)) {
                        ToastUtils.showShortToast(LoginActivity.this,"账号不能为空!");
                        isChecked = true;
                        break;
                    }
                    if(TextUtils.isEmpty(passWord)) {
                        ToastUtils.showShortToast(LoginActivity.this, "密码不能为空!");
                        isChecked = true;
                        return;
                    }
                    login(userName,passWord);
                }
                break;
        }
    }

    private void login(String userName, String passWord) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("登陆中...");
        }
        mLoadingDialog.show();
        LoginRequest request = new LoginRequest();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            request.setMobile(userName);
            request.setPassword(passWord);
            ServiceClient.requestServer(this,"登陆中",request, LoginRequest.LoginResponse.class,
                    new ServiceClient.OnSimpleActionListener<LoginRequest.LoginResponse>() {
                        @Override
                        public void onSuccess(LoginRequest.LoginResponse responseDto) {
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            if (responseDto.getOperateCode() == 0) {
                                if(responseDto.getIsSuccessful()) {
                                    MyApplication.sessionId = responseDto.getSessionId();
                                    IntentUtil.startActivityAndFinish(LoginActivity.this,MainActivity.class);
                                }
                            }else{
                                ToastUtils.showShortToast(LoginActivity.this,"登录失败,请稍后重试!");
                                isChecked = true;
                            }
                        }

                        @Override
                        public boolean onFailure(String errorMessage) {
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            ToastUtils.showShortToast(LoginActivity.this,"登录失败,请稍后重试!");
                            isChecked = true;
                            return false;
                        }
                    });
        }
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
