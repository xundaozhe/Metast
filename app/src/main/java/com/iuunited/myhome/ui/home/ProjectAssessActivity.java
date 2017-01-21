package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.ProQuoteProjectRequest;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.bean.RequoteProjectRequest;
import com.iuunited.myhome.event.AddQuoteEvent;
import com.iuunited.myhome.event.AddQuoteSuccessEvent;
import com.iuunited.myhome.ui.adapter.AddCostAdapter;
import com.iuunited.myhome.ui.project.PriceDetailsActivity;
import com.iuunited.myhome.ui.project.professional.AddNewProjectActivity;
import com.iuunited.myhome.ui.project.professional.EditNewProjectActivity;
import com.iuunited.myhome.ui.project.professional.ProItemDepotActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 18:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修商工程估价页面
 * Created by xundaozhe on 2016/11/27.
 */

public class ProjectAssessActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private TextView et_price;//小计
    private TextView tv_tax;
    private TextView tv_add_tax_price;
    private EditText et_description;
    private Button btn_submit_quote;

    private Button btn_item_depot;
    private Button btn_new_item;
    private ListView lv_quote;
    private AddCostAdapter mQuoteAdapter;
    private List<QuoteItemBean> mDatas = new ArrayList<>();
    private int positions;
    private double price;
    private double tax;
    private double addTaxPrice;
    private String description;
    private int projectId;
    private boolean isQuote;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_assess);
        EventBus.getDefault().register(this);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddQuoteEvent(AddQuoteEvent event){
        int type = event.type;
        QuoteItemBean itemBean = event.mItemBean;
        if(type == 1) {
            mDatas.add(itemBean);
            setQuoteAdapter();
            setQuote();
        }else{
            mDatas.remove(positions);
            mDatas.add(itemBean);
            setQuoteAdapter();
            setQuote();
        }
    }

    private void setQuote(){
        if(price>0) {
            price = 0;
        }
        if(tax>0) {
            tax = 0;
        }
        if(addTaxPrice>0) {
            addTaxPrice = 0;
        }
        for (int i = 0;i<mDatas.size();i++){
            QuoteItemBean quoteItemBean = mDatas.get(i);
            double unitPrice = quoteItemBean.getUnitPrice();
            price += unitPrice;
            double subTotal = quoteItemBean.getSubTotal();
            double itemTax = subTotal - unitPrice;
            tax += itemTax;
            addTaxPrice += subTotal;
        }
        et_price.setText("$" + price);
        tv_tax.setText("$" + tax);
        tv_add_tax_price.setText("$" + addTaxPrice);
    }

    private void initView() {
        projectId = getIntent().getIntExtra("projectId",0);
        isQuote = getIntent().getBooleanExtra("isQuote",false);
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        btn_item_depot = (Button)findViewById(R.id.btn_item_depot);
        btn_new_item = (Button) findViewById(R.id.btn_new_item);
        lv_quote = (ListView) findViewById(R.id.lv_quote);
        et_price = (TextView) findViewById(R.id.et_price);
        tv_tax = (TextView) findViewById(R.id.tv_tax);
        tv_add_tax_price = (TextView) findViewById(R.id.tv_add_tax_price);
        et_description = (EditText) findViewById(R.id.et_description);
        btn_submit_quote = (Button) findViewById(R.id.btn_submit_quote);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("昵称");
        iv_share.setVisibility(View.GONE);
        btn_item_depot.setOnClickListener(this);
        btn_new_item.setOnClickListener(this);
        lv_quote.setOnItemClickListener(this);
        btn_submit_quote.setOnClickListener(this);
//        setQuoteAdapter();
    }

    private void setQuoteAdapter() {
        if (mQuoteAdapter == null) {
            mQuoteAdapter = new AddCostAdapter(this, mDatas);
            lv_quote.setAdapter(mQuoteAdapter);
        }
        mQuoteAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
//                IntentUtil.startActivity(this, AddNewProjectActivity.class);
            case R.id.btn_item_depot:
                IntentUtil.startActivity(this,ProItemDepotActivity.class);
                break;
            case R.id.btn_new_item:
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                IntentUtil.startActivity(this,EditNewProjectActivity.class,bundle);
                break;
            case R.id.btn_submit_quote:
                if(isQuote) {
                    reSubmitQuote();
                }else{
                    submitQuote();
                }
                break;
        }
    }

    private void reSubmitQuote() {
        RequoteProjectRequest request = new RequoteProjectRequest();
        if(projectId!=0) {
            request.setProjectId(projectId);
        }else{
            ToastUtils.showShortToast(this, "获取信息失败,请稍后再试!");
            return;
        }
        if(tax!=0) {
            request.setTaxTotal(tax);
        }
        if(addTaxPrice!=0) {
            request.setTotal(addTaxPrice);
        }else{
            ToastUtils.showShortToast(this, "请点击添加新科目");
            return;
        }
        if(price!=0) {
            request.setSubTotal(price);
        }
        description = et_description.getText().toString().trim();
        if(!TextUtils.isEmpty(description)) {
            request.setDescription(description);
        }
        if(mDatas.size()>0) {
            request.setItems(mDatas);
        }else{
            ToastUtils.showShortToast(this, "请点击添加新科目");
            return;
        }
        ServiceClient.requestServer(this, "提交中...", request, ProQuoteProjectRequest.ProQuoteProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<ProQuoteProjectRequest.ProQuoteProjectResponse>() {
                    @Override
                    public void onSuccess(ProQuoteProjectRequest.ProQuoteProjectResponse responseDto) {
                        if(responseDto.getIsSuccessful()) {
                            ToastUtils.showShortToast(ProjectAssessActivity.this,"提交成功!");
                            EventBus.getDefault().post(new AddQuoteSuccessEvent(1));
                            ProjectAssessActivity.this.finish();
                        }else{
                            ToastUtils.showShortToast(ProjectAssessActivity.this,"提交失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        ToastUtils.showShortToast(ProjectAssessActivity.this,"提交失败,请稍后再试!");
                        return false;
                    }
                });
    }

    private void submitQuote() {
        ProQuoteProjectRequest request = new ProQuoteProjectRequest();
        if(projectId!=0) {
            request.setProjectId(projectId);
        }else{
            ToastUtils.showShortToast(this, "获取信息失败,请稍后再试!");
            return;
        }
        if(tax!=0) {
            request.setTaxTotal(tax);
        }
        if(addTaxPrice!=0) {
            request.setTotal(addTaxPrice);
        }else{
            ToastUtils.showShortToast(this, "请点击添加新科目");
            return;
        }
        if(price!=0) {
            request.setSubTotal(price);
        }
        description = et_description.getText().toString().trim();
        if(!TextUtils.isEmpty(description)) {
            request.setDescription(description);
        }
        if(mDatas.size()>0) {
            request.setItems(mDatas);
        }else{
            ToastUtils.showShortToast(this, "请点击添加新科目");
            return;
        }
        ServiceClient.requestServer(this, "提交中...", request, ProQuoteProjectRequest.ProQuoteProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<ProQuoteProjectRequest.ProQuoteProjectResponse>() {
                    @Override
                    public void onSuccess(ProQuoteProjectRequest.ProQuoteProjectResponse responseDto) {
                        if(responseDto.getIsSuccessful()) {
                            ToastUtils.showShortToast(ProjectAssessActivity.this,"提交成功!");
                            EventBus.getDefault().post(new AddQuoteSuccessEvent(1));
                            ProjectAssessActivity.this.finish();
                        }else{
                            ToastUtils.showShortToast(ProjectAssessActivity.this,"提交失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        ToastUtils.showShortToast(ProjectAssessActivity.this,"提交失败,请稍后再试!");
                        return false;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        positions = position;
        QuoteItemBean quoteItemBean = mDatas.get(positions);
        Bundle bundle = new Bundle();
        bundle.putInt("type",2);
        bundle.putSerializable("quoteItemBean",quoteItemBean);
        IntentUtil.startActivity(this,EditNewProjectActivity.class,bundle);
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
