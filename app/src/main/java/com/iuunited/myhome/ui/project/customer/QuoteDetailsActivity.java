package com.iuunited.myhome.ui.project.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.bean.QuoteTaxItemBean;
import com.iuunited.myhome.ui.adapter.CustomerTaxAdapter;
import com.iuunited.myhome.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.id.tv_taxTotal;

/**
 * @author xundaozhe
 * @time 2017/2/6 18:27
 * Created by xundaozhe on 2017/2/6.
 */

public class QuoteDetailsActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;

    private TextView tv_quote_name;
    private TextView tv_description;
    private TextView tv_subTotal;
    private TextView tv_quantity;
    private TextView tv_total;
    private ListView lv_tax;
    private CustomerTaxAdapter mAdapter;
    private List<QuoteTaxItemBean> mDatas = new ArrayList<>();
    private LinearLayout ll_tax;

    private String name;
    private String description;
    private double unitPrice;
    private double quantity;
    private double total;

    private QuoteItemBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_details);
        initView();
        initData();
    }

    private void initView() {
        tv_quote_name = (TextView) findViewById(R.id.tv_quote_name);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_subTotal = (TextView) findViewById(R.id.tv_subTotal);
        tv_quantity = (TextView) findViewById(R.id.tv_quantity);
        tv_total = (TextView) findViewById(R.id.tv_total);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        lv_tax = (ListView) findViewById(R.id.lv_tax);
        ll_tax = (LinearLayout) findViewById(R.id.ll_tax);
    }

    private void initData() {
        bean = (QuoteItemBean) getIntent().getSerializableExtra("itemBean");
        if (bean != null) {
            name = bean.getName();
            if(!TextUtils.isEmpty(name)) {
                tv_quote_name.setText(name);
            }
            description = bean.getDescription();
            if(!TextUtils.isEmpty(description)) {
                tv_description.setText(description);
            }
            unitPrice = bean.getUnitPrice();
            tv_subTotal.setText("$"+unitPrice);
            quantity = bean.getQuantity();
            tv_quantity.setText(quantity+"");
            total = bean.getSubTotal();
            tv_total.setText("$" + total);
            List<QuoteTaxItemBean> taxItems = bean.getTaxItems();
            if(taxItems!=null) {
                if(taxItems.size()>0) {
                    for (int i = 0;i<taxItems.size();i++){
                        QuoteTaxItemBean taxItemBean = taxItems.get(i);
                        mDatas.add(taxItemBean);
                    }
                    setAdpter();
                }else{
                    //隐藏控件
                    ll_tax.setVisibility(View.GONE);
                }
            }
        }
        iv_back.setOnClickListener(this);
        tv_title.setText("科目");
    }

    private void setAdpter() {
        if(mAdapter == null) {
            mAdapter = new CustomerTaxAdapter(this, mDatas);
            lv_tax.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
        }
    }
}
