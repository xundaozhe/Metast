package com.iuunited.myhome.ui.mine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 12:20
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/28.
 */
public class SettingActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private RelativeLayout rl_change_pwd;
    private int changePwdIsCheck = 1;
    private ImageView iv_rePwd_more;
    private LinearLayout ll_restart_pwd;
    private TextView tv_forget_pwd;

    private RelativeLayout rl_mine_setting;
    private int noticeSettingCheck = 1;
    private ImageView iv_notice_setting;
    private LinearLayout ll_notice_setting;

    private RelativeLayout rl_specialty_setting;
    private ImageView iv_specialty_more;
    private int specialtyCheck = 1;
    private LinearLayout ll_specialty_setting;

    private RelativeLayout rl_individual_setting;
    private ImageView iv_individual_more;
    private int individualCheck = 1;
    private LinearLayout ll_individual_setting;

    private RelativeLayout rl_money_center;
    private ImageView iv_money_more;
    private int moneyCenterCheck = 1;
    private LinearLayout ll_money_center;

    private RelativeLayout rl_close_account;
    private ImageView iv_close_account;
    private int closeCheck = 1;
    private LinearLayout ll_close_account;
    
    private ImageView iv_push_notice;
    private int iv_push_notice_check = 1;
    private ImageView iv_email_push;
    private int emailPushCheck = 1;

    private ImageView iv_push_notice_two;
    private int pushNoticeCheck = 1;
    private ImageView iv_email_push_two;
    private int email_push_two_check = 1;

    private String userType;
    
    private Button btn_revise;
    private Button btn_add_specialty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        rl_change_pwd = (RelativeLayout) findViewById(R.id.rl_change_pwd);
        iv_rePwd_more = (ImageView) findViewById(R.id.iv_rePwd_more);
        ll_restart_pwd = (LinearLayout) findViewById(R.id.ll_restart_pwd);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);

        rl_mine_setting = (RelativeLayout)findViewById(R.id.rl_mine_setting);
        iv_notice_setting = (ImageView)findViewById(R.id.iv_notice_setting);
        ll_notice_setting = (LinearLayout)findViewById(R.id.ll_notice_setting);
        btn_revise = (Button)findViewById(R.id.btn_revise);

        rl_specialty_setting = (RelativeLayout) findViewById(R.id.rl_specialty_setting);
        iv_specialty_more = (ImageView) findViewById(R.id.iv_specialty_more);
        ll_specialty_setting = (LinearLayout) findViewById(R.id.ll_specialty_setting);

        rl_individual_setting = (RelativeLayout)findViewById(R.id.rl_individual_setting);
        iv_individual_more = (ImageView) findViewById(R.id.iv_individual_more);
        ll_individual_setting = (LinearLayout) findViewById(R.id.ll_individual_setting);

        rl_money_center = (RelativeLayout) findViewById(R.id.rl_money_center);
        iv_money_more = (ImageView) findViewById(R.id.iv_money_more);
        ll_money_center = (LinearLayout) findViewById(R.id.ll_money_center);

        rl_close_account = (RelativeLayout) findViewById(R.id.rl_close_account);
        iv_close_account = (ImageView)findViewById(R.id.iv_close_account);
        ll_close_account = (LinearLayout) findViewById(R.id.ll_close_account);

        iv_push_notice = (ImageView) findViewById(R.id.iv_push_notice);
        iv_email_push = (ImageView) findViewById(R.id.iv_email_push);
        iv_push_notice_two = (ImageView) findViewById(R.id.iv_push_notice_two);
        iv_email_push_two = (ImageView)findViewById(R.id.iv_email_push_two);

        btn_add_specialty = (Button)findViewById(R.id.btn_add_specialty);
    }

    private void initData() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,0+"");
        iv_back.setOnClickListener(this);
        tv_title.setText("设置");
        iv_share.setVisibility(View.GONE);

        rl_change_pwd.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);

        rl_mine_setting.setOnClickListener(this);
        rl_specialty_setting.setOnClickListener(this);
        rl_individual_setting.setOnClickListener(this);
        rl_money_center.setOnClickListener(this);
        rl_close_account.setOnClickListener(this);
        btn_revise.setOnClickListener(this);

        iv_push_notice.setOnClickListener(this);
        iv_email_push.setOnClickListener(this);
        iv_push_notice_two.setOnClickListener(this);
        iv_email_push_two.setOnClickListener(this);

        btn_add_specialty.setOnClickListener(this);

        if(!userType.equals("0")) {
            if(userType.equals("1")) {
                rl_specialty_setting.setVisibility(View.GONE);
                rl_money_center.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",4);
                intent.setClass(SettingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_change_pwd:
                if(changePwdIsCheck == 1) {
                    iv_rePwd_more.setImageResource(R.drawable.item_project_adown);
                    ll_restart_pwd.setVisibility(View.VISIBLE);
                    changePwdIsCheck = 2;
                }else{
                    iv_rePwd_more.setImageResource(R.drawable.item_project_into);
                    ll_restart_pwd.setVisibility(View.GONE);
                    changePwdIsCheck = 1;
                }
                break;
            case R.id.tv_forget_pwd:
                IntentUtil.startActivity(this,ForgetPwdActivity.class);
                break;
            case R.id.btn_add_specialty:
                IntentUtil.startActivity(this,EditSpecialtyActivity.class);
                break;
            case R.id.rl_mine_setting:
                if(noticeSettingCheck == 1) {
                    iv_notice_setting.setImageResource(R.drawable.item_project_adown);
                    ll_notice_setting.setVisibility(View.VISIBLE);
                    noticeSettingCheck = 2;
                }else{
                    iv_notice_setting.setImageResource(R.drawable.item_project_into);
                    ll_notice_setting.setVisibility(View.GONE);
                    noticeSettingCheck = 1;
                }
                break;
            case R.id.btn_revise:
                IntentUtil.startActivity(this,ReviseSettingActivity.class);
                break;
            case R.id.rl_specialty_setting:
                if(specialtyCheck == 1) {
                    iv_specialty_more.setImageResource(R.drawable.item_project_adown);
                    ll_specialty_setting.setVisibility(View.VISIBLE);
                    specialtyCheck = 2;
                }else{
                    iv_specialty_more.setImageResource(R.drawable.item_project_into);
                    ll_specialty_setting.setVisibility(View.GONE);
                    specialtyCheck = 1;
                }
                break;
            case R.id.rl_individual_setting:
                if(individualCheck == 1) {
                    iv_individual_more.setImageResource(R.drawable.item_project_adown);
                    ll_individual_setting.setVisibility(View.VISIBLE);
                    individualCheck = 2;
                }else{
                    iv_individual_more.setImageResource(R.drawable.item_project_into);
                    ll_individual_setting.setVisibility(View.GONE);
                    individualCheck = 1;
                }
                break;
            case R.id.rl_money_center:
                if(moneyCenterCheck == 1) {
                    iv_money_more.setImageResource(R.drawable.item_project_adown);
                    ll_money_center.setVisibility(View.VISIBLE);
                    moneyCenterCheck = 2;
                }else{
                    iv_money_more.setImageResource(R.drawable.item_project_into);
                    ll_money_center.setVisibility(View.GONE);
                    moneyCenterCheck = 1;
                }
                break;
            case R.id.rl_close_account:
                if(closeCheck == 1) {
                    iv_close_account.setImageResource(R.drawable.item_project_adown);
                    ll_close_account.setVisibility(View.VISIBLE);
                    closeCheck = 2;
                }else{
                    iv_close_account.setImageResource(R.drawable.item_project_into);
                    ll_close_account.setVisibility(View.GONE);
                    closeCheck = 1;
                }
                break;
            case R.id.iv_push_notice:
                if(iv_push_notice_check == 1) {
                    iv_push_notice.setImageResource(R.drawable.icon_notice_yes);
                    iv_push_notice_check = 2;
                }else{
                    iv_push_notice.setImageResource(R.drawable.icon_notice_no);
                    iv_push_notice_check = 1;
                }
                break;
            case R.id.iv_email_push:
                if(emailPushCheck == 1) {
                    iv_email_push.setImageResource(R.drawable.icon_notice_yes);
                    emailPushCheck = 2;
                }else{
                    iv_email_push.setImageResource(R.drawable.icon_notice_no);
                    emailPushCheck = 1;
                }
                break;
            case R.id.iv_push_notice_two:
                if(pushNoticeCheck == 1) {
                    iv_push_notice_two.setImageResource(R.drawable.icon_notice_yes);
                    pushNoticeCheck = 2;
                }else{
                    iv_push_notice_two.setImageResource(R.drawable.icon_notice_no);
                    pushNoticeCheck = 1;
                }
                break;
            case R.id.iv_email_push_two:
                if(email_push_two_check == 1) {
                    iv_email_push_two.setImageResource(R.drawable.icon_notice_yes);
                    email_push_two_check = 2;
                }else{
                    iv_email_push_two.setImageResource(R.drawable.icon_notice_no);
                    email_push_two_check = 1;
                }
                break;
        }
    }
}
