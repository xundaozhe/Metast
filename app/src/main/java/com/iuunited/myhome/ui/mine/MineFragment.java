package com.iuunited.myhome.ui.mine;

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
import com.iuunited.myhome.ui.StartActivity;
import com.iuunited.myhome.ui.login.LoginActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;

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
        iv_back.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        tv_title.setText("我的");
        rl_mine_feedback.setOnClickListener(this);
        rl_mine_setting.setOnClickListener(this);
        rl_abstract.setOnClickListener(this);
        rl_mine_aboutMy.setOnClickListener(this);
        rl_dropout_app.setOnClickListener(this);

        userType = DefaultShared.getStringValue(getActivity(),Config.CONFIG_USERTYPE,"0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_feedback :
                IntentUtil.startActivity(getActivity(),FeedBackActivity.class);
                break;
            case R.id.rl_mine_setting :
                IntentUtil.startActivity(getActivity(),SettingActivity.class);
                break;
            case R.id.rl_abstract://简介 -- 用户简介  装修商简介
                if(!TextUtils.isEmpty(userType)) {
                    if(userType.equals("1")) {
                        IntentUtil.startActivity(getActivity(),BriefIntroductActivity.class);
                    }else{
                        IntentUtil.startActivity(getActivity(), ProIntroductActivity.class);
                    }
                }
                break;
            case R.id.rl_mine_aboutMy:
                IntentUtil.startActivity(getActivity(), AboutMyActivity.class);
                break;
            case R.id.rl_dropout_app:
                DefaultShared.clear(MyApplication.getContext());
                IntentUtil.startActivityAndFinish(getActivity(), StartActivity.class);
                break;
        }
    }
}
