package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.os.Message;
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
import com.iuunited.myhome.bean.GetQuoteDetailsRequest;
import com.iuunited.myhome.bean.GetQuoteItemBean;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.project.professional.AddCostActivity;
import com.iuunited.myhome.ui.project.professional.AddTaxActivity;
import com.iuunited.myhome.util.DateUtils;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

public class QuotedPriceActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private static final int GET_QUOTE_DETAILS_SUCCESS = 0X001;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private TextView tv_quote_total;//总价
    private TextView tv_price;//小计
    private TextView tv_tax_price;//税
    private TextView tv_total;//总价
    private TextView tv_custom_name;
    private TextView tv_quote_date;


    private Button btn_look_details;

    private String userType;
    private int itemId;
    private long quoteTime;
    private String quoteName;
    private String totals;
    private String taxPrice;
    private String price;
    private int quoteId;//估价Id
    private int projectId;//项目Id
    private double subTotal;//小计
    private double taxTotal;//税额
    private double total;//总估价
    private String description;//备注
    private List<QuoteItemBean> items = new ArrayList<>();//报价明细数组
    private boolean isQuote;
    private int status;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quoted_price);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        ActivityCollector.addActivity(this);
        initView();
        initData();
    }

    private void initView() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE, "");
        itemId = getIntent().getIntExtra("itemId",0);
        quoteTime = getIntent().getLongExtra("quoteTime",0L);
        quoteName = getIntent().getStringExtra("quoteName");
        isQuote = getIntent().getBooleanExtra("isQuote",false);
        status = getIntent().getIntExtra("status",0);
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        
        btn_look_details = (Button)findViewById(R.id.btn_look_details);
        tv_quote_total = (TextView) findViewById(R.id.tv_quote_total);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_tax_price = (TextView)findViewById(R.id.tv_tax_price);
        tv_total = (TextView)findViewById(R.id.tv_total);
        tv_custom_name = (TextView) findViewById(R.id.tv_custom_name);
        tv_quote_date = (TextView)findViewById(R.id.tv_quote_date);

    }

    private void initData() {
        iv_back.setOnClickListener(this);
        iv_share.setVisibility(View.GONE);
        btn_look_details.setOnClickListener(this);
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        initQuoteDetails();
    }

    private void initQuoteDetails() {
        if(itemId!=0) {
            GetQuoteDetailsRequest request = new GetQuoteDetailsRequest();
            request.setId(itemId);
            ServiceClient.requestServer(this, "加载中...", request, GetQuoteDetailsRequest.GetQuoteDetailsResponse.class,
                    new ServiceClient.OnSimpleActionListener<GetQuoteDetailsRequest.GetQuoteDetailsResponse>() {
                        @Override
                        public void onSuccess(GetQuoteDetailsRequest.GetQuoteDetailsResponse responseDto) {
                            if(responseDto.getOperateCode() == 0) {
                                quoteId = responseDto.getId();
                                projectId = responseDto.getProjectId();
                                subTotal = responseDto.getSubTotal();
                                taxTotal = responseDto.getTaxTotal();
                                total = responseDto.getTotal();
                                description = responseDto.getDescription();
                                List<QuoteItemBean> quoteItem = responseDto.getItems();
                                if(quoteItem!=null) {
                                    if(quoteItem.size()>0) {
                                        for (int i = 0;i<quoteItem.size();i++){
                                            QuoteItemBean itemBean = quoteItem.get(i);
                                            items.add(itemBean);
                                        }
                                    }
                                }
                                double total = responseDto.getTotal();
                                totals = "$"+total;
                                tv_quote_total.setText(totals);
                                tv_total.setText(totals);
                                double taxTotal = responseDto.getTaxTotal();
                                taxPrice = "$"+taxTotal;
                                tv_tax_price.setText(taxPrice);
                                double subTotal = responseDto.getSubTotal();
                                if(subTotal>0) {
                                    price = "$"+subTotal;
                                }else{
                                    price = "$"+ (total-taxTotal);
                                }
                                tv_price.setText(price);
                                if(quoteTime!=0L) {
                                    String date = DateUtils.getDateToStringSec(quoteTime);
                                    tv_quote_date.setText(date);
                                }
                                if(!TextUtils.isEmpty(quoteName)) {
                                    tv_custom_name.setText(quoteName);
                                }
                                Message message = new Message();
                                message.what = GET_QUOTE_DETAILS_SUCCESS;
                                sendUiMessage(message);
                            }else{
                                if(mLoadingDialog!=null) {
                                    mLoadingDialog.dismiss();
                                }
                                ToastUtils.showLongToast(QuotedPriceActivity.this,"获取估价详情失败,请稍后重试!");
                            }
                        }

                        @Override
                        public boolean onFailure(String errorMessage) {
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            ToastUtils.showLongToast(QuotedPriceActivity.this,"获取估价详情失败,请稍后重试!");
                            return false;
                        }
                    });
        }else{
            if(mLoadingDialog!=null) {
                mLoadingDialog.dismiss();
            }
            ToastUtils.showShortToast(this, "获取估价详情失败,请稍后再试!");
            this.finish();
        }
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case GET_QUOTE_DETAILS_SUCCESS :
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_look_details:
                Bundle bundle = new Bundle();
                bundle.putInt("quoteId", quoteId);
                bundle.putInt("projectId", projectId);
                bundle.putDouble("subTotal",subTotal);
                bundle.putDouble("taxTotal",taxTotal);
                bundle.putDouble("total",total);
                bundle.putSerializable("items", (Serializable) items);
                bundle.putString("description",description);
                bundle.putInt("status",status);
                if(userType!=null) {
                    if(userType.equals("1")) {
                        IntentUtil.startActivity(this,PriceDetailsActivity.class,bundle);
                    }else{
                        if(isQuote) {
                            IntentUtil.startActivity(this,AddCostActivity.class,bundle);
                        }else{
                            IntentUtil.startActivity(this,PriceDetailsActivity.class,bundle);
                        }
                    }
                }
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
