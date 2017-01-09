package com.iuunited.myhome.ui.project.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.event.UserMarkerAddressEvent;
import com.iuunited.myhome.ui.home.MapActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.iuunited.myhome.R.id.et_address;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 15:55
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseEssentialActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private Button btn_select_map;
    private String address;
    private String markerAddress = "";

    private EditText et_address;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_essential);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userAddress(UserAddressEvent event){
        address = event.address;
        if(markerAddress.equals("")) {
            if(!TextUtils.isEmpty(address)) {
                et_address.setText(address);
            }
        } else{
            et_address.setText(markerAddress);
        }
        if(!TextUtils.isEmpty(address)&&!markerAddress.equals("")) {
            et_address.setText(address);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userMarkerAddress(UserMarkerAddressEvent event){
        markerAddress = event.markerAddress;
        if(!markerAddress.equals("")) {
            et_address.setText(markerAddress);
        }
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        btn_select_map = (Button) findViewById(R.id.btn_select_map);
        et_address = (EditText)findViewById(R.id.et_address);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);

        btn_select_map.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_select_map:
                Intent intent = new Intent();
                intent.putExtra("class", "ReviseEssentialActivity");
                intent.setClass(ReviseEssentialActivity.this,MapActivity.class);
                startActivity(intent);
//                IntentUtil.startActivity(this, MapActivity.class);
                break;
        }
    }
}
