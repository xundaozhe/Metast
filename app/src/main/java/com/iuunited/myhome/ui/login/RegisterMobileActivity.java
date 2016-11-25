package com.iuunited.myhome.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.bean.RegisterMobileRequest;
import com.iuunited.myhome.bean.RegisterVerifySMSRequest;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 12:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册---用户手机
 * Created by xundaozhe on 2016/10/26.
 */
public class RegisterMobileActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {
    
    private ImageView iv_back;
    private TextView btn_next_one;
    private TextView tv_title;
    private ImageView iv_share;
    private EditText et_user_mobile;
    private Button btn_send_idf_code;
    private EditText et_verify_captcha;

    private boolean isSendChecked = true;
    private String userMobile;
    private String captcha;//短信验证码
    private boolean isNextChecked = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_next_one = (TextView)findViewById(R.id.btn_next_one);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        et_user_mobile = (EditText) findViewById(R.id.et_user_mobile);
        btn_send_idf_code = (Button) findViewById(R.id.btn_send_idf_code);
        et_verify_captcha = (EditText)findViewById(R.id.et_verify_captcha);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        btn_next_one.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_send_idf_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                IntentUtil.startActivityAndFinish(this,LoginActivity.class);
                break;
            case R.id.btn_next_one:
                if(isNextChecked) {
                    isNextChecked = false;
                    captcha = et_verify_captcha.getText().toString().trim();
                    if(TextUtils.isEmpty(captcha)) {
                        ToastUtils.showLongToast(this, "验证码不能为空!");
                        isNextChecked = true;
                        break;
                    }
//                    verifyCaptch(captcha);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
                            verifyCaptch(captcha);
//                        }
//                    }).start();
                }

                break;
            case R.id.btn_send_idf_code:
                if(isSendChecked) {
                    isSendChecked = false;
                    userMobile = et_user_mobile.getText().toString().trim();
                    if(TextUtils.isEmpty(userMobile)) {
                        ToastUtils.showLongToast(this,"手机号码不能为空!");
                        isSendChecked = true;
                        break;
                    }
                    if(!TextUtils.isMobileNO(userMobile)) {
                        ToastUtils.showLongToast(this, "请输入正确的手机号!");
                        isSendChecked = true;
                        return;
                    }
                    sendIdfCode(userMobile);
                }
                break;
        }
    }

    private void verifyCaptch(String captcha) {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("验证中...");
        }
        mLoadingDialog.show();
        RegisterVerifySMSRequest request = new RegisterVerifySMSRequest();
        request.setCaptcha(captcha);
//        request.setSessionId(MyApplication.sessionId);
        ServiceClient.requestServer(this,"验证中...",request, RegisterVerifySMSRequest.RegisterVerifySMSResponse.class,
                new ServiceClient.OnSimpleActionListener<RegisterVerifySMSRequest.RegisterVerifySMSResponse>() {
                    @Override
                    public void onSuccess(RegisterVerifySMSRequest.RegisterVerifySMSResponse responseDto) {
                        int operateCode = responseDto.getOperateCode();
                        boolean captchaMeeted = responseDto.getIsCaptchaMeeted();
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                        if (captchaMeeted) {
                            Bundle bundle = new Bundle();
                            bundle.putString("userMobile",userMobile);
                            IntentUtil.startActivity(RegisterMobileActivity.this, RegisterEmailActivity.class,bundle);
                        } else {
                            ToastUtils.showLongToast(RegisterMobileActivity.this, "验证码失效,请稍后重试!");
                            isNextChecked = true;
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showLongToast(RegisterMobileActivity.this,"验证失败,请稍后重试!");
                        isNextChecked = true;
                        return false;
                    }
                });
//        OkHttpClient httpClient = new OkHttpClient();
//        FormBody body = new FormBody.Builder()
//                .add("Captcha",captcha).build();
//        Request request = new Request.Builder().url("http://www.iuunited.com:9000/Register/VerifyCaptcha")
//                .addHeader("Content-Type","application/json;charset=utf-8")
//                .addHeader("session-id",MyApplication.sessionId)
//                .post(body).build();
//        Call call = httpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ToastUtils.showShortToast(RegisterOneActivity.this,"请求失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()) {
//                    String s = response.body().string();
//                }
//            }
//        });
    }

    private void sendIdfCode(String mobile) {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("验证码发送中...");
        }
        mLoadingDialog.show();
        RegisterMobileRequest request = new RegisterMobileRequest();
        request.setMobile(mobile);
//        request.setOperateCode(0); //http://www.iuunited.com:9000/Register/VerifyMobile
        ServiceClient.requestServer(this,"发送中...",request,RegisterMobileRequest.RegisterMobileResponse.class,
                new ServiceClient.OnSimpleActionListener<RegisterMobileRequest.RegisterMobileResponse>() {
                    @Override
                    public void onSuccess(RegisterMobileRequest.RegisterMobileResponse responseDto) {
                        int operateCode = responseDto.getOperateCode();
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if(operateCode == 0) {
                            String sessionId = responseDto.getSessionId();
                            MyApplication.sessionId = sessionId;
//                            isSendChecked = false;
                        }else{
                            ToastUtils.showLongToast(RegisterMobileActivity.this,"获取验证码失败,请稍后重试!");
                            isSendChecked = true;
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        String errorMessage1 = errorMessage;
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showLongToast(RegisterMobileActivity.this,"出错啦,请稍后重试!");
                        isSendChecked = true;
                        return false;
                    }
                });
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
