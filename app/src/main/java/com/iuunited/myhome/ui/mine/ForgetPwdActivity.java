package com.iuunited.myhome.ui.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.login.SettingPwdActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.ForgetPwdSuccessDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 14:41
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 忘记密码--修改密码
 * Created by xundaozhe on 2016/10/29.
 */
public class ForgetPwdActivity extends BaseFragmentActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private ImageView iv_react_one;
    private ImageView iv_react_two;

    private TextView tv_url_two,tv_url_one;
    private boolean isCheckedOne = true;
    private boolean isCheckedTwo = true;
    private String urlText = "条款和协议";
    private String urlTexts = "隐私协议";

    private Button btn_submit;
    private ForgetPwdSuccessDialog mDidlog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        iv_react_one = (ImageView) findViewById(R.id.iv_react_one);
        iv_react_two = (ImageView) findViewById(R.id.iv_react_two);
        tv_url_two = (TextView) findViewById(R.id.tv_url_two);
        tv_url_one = (TextView)findViewById(R.id.tv_url_one);

        btn_submit = (Button)findViewById(R.id.btn_submit);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("修改密码");
        iv_share.setVisibility(View.GONE);

        iv_react_one.setOnClickListener(this);
        iv_react_two.setOnClickListener(this);

        settingUrl();
        settingUrls();

        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
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
            case R.id.btn_submit:
                if(mDidlog == null) {
                    mDidlog = new ForgetPwdSuccessDialog(this);
                    mDidlog.show();
                }else{
                    mDidlog.show();
                }
                break;
        }

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
                ToastUtils.showLongToast(ForgetPwdActivity.this,"暂未添加该功能!");
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
                ToastUtils.showLongToast(ForgetPwdActivity.this,"暂未添加该功能!");
            }
        }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_url_one.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv_url_one.append(spStr);
        tv_url_one.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

}
