package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AcceptProjectQuoteRequest;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.AcceptQuoteEvent;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.adapter.AddCostAdapter;
import com.iuunited.myhome.ui.adapter.ProjectItemAdapter;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyListView;
import com.iuunited.myhome.view.ProjectCancelDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/9 18:06
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/9.
 */

public class PriceDetailsActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private TextView tv_subTotal;
    private TextView tv_tax;
    private TextView tv_total;
    private TextView tv_description;
    private Button btn_accept_quote;

    private MyListView lv_project_item;
    private AddCostAdapter mAdapter;//ProjectItemAdapter
    private int quoteId;//估价Id
    private int projectId;//项目Id
    private double subTotal;//小计
    private double taxTotal;//税额
    private double total;//总估价
    private String description;//备注
    private List<QuoteItemBean> items = new ArrayList<>();//报价明细数组
    private String userType;
    private int status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_details);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,"");
        quoteId = getIntent().getIntExtra("quoteId",0);
        projectId = getIntent().getIntExtra("projectId",0);
        subTotal = getIntent().getDoubleExtra("subTotal",0.0);
        taxTotal = getIntent().getDoubleExtra("taxTotal",0.0);
        total = getIntent().getDoubleExtra("total",0.0);
        items = (List<QuoteItemBean>) getIntent().getSerializableExtra("items");
        description = getIntent().getStringExtra("description");
        status = getIntent().getIntExtra("status", status);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_subTotal = (TextView) findViewById(R.id.tv_subTotal);
        tv_tax = (TextView) findViewById(R.id.tv_tax);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_description = (TextView) findViewById(R.id.tv_description);
        btn_accept_quote = (Button) findViewById(R.id.btn_accept_quote);

        lv_project_item = (MyListView)findViewById(R.id.lv_project_item);
        
    }

    private void initData() {
        if(!TextUtils.isEmpty(userType)) {
            if(userType.equals("2")) {
                btn_accept_quote.setVisibility(View.GONE);
            }
        }
        if(status == 1) {
            btn_accept_quote.setText("已接受估价");
            btn_accept_quote.setBackgroundDrawable(getResources().getDrawable(R.drawable.register_text_border_on));
            btn_accept_quote.setEnabled(false);
            btn_accept_quote.setPadding(0,18,0,18);
        }
        btn_accept_quote.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_title.setText("订单详情");
        iv_share.setVisibility(View.GONE);
        if(items!=null) {
            if(items.size()>0) {
                setAdapter();
            }
        }
        tv_subTotal.setText("$"+subTotal);
        tv_tax.setText("$"+taxTotal);
        tv_total.setText("$" + total);
        if(!TextUtils.isEmpty(description)) {
            tv_description.setText(description);
        }
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new AddCostAdapter(this,items);
            lv_project_item.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_accept_quote:
                ProjectCancelDialog mCancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                    @Override
                    public void cancelClick(int id, Context context) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
                                acceptQuote();
                                break;
                        }
                    }
                },"接受估价","请确认是否接受估价");
                mCancelDialog.show();
                break;
        }
    }

    private void acceptQuote() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        AcceptProjectQuoteRequest request = new AcceptProjectQuoteRequest();
        request.setProjectId(projectId);
        ServiceClient.requestServer(this, "加载中...", request, AcceptProjectQuoteRequest.AcceptProjectQuoteResponse.class,
                new ServiceClient.OnSimpleActionListener<AcceptProjectQuoteRequest.AcceptProjectQuoteResponse>() {
                    @Override
                    public void onSuccess(AcceptProjectQuoteRequest.AcceptProjectQuoteResponse responseDto) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                        if(responseDto.getIsSuccessful()) {
                            ToastUtils.showShortToast(PriceDetailsActivity.this,"接收成功!");
                            EventBus.getDefault().post(new AddProjectEvent(1));
                            EventBus.getDefault().post(new AcceptQuoteEvent(1));
                            ActivityCollector.finishAll();
                            PriceDetailsActivity.this.finish();
                        }else{
                            ToastUtils.showShortToast(PriceDetailsActivity.this,"请求失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(PriceDetailsActivity.this,"请求失败,请稍后再试!");
                        return false;
                    }
                });
    }

    @Override
    public void showLoadingDialog(String text) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showCustomToast(String text) {

    }

    @Override
    public boolean getSuccessful() {
        return false;
    }

    @Override
    public void setSuccessful(boolean isSuccessful) {

    }
}
