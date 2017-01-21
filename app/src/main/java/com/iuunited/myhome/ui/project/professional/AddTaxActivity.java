package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AddTaxBean;
import com.iuunited.myhome.ui.adapter.AddTaxAdapter;
import com.iuunited.myhome.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/29 17:03
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/29.
 */

public class AddTaxActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private ListView lv_add_tax;
    private List<AddTaxBean> mDatas = new ArrayList<>();
    private AddTaxAdapter mAdapter;
    private Button btn_add_new_tax;
    private AddTaxBean data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tax);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        data = (AddTaxBean) getIntent().getSerializableExtra("newTax");
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        lv_add_tax = (ListView)findViewById(R.id.lv_add_tax);
        btn_add_new_tax = (Button)findViewById(R.id.btn_add_new_tax);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_add_new_tax.setOnClickListener(this);
        setAdapter();
    }

    private void setAdapter() {
        AddTaxBean addTaxBean = new AddTaxBean();
        addTaxBean.setTaxName("gst");
//        addTaxBean.setTaxValue("5.00%");
        AddTaxBean bean = new AddTaxBean();
        bean.setTaxName("pst");
//        bean.setTaxValue("7.00%");
        mDatas.add(addTaxBean);
        mDatas.add(bean);
        if(data != null) {
            mDatas.add(data);
        }
        if(mAdapter == null) {
            mAdapter = new AddTaxAdapter(this,mDatas);
            lv_add_tax.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_add_new_tax:
                //跳转到增加新税的activity
                IntentUtil.startActivityAndFinish(this,AddNewTaxActivity.class);
                break;
        }
    }
}
