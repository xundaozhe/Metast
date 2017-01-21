package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.ChangeProjectQuoteRequest;
import com.iuunited.myhome.bean.EvaluateItenBean;
import com.iuunited.myhome.bean.GetProjectQuoteRequest;
import com.iuunited.myhome.bean.GetQuoteDetailsRequest;
import com.iuunited.myhome.bean.GetQuoteItemBean;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.event.AddQuoteEvent;
import com.iuunited.myhome.ui.adapter.AddCostAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.edit;
import static com.iuunited.myhome.R.id.et_price;
import static com.iuunited.myhome.R.id.tv_add_tax_price;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/27 14:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/27.
 */

public class AddCostActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private TextView tv_subTotal;
    private TextView tv_tax;
    private TextView tv_total;
    private EditText et_description;
    private Button btn_submit;

    private MyListView lv_details_item;
    private AddCostAdapter mAdapter;

    private Button btn_item_depot;
    private Button btn_new_item;
    private int quoteId;//估价Id
    private int projectId;//项目Id
    private double subTotal;//小计
    private double taxTotal;//税额
    private double total;//总估价
    private String description;//备注
    private List<QuoteItemBean> items = new ArrayList<>();//报价明细数组
    private int positions;
    private int claz;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddQuoteEvent(AddQuoteEvent event){
        int type = event.type;
        QuoteItemBean itemBean = event.mItemBean;
        if(type == 1) {
            items.add(itemBean);
            setAdapter();
            setQuote();
        }else{
            items.remove(positions);
            items.add(itemBean);
            setAdapter();
            setQuote();
        }
    }

    private void setQuote() {
        if(subTotal>0) {
            subTotal = 0;
        }
        if(taxTotal>0) {
            taxTotal = 0;
        }
        if(total>0) {
            total = 0;
        }
        for (int i = 0;i<items.size();i++){
            QuoteItemBean quoteItemBean = items.get(i);
            double unitPrice = quoteItemBean.getUnitPrice();
            subTotal += unitPrice;
            double subTotal = quoteItemBean.getSubTotal();
            double itemTax = subTotal - unitPrice;
            taxTotal += itemTax;
            total += subTotal;
        }
        tv_subTotal.setText("$" + subTotal);
        tv_tax.setText("$" + taxTotal);
        tv_total.setText("$" + total);
    }

    private void initView() {
        quoteId = getIntent().getIntExtra("quoteId",0);
        projectId = getIntent().getIntExtra("projectId",0);
        subTotal = getIntent().getDoubleExtra("subTotal",0.0);
        taxTotal = getIntent().getDoubleExtra("taxTotal",0.0);
        total = getIntent().getDoubleExtra("total",0.0);
        claz = getIntent().getIntExtra("class",0);
        if(claz<=1) {
            items = (List<QuoteItemBean>) getIntent().getSerializableExtra("items");
        }
        description = getIntent().getStringExtra("description");
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        btn_item_depot = (Button)findViewById(R.id.btn_item_depot);
        btn_new_item = (Button) findViewById(R.id.btn_new_item);
        tv_subTotal = (TextView) findViewById(R.id.tv_subTotal);
        tv_tax = (TextView) findViewById(R.id.tv_tax);
        tv_total = (TextView) findViewById(R.id.tv_total);
        et_description = (EditText) findViewById(R.id.et_description);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        lv_details_item = (MyListView) findViewById(R.id.lv_details_item);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("装修科目");
        iv_share.setVisibility(View.GONE);
        btn_item_depot.setOnClickListener(this);
        btn_new_item.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        if(claz>0) {
            if(mLoadingDialog==null) {
                mLoadingDialog = new LoadingDialog(this);
                mLoadingDialog.setMessage("加载中...");
            }
            mLoadingDialog.show();
            getQuoteList();
//            initQuoteDetails();
        }else{
            if(items!=null) {
                if(items.size()>0) {
                    setAdapter();
                }
            }
            if(subTotal>0) {
                tv_subTotal.setText("$" + subTotal);
            }else{
                tv_subTotal.setText("$" + (total-taxTotal));
            }
            tv_tax.setText("$" + taxTotal);
            tv_total.setText("$" + total);
            if(!TextUtils.isEmpty(description)) {
                et_description.setText(description);
            }
        }
        lv_details_item.setOnItemClickListener(this);
    }

    /**
     * 访问获取项目详情估价列表,
     */
    private void getQuoteList() {
        GetProjectQuoteRequest request = new GetProjectQuoteRequest();
        request.setProjectId(projectId);
        ServiceClient.requestServer(this, "获取数据中...", request, GetProjectQuoteRequest.GetProjectQuoteResponse.class,
                new ServiceClient.OnSimpleActionListener<GetProjectQuoteRequest.GetProjectQuoteResponse>() {
                    @Override
                    public void onSuccess(GetProjectQuoteRequest.GetProjectQuoteResponse responseDto) {
                        if (responseDto.getOperateCode() == 0) {
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
                        ToastUtils.showShortToast(AddCostActivity.this, "获取数据失败,请稍后重试!");
                        AddCostActivity.this.finish();
                        return false;
                    }
                });
    }

    /**
     * 首先访问获取项目估价列表接口  成功后返回对象中有我是否估价,遍历,如果我估价取出那个id然后set进这里在访问
     */
    private void initQuoteDetails(int quouteId) {
        GetQuoteDetailsRequest request = new GetQuoteDetailsRequest();
        request.setId(quoteId);
        ServiceClient.requestServer(this, "加载中...", request, GetQuoteDetailsRequest.GetQuoteDetailsResponse.class,
                new ServiceClient.OnSimpleActionListener<GetQuoteDetailsRequest.GetQuoteDetailsResponse>() {
                    @Override
                    public void onSuccess(GetQuoteDetailsRequest.GetQuoteDetailsResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getOperateCode() == 0) {
                            List<QuoteItemBean> beanItems = responseDto.getItems();
                            if(beanItems.size()>0) {
                                for (int i = 0;i<beanItems.size();i++){
                                    QuoteItemBean quoteItemBean = beanItems.get(i);
                                    if(items == null) {
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
                            if(subTotal>0) {
                                tv_subTotal.setText("$" + subTotal);
                            }else{
                                tv_subTotal.setText("$" + (total - taxTotal));
                            }
                            tv_tax.setText("$" + taxTotal);
                            tv_total.setText("$" + total);
                            if(!TextUtils.isEmpty(description)) {
                                et_description.setText(description);
                            }
                        }else{
                            ToastUtils.showShortToast(AddCostActivity.this,"加载失败,请稍后重试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(AddCostActivity.this,"加载失败,请稍后重试!");
                        return false;
                    }
                });
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new AddCostAdapter(this,items);
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
            case R.id.btn_item_depot:
                IntentUtil.startActivity(this,ProItemDepotActivity.class);
                break;
            case R.id.btn_new_item:
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                bundle.putInt("changeQuote",1);
                IntentUtil.startActivity(this,EditNewProjectActivity.class,bundle);
                break;
            case R.id.btn_submit:
                submitChangeQuote();
                break;
        }
    }

    private void submitChangeQuote() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("提交中...");
        }
        mLoadingDialog.show();
        ChangeProjectQuoteRequest request = new ChangeProjectQuoteRequest();
        request.setId(quoteId);
        request.setProjectId(projectId);
        request.setSubTotal(subTotal);
        request.setTaxTotal(taxTotal);
        request.setTotal(total);
        String desc = et_description.getText().toString().trim();
        if(!TextUtils.isEmpty(desc)) {
            request.setDescription(desc);
        }
        request.setItems(items);
        ServiceClient.requestServer(this, "提交中...", request, ChangeProjectQuoteRequest.ChangeProjectQuoteResponse.class,
                new ServiceClient.OnSimpleActionListener<ChangeProjectQuoteRequest.ChangeProjectQuoteResponse>() {
                    @Override
                    public void onSuccess(ChangeProjectQuoteRequest.ChangeProjectQuoteResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getIsSuccessful()) {
                            ToastUtils.showShortToast(AddCostActivity.this, "修改成功!");
                            AddCostActivity.this.finish();
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(AddCostActivity.this, "修改成功!");
                        return false;
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        positions = position;
        QuoteItemBean quoteItemBean = items.get(position);
        int quoteId = quoteItemBean.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("quoteId",quoteId);
        bundle.putInt("type",2);
        bundle.putSerializable("quoteItemBean",quoteItemBean);
        IntentUtil.startActivity(this,EditNewProjectActivity.class,bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
