package com.iuunited.myhome.ui.project.customer;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.EvaluateItenBean;
import com.iuunited.myhome.bean.GetProjectQuoteRequest;
import com.iuunited.myhome.bean.GetQuoteDetailsRequest;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.ui.adapter.DetailsItemAdapter;
import com.iuunited.myhome.ui.project.professional.AddCostActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 15:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class LoocUpDetailsActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable, AdapterView.OnItemClickListener {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private TextView tv_subTotal;
    private TextView tv_taxTotal;
    private TextView tv_total;
    private TextView tv_description;
    private MyListView lv_details_item;
    private DetailsItemAdapter mAdapter;

    private int projectId;
    private int quoteId;
    private List<QuoteItemBean> items = new ArrayList<>();//报价明细数组
    private  double total;
    private double taxTotal;
    private double subTotal;
    private String description;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_details);
        initView();
        initData();
        initEndDialog();
    }

    /**
     * 工程完结时弹出
     */
    private void initEndDialog() {
        ProjectEndDialog projectEndDialog = new ProjectEndDialog();
        projectEndDialog.show(getSupportFragmentManager(),"endDialog");
    }

    private void initView() {
        projectId = getIntent().getIntExtra("projectId",0);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        lv_details_item = (MyListView) findViewById(R.id.lv_details_item);
        tv_subTotal = (TextView) findViewById(R.id.tv_subTotal);
        tv_taxTotal = (TextView) findViewById(R.id.tv_taxTotal);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_description = (TextView) findViewById(R.id.tv_description);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("昵称");
        iv_share.setVisibility(View.GONE);
        if(projectId>0) {
            if(mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
                mLoadingDialog.setMessage("加载中...");
            }
            mLoadingDialog.show();
            getQuoteList(projectId);
        }else{
            ToastUtils.showShortToast(this, "获取项目详情失败,请稍后重试!");
            this.finish();
        }
//        setAdapter();
        lv_details_item.setOnItemClickListener(this);
    }

    private void getQuoteList(int projectId) {
        GetProjectQuoteRequest request = new GetProjectQuoteRequest();
        request.setProjectId(projectId);
        ServiceClient.requestServer(this, "加载中...", request, GetProjectQuoteRequest.GetProjectQuoteResponse.class,
                new ServiceClient.OnSimpleActionListener<GetProjectQuoteRequest.GetProjectQuoteResponse>() {
                    @Override
                    public void onSuccess(GetProjectQuoteRequest.GetProjectQuoteResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            boolean iAmQuoted = responseDto.getIAmQuoted();
                            if(iAmQuoted) {
                                List<EvaluateItenBean> quotes = responseDto.getQuotes();
                                if(quotes.size()>0) {
                                    EvaluateItenBean evaluateItenBean = quotes.get(0);
                                    quoteId = evaluateItenBean.getId();
                                    initQuoteDetails(quoteId);
                                }
                            }
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(LoocUpDetailsActivity.this, "获取项目详情失败,请稍后再试!");
                        LoocUpDetailsActivity.this.finish();
                        return false;
                    }
                });
    }

    private void initQuoteDetails(int quoteId) {
        GetQuoteDetailsRequest request = new GetQuoteDetailsRequest();
        request.setId(quoteId);
        ServiceClient.requestServer(this, "加载中...", request, GetQuoteDetailsRequest.GetQuoteDetailsResponse.class,
                new ServiceClient.OnSimpleActionListener<GetQuoteDetailsRequest.GetQuoteDetailsResponse>() {
                    @Override
                    public void onSuccess(GetQuoteDetailsRequest.GetQuoteDetailsResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if(responseDto.getOperateCode() == 0) {
                            List<QuoteItemBean> beanItems = responseDto.getItems();
                            if(beanItems.size()>0) {
                                for (int i = 0;i<beanItems.size();i++) {
                                    QuoteItemBean quoteItemBean = beanItems.get(i);
                                    if (items == null) {
                                        items = new ArrayList<QuoteItemBean>();
                                    }
                                    items.add(quoteItemBean);
                                }
                                setAdapter();
                            }
                            total = responseDto.getTotal();
                            taxTotal = responseDto.getTaxTotal();
                            subTotal = responseDto.getSubTotal();
                            description = responseDto.getDescription();
                            if (subTotal > 0) {
                                tv_subTotal.setText("$"+subTotal);
                            }else{
                                tv_subTotal.setText("$"+(total-taxTotal));
                            }
                            tv_total.setText("$" + total);
                            tv_taxTotal.setText("$" + taxTotal);
                            if(!TextUtils.isEmpty(description)) {
                                tv_description.setText(description);
                            }
                        }else{
                            ToastUtils.showShortToast(LoocUpDetailsActivity.this,"加载失败,请稍后重试!");
                            LoocUpDetailsActivity.this.finish();
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(LoocUpDetailsActivity.this,"加载失败,请稍后重试!");
                        LoocUpDetailsActivity.this.finish();
                        return false;
                    }
                });
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new DetailsItemAdapter(this,items);
            lv_details_item.setAdapter(mAdapter);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QuoteItemBean quoteItemBean = items.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemBean",quoteItemBean);
        IntentUtil.startActivity(LoocUpDetailsActivity.this,QuoteDetailsActivity.class,bundle);
    }
}
