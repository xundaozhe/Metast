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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.bean.RegisterMobileRequest;
import com.iuunited.myhome.bean.RegisterUserRequest;
import com.iuunited.myhome.bean.RegisterVerifySMSRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.RegisterSuccessDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 12:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 注册页面
 * Created by xundaozhe on 2016/10/26.
 */
public class RegisterActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {
    
    private RelativeLayout iv_back;
    private TextView btn_next_one;
    private TextView tv_title;
    private ImageView iv_share;
    private EditText et_user_mobile;//手机号
    private Button btn_send_idf_code;
    private EditText et_verify_captcha;//验证码
    private EditText et_user_password;//密码
    private EditText et_user_email;//邮箱
    private ImageView iv_react_one;
    private ImageView iv_react_two;
    private TextView tv_url_two,tv_url_one;
    
    private TextView tv_question;

    private RegisterSuccessDialog mDialog;


    private boolean isSendChecked = true;
    private String userMobile;
    private String captcha;//短信验证码
    private String passWord;
    private String email;
    private int userType;
    private boolean isNextChecked = true;

    private boolean isCheckedOne = true;
    private boolean isCheckedTwo = true;
    private String urlText = "条款和协议";
    private String urlTexts = "隐私协议";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        btn_next_one = (TextView)findViewById(R.id.btn_next_one);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        et_user_mobile = (EditText) findViewById(R.id.et_user_mobile);
        btn_send_idf_code = (Button) findViewById(R.id.btn_send_idf_code);
        et_verify_captcha = (EditText)findViewById(R.id.et_verify_captcha);
        et_user_password = (EditText)findViewById(R.id.et_user_password);
        et_user_email = (EditText)findViewById(R.id.et_user_email);
        iv_react_one = (ImageView)findViewById(R.id.iv_react_one);
        iv_react_two = (ImageView)findViewById(R.id.iv_react_two);
        tv_url_two = (TextView)findViewById(R.id.tv_url_two);
        tv_url_one = (TextView)findViewById(R.id.tv_url_one);
        tv_question = (TextView)findViewById(R.id.tv_question);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        btn_next_one.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_send_idf_code.setOnClickListener(this);
        iv_react_one.setOnClickListener(this);
        iv_react_two.setOnClickListener(this);
        tv_url_one.setOnClickListener(this);
        tv_url_two.setOnClickListener(this);
        tv_question.setOnClickListener(this);

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
                IntentUtil.startActivity(RegisterActivity.this,ReactTwoActivity.class);
//                ToastUtils.showShortToast(RegisterMobileActivity.this,"暂未添加该功能!");
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
                IntentUtil.startActivity(RegisterActivity.this,ReactOneActivity.class);
//                ToastUtils.showShortToast(RegisterMobileActivity.this, "暂未添加该功能!");
            }
        }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_url_one.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv_url_one.append(spStr);
        tv_url_one.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                IntentUtil.startActivityAndFinish(this,LoginActivity.class);
                break;
            case R.id.btn_next_one:
                if(isNextChecked) {
                    iv_react_two.setDrawingCacheEnabled(true);
                        isNextChecked = false;
                        captcha = et_verify_captcha.getText().toString().trim();
                        passWord = et_user_password.getText().toString().trim();
                        email = et_user_email.getText().toString().trim();
                        userMobile = et_user_mobile.getText().toString().trim();
                        if (TextUtils.isEmpty(userMobile)) {
                            ToastUtils.showShortToast(this, "手机号码不能为空!");
                            isNextChecked = true;
                            break;
                        }
                        if (!TextUtils.isMobileNO(userMobile)) {
                            ToastUtils.showShortToast(this, "请输入正确的手机号!");
                            isNextChecked = true;
                            break;
                        }
                        if (TextUtils.isEmpty(captcha)) {
                            ToastUtils.showShortToast(this, "验证码不能为空!");
                            isNextChecked = true;
                            break;
                        }
                        if (TextUtils.isEmpty(passWord)) {
                            ToastUtils.showShortToast(this, "密码不能为空!");
                            isNextChecked = true;
                            break;
                        }
                        if (!TextUtils.isEmail(email) && !email.equals("")) {
                            ToastUtils.showShortToast(this, "请输入正确的邮箱!");
                            isNextChecked = true;
                            break;
                        }
                        if (!iv_react_two.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.react_yes).getConstantState()) ||
                                !iv_react_one.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.react_yes).getConstantState())) {
                            ToastUtils.showShortToast(RegisterActivity.this,"请详细阅读协议并同意后进行下一步操作!");
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
                        ToastUtils.showShortToast(this,"手机号码不能为空!");
                        isSendChecked = true;
                        break;
                    }
                    if(!TextUtils.isMobileNO(userMobile)) {
                        ToastUtils.showShortToast(this, "请输入正确的手机号!");
                        isSendChecked = true;
                        return;
                    }
                    sendIdfCode(userMobile);
                }
                break;
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
            case R.id.tv_question:
                IntentUtil.startActivity(this,QuestionActivity.class);
                break;
        }
    }

    private void registerUser(){
        if(mLoadingDialog != null) {
//            mLoadingDialog = new LoadingDialog(RegisterMobileActivity.this);
            mLoadingDialog.setMessage("注册中...");
        }
        mLoadingDialog.show();
        if(!TextUtils.isEmpty(passWord)) {
            if(!TextUtils.isEmpty(userMobile)) {
                    this.userType = MyApplication.userType;
                    RegisterUserRequest request = new RegisterUserRequest();
                    request.setMobile(userMobile);
                    request.setEmail(email);
                    request.setPassword(passWord);
                    request.setUserType(userType);
                    ServiceClient.requestServer(this, "请求中...", request, RegisterUserRequest.RegisterUserResponse.class,
                            new ServiceClient.OnSimpleActionListener<RegisterUserRequest.RegisterUserResponse>() {
                                @Override
                                public void onSuccess(RegisterUserRequest.RegisterUserResponse responseDto) {
                                    int operateCode = responseDto.getOperateCode();
                                    if(operateCode == 0) {
                                        if(mLoadingDialog != null) {
                                            mLoadingDialog.dismiss();
                                        }
                                        int userId = responseDto.getUserId();
                                        String mobile = responseDto.getMobile();
                                        if (userId != 0) {
                                            DefaultShared.putStringValue(MyApplication.getContext(), Config.CONFIG_USERID, userId + "");
                                            DefaultShared.putStringValue(MyApplication.getContext(), Config.CONFIG_USERNAME, mobile);
                                            DefaultShared.putStringValue(MyApplication.getContext(),Config.CONFIG_PASSWORD,passWord);
                                        }
                                        MyApplication.userId = userId;
                                        MyApplication.mobile = mobile;
                                        MyApplication.userLogoUri = responseDto.getLogoUri();
                                        ActivityCollector.finishAll();
                                        showSuccessDialog();
                                    }else if(operateCode == 20) {
                                        ToastUtils.showShortToast(RegisterActivity.this,"用户已经存在,请直接登录");
                                    }else if(operateCode == 21) {
                                        ToastUtils.showShortToast(RegisterActivity.this,"验证码错误,请核对后再次输入!");
                                    }else{
                                        ToastUtils.showShortToast(RegisterActivity.this,"网络开小差啦,请稍后重试!");
                                    }
                                }

                                @Override
                                public boolean onFailure(String errorMessage) {
                                    ToastUtils.showShortToast(RegisterActivity.this,"注册失败,请稍后重试!");
                                    return false;
                                }
                            });
                }
            }

    }

    private void verifyCaptch(String captcha) {
        if(mLoadingDialog != null) {
//            mLoadingDialog = new LoadingDialog(this);
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
                        if(operateCode == 0) {
                            if (captchaMeeted) {
    //                            Bundle bundle = new Bundle();
    //                            bundle.putString("userMobile",userMobile);
    //                            IntentUtil.startActivity(RegisterMobileActivity.this, RegisterEmailActivity.class,bundle);
                                if(mLoadingDialog!=null) {
                                    mLoadingDialog.dismiss();
                                }
                                registerUser();
                            } else {
                                ToastUtils.showShortToast(RegisterActivity.this, "验证失败,请稍后重试!");
                                isNextChecked = true;
                            }
                        }else if(operateCode == 21) {
                            ToastUtils.showShortToast(RegisterActivity.this, "验证码错误,请核对后再次输入!");
                            isNextChecked = true;
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(RegisterActivity.this,"验证失败,请稍后重试!");
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
                            if (!TextUtils.isEmpty(sessionId)) {
                                DefaultShared.putStringValue(MyApplication.getContext(), Config.CONFIG_SESSIONID,sessionId);
                                MyApplication.sessionId = sessionId;
                            }
//                            isSendChecked = false;
                        }else if(operateCode == 20) {
                            ToastUtils.showShortToast(RegisterActivity.this,"用户已经存在,请直接登录");
                            isSendChecked = true;
                        }else if(operateCode == 21) {
                            ToastUtils.showShortToast(RegisterActivity.this,"验证码错误,请核对后再次输入!");
                            isSendChecked = true;
                        }else{
                            ToastUtils.showShortToast(RegisterActivity.this,"获取验证码失败,请稍后重试!");
                            isSendChecked = true;
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        String errorMessage1 = errorMessage;
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(RegisterActivity.this,"出错啦,请稍后重试!");
                        isSendChecked = true;
                        return false;
                    }
                });
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
