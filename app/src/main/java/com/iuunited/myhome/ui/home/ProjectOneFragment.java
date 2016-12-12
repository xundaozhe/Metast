package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 10:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class ProjectOneFragment extends BaseFragments implements View.OnClickListener {

    private Button btn_next_one;
    private Button btn_select_map;
    private String address;

    private EditText et_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_one,null);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userAddress(UserAddressEvent event){
        address = event.address;
        if(!TextUtils.isEmpty(address)) {
            et_address.setText(address);
        }
    }

    private void initView(View view) {
        btn_next_one = (Button) view.findViewById(R.id.btn_next_one);
        btn_select_map = (Button) view.findViewById(R.id.btn_select_map);
        et_address = (EditText) view.findViewById(R.id.et_address);
        et_address.setFocusable(false);
    }

    private void initData() {
        btn_next_one.setOnClickListener(this);
        btn_select_map.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_one:
                EventBus.getDefault().post(new ChangeProjectFmEvent(1));
                break;
            case R.id.btn_select_map:
                IntentUtil.startActivity(getActivity(),MapActivity.class);
                break;
        }
    }
}
