package com.iuunited.myhome.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 14:54
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 提交E-mail界面
 * Created by xundaozhe on 2016/10/26.
 */
public class RegisterEmailActivity extends BaseFragmentActivity {

    private ImageView iv_back;
    private Button btn_next_email;
    private TextView tv_title;
    private ImageView iv_share;
    private EditText et_user_email;
    private String userEmail;
    private String userMobile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        initView();
        initData();
    }

    private void initView() {
        btn_next_email = (Button) findViewById(R.id.btn_next_email);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);

        et_user_email = (EditText)findViewById(R.id.et_user_email);
    }

    private void initData() {
        userMobile = getIntent().getStringExtra("userMobile");
        btn_next_email.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_email :
//                IntentUtil.startActivity(this,SettingPwdActivity.class);
                userEmail = et_user_email.getText().toString().trim();
                if(TextUtils.isEmpty(userEmail)) {
                    ToastUtils.showShortToast(RegisterEmailActivity.this,"邮箱地址不能为空!");
                }
                if (!TextUtils.isEmail(userEmail)) {
                    ToastUtils.showShortToast(RegisterEmailActivity.this,"请输入正确的email!");
                }else {
                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(userEmail)) {
                        intent.putExtra("userEmail", userEmail);
                        intent.putExtra("userMobile", userMobile);
                        intent.setClass(RegisterEmailActivity.this, SettingPwdActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.showShortToast(RegisterEmailActivity.this, "邮箱地址不能为空! ");
                    }
                }

                break;
            case R.id.iv_back :
                IntentUtil.startActivityAndFinish(this,RegisterMobileActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
