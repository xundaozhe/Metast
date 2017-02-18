package com.iuunited.myhome.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.StartActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.view.ProjectCancelDialog;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 10:44
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/28.
 */
public class MineFragment extends BaseFragments implements View.OnClickListener {

    private RelativeLayout iv_back;
    private ImageView iv_share;
    private TextView tv_title;
    private RelativeLayout rl_mine_feedback;
    private RelativeLayout rl_mine_setting;
    private RelativeLayout rl_abstract;
    private RelativeLayout rl_mine_aboutMy;
    private RelativeLayout rl_dropout_app;
    private String userType;

    private String typeMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        iv_back = (RelativeLayout) view.findViewById(R.id.iv_back);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        rl_mine_feedback = (RelativeLayout) view.findViewById(R.id.rl_mine_feedback);
        rl_mine_setting = (RelativeLayout) view.findViewById(R.id.rl_mine_setting);
        rl_abstract = (RelativeLayout) view.findViewById(R.id.rl_abstract);
        rl_mine_aboutMy = (RelativeLayout) view.findViewById(R.id.rl_mine_aboutMy);
        rl_dropout_app = (RelativeLayout) view.findViewById(R.id.rl_dropout_app);
    }

    private void initData() {
        typeMessage = DefaultShared.getStringValue(getActivity(),"typeMessage","");
        iv_back.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        tv_title.setText("我的");
        rl_mine_feedback.setOnClickListener(this);
        rl_mine_setting.setOnClickListener(this);
        rl_abstract.setOnClickListener(this);
        rl_mine_aboutMy.setOnClickListener(this);
        rl_dropout_app.setOnClickListener(this);

        userType = DefaultShared.getStringValue(getActivity(),Config.CONFIG_USERTYPE,"0");
        onclick();
    }

    private void onclick() {
        if(!TextUtils.isEmpty(typeMessage)) {
            if (typeMessage.equals("message")) {

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_feedback :
//                if(!TextUtils.isEmpty(typeMessage)) {
//                    if(typeMessage.equals("message")) {
//                        ToastUtils.showShortToast(getActivity(),"请点击退出后再进行此类操作!");
//                    }
//                }else{
                    IntentUtil.startActivity(getActivity(),FeedBackActivity.class);
//                }
                break;
            case R.id.rl_mine_setting :
//                if(!TextUtils.isEmpty(typeMessage)) {
//                    if(typeMessage.equals("message")) {
//                        ToastUtils.showShortToast(getActivity(),"请点击退出后再进行此类操作!");
//                    }
//                }else {
                    IntentUtil.startActivity(getActivity(), SettingActivity.class);
//                }
                break;
            case R.id.rl_abstract://简介 -- 用户简介  装修商简介
//                if(!TextUtils.isEmpty(typeMessage)) {
//                    if(typeMessage.equals("message")) {
//                        ToastUtils.showShortToast(getActivity(),"请点击退出后再进行此类操作!");
//                    }
//                }else {
                    if (!TextUtils.isEmpty(userType)) {
                        if (userType.equals("1")) {
                            IntentUtil.startActivity(getActivity(), BriefIntroductActivity.class);
                        } else {
                            IntentUtil.startActivity(getActivity(), ProIntroductActivity.class);
                        }
                    }
//                }
                break;
            case R.id.rl_mine_aboutMy:
//                if(!TextUtils.isEmpty(typeMessage)) {
//                    if(typeMessage.equals("message")) {
//                        ToastUtils.showShortToast(getActivity(),"请点击退出后再进行此类操作!");
//                    }
//                }else {
                    IntentUtil.startActivity(getActivity(), AboutMyActivity.class);
//                }
                break;
            case R.id.rl_dropout_app:
//                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("退出登录")
//                        .setMessage("确定退出当前登录?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DefaultShared.clear(MyApplication.getContext());
//                                IntentUtil.startActivityAndFinish(getActivity(), StartActivity.class);
//                            }
//                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create();
//                dialog.show();
                ProjectCancelDialog cancelDialog = new ProjectCancelDialog(getActivity(), new ICancelListener() {
                    @Override
                    public void cancelClick(int id, Context context) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
                                DefaultShared.clear(MyApplication.getContext());
                                DefaultShared.putBooleanValue(getActivity(),Config.ISFIRST,true);
                                IntentUtil.startActivityAndFinish(getActivity(), StartActivity.class);
                                break;
                        }
                    }
                },"退出登录","确定要退出当前登录吗?");
                cancelDialog.show();
                break;
        }
    }
}
