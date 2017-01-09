package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.project.professional.AddCostActivity;
import com.iuunited.myhome.ui.project.professional.AddTaxActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/1 10:49
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/1.
 */

public class QuotedPriceActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    
    private Button btn_look_details;

    private String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quoted_price);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,"");
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        
        btn_look_details = (Button)findViewById(R.id.btn_look_details);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        iv_share.setVisibility(View.GONE);

        btn_look_details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_look_details:
                if(userType!=null) {
                    if(userType.equals("1")) {
                        IntentUtil.startActivity(this,PriceDetailsActivity.class);
                    }else{
                        IntentUtil.startActivity(this,AddCostActivity.class);
                    }
                }
                break;
        }
    }
}
