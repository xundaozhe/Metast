package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AddTaxBean;
import com.iuunited.myhome.bean.QuoteItemBean;
import com.iuunited.myhome.bean.QuoteTaxItemBean;
import com.iuunited.myhome.event.AddQuoteEvent;
import com.iuunited.myhome.event.AddTaxEvent;
import com.iuunited.myhome.ui.adapter.TaxAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/28 23:34
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修商编辑项目
 * Created by xundaozhe on 2016/11/28.
 */

public class EditNewProjectActivity extends BaseFragmentActivity implements TaxAdapter.ICallBack{

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private Button btn_add_tax;
    private Button btn_num_add;
    private Button btn_num_delete;
    private TextView et_num;
    private int projectNum = 1;
    private EditText et_item_name;//科目名称
    private EditText et_item_description;//描述
    private EditText et_price;//单价
    private TextView tv_total;//总价
    private Button btn_confirm;//确定


    private MyListView lv_tax;
    private TaxAdapter mAdapter;
    private List<AddTaxBean> mDatas = new ArrayList<>();
    private int itemPosition;
    private AddTaxBean itemTaxBean;
    private double unitPrice;//单价
    private List<QuoteTaxItemBean> mQuoteTaxItem = new ArrayList<>();
    private double quantity;//数量
    private double subTotal;//小计==总价
    private double amounts;//税项小计
    private String itemName;//科目名称
    private String itemDscription;//描述
    private QuoteItemBean mQuoteItemBean = new QuoteItemBean();
    private int type;
    private QuoteItemBean itemBean;
    private int changeQuote;//如果是1的话表示修改
    private int quoteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_project);
        EventBus.getDefault().register(this);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTaxChangeEvent(AddTaxEvent event){
        int type = event.type;
        AddTaxBean bean = event.bean;
        switch (type) {
            case 1 :
                mDatas.add(bean);
                getTotle();
                setAdapter();
                break;
            case 2:
                mDatas.remove(itemTaxBean);
                mDatas.add(bean);
                getTotle();
                setAdapter();
                break;
            case 3:
                mDatas.remove(itemTaxBean);
                getTotle();
                setAdapter();
                break;
        }
    }

    private void getTotle(){
        if(unitPrice!=0.0&&quantity>0.0) {
            if(mQuoteTaxItem.size()>0) {
                mQuoteTaxItem.clear();
            }
            if(subTotal>0) {
                subTotal = 0;
            }
            if(amounts>0) {
                amounts = 0;
            }
            if(mDatas.size()>0) {
                for (int i = 0;i<mDatas.size();i++){
                    AddTaxBean addTaxBean = mDatas.get(i);
                    QuoteTaxItemBean itemBean = new QuoteTaxItemBean();
                    String taxName = addTaxBean.getTaxName();
                    if(!TextUtils.isEmpty(taxName)) {
                        itemBean.setName(taxName);
                    }
                    String taxRate = addTaxBean.getTaxRate();
                    double rate = Double.parseDouble(taxRate);
                    rate = rate/100;
                    itemBean.setTaxRate(rate);
                    double amount = unitPrice * quantity * rate;
                    int taxId = addTaxBean.getId();
                    itemBean.setId(taxId);
                    itemBean.setTaxAmount(amount);
                    mQuoteTaxItem.add(itemBean);
                }
                if(mQuoteTaxItem.size()>0) {
                    for (int i = 0;i<mQuoteTaxItem.size();i++){
                        QuoteTaxItemBean quoteTaxItemBean = mQuoteTaxItem.get(i);
                        double taxAmount = quoteTaxItemBean.getTaxAmount();
                        amounts += taxAmount;
                    }
                }
            }
            subTotal = unitPrice*quantity+amounts;
            tv_total.setText("$"+subTotal);
        }
    }

    private void initView() {
        quoteId = getIntent().getIntExtra("quoteId",-1);
        changeQuote = getIntent().getIntExtra("changeQuote",-1);
        type = getIntent().getIntExtra("type",0);
        itemBean = (QuoteItemBean) getIntent().getSerializableExtra("quoteItemBean");
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        btn_add_tax = (Button)findViewById(R.id.btn_add_tax);
        btn_num_add = (Button) findViewById(R.id.btn_num_add);
        btn_num_delete = (Button) findViewById(R.id.btn_num_delete);
        et_num = (TextView)findViewById(R.id.et_num);
        et_item_name = (EditText) findViewById(R.id.et_item_name);
        et_item_description = (EditText) findViewById(R.id.et_item_description);
        et_price = (EditText) findViewById(R.id.et_price);
        tv_total = (TextView) findViewById(R.id.tv_total);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        lv_tax = (MyListView)findViewById(R.id.lv_tax);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_add_tax.setOnClickListener(this);
        btn_num_add.setOnClickListener(this);
        btn_num_delete.setOnClickListener(this);
        et_price.addTextChangedListener(mTextWatcher);
        et_num.addTextChangedListener(twNumber);
        btn_confirm.setOnClickListener(this);
        if(type == 1) {
            quantity = 1.0;
            AddTaxBean addTaxBean = new AddTaxBean();
            addTaxBean.setTaxName("个税");
            addTaxBean.setTaxRate("5");
            mDatas.add(addTaxBean);
        }else if(type == 2) {
            if(itemBean!=null) {
                String name = itemBean.getName();
                if(!TextUtils.isEmpty(name)) {
                    et_item_name.setText(name);
                }
                String description = itemBean.getDescription();
                if(!TextUtils.isEmpty(description)) {
                    et_item_description.setText(description);
                }
                double unitPrice = itemBean.getUnitPrice();
                if(unitPrice!=0.0) {
                    et_price.setText(String.valueOf(unitPrice));
                }
                double quantity = itemBean.getQuantity();
                if(quantity!=0.0) {
                    et_num.setText(String.valueOf(quantity));
                }
                List<QuoteTaxItemBean> taxItems = itemBean.getTaxItems();
                if(taxItems.size()>0) {
                    for (int i = 0;i<taxItems.size();i++){
                        QuoteTaxItemBean quoteTaxItemBean = taxItems.get(i);
                        String taxName = quoteTaxItemBean.getName();
                        double taxRate = quoteTaxItemBean.getTaxRate();
                        int taxId = quoteTaxItemBean.getId();
                        AddTaxBean addTaxBean = new AddTaxBean();
                        if(!TextUtils.isEmpty(taxName)) {
                               addTaxBean.setTaxName(taxName);
                        }
                        addTaxBean.setTaxRate(String.valueOf(taxRate*100));
                        addTaxBean.setId(taxId);
                        mDatas.add(addTaxBean);
                    }
                }else{
                    lv_tax.setVisibility(View.GONE);
                }
                double subTotal = itemBean.getSubTotal();
                if(subTotal!=0.0) {
                    tv_total.setText("$"+subTotal);
                }
            }
        }
        setAdapter();
    }

    /********数量********/
    private TextWatcher twNumber = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            quantity = Double.parseDouble(et_num.getText().toString());
            getTotle();
        }
    };

    private android.os.Handler mHandler = new android.os.Handler();
    private Runnable priceRun = new Runnable() {
        @Override
        public void run() {
            getTotle();
        }
    };

    /********总价********/
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(priceRun!=null) {
                mHandler.removeCallbacks(priceRun);
            }
            String priceStr = s.toString().trim();
            if(!TextUtils.isEmpty(priceStr)) {
                unitPrice = Double.parseDouble(priceStr);
                mHandler.postDelayed(priceRun,800);
            }
        }
    };

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new TaxAdapter(this,this,mDatas);
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
            case R.id.btn_add_tax:
                Bundle bundle = new Bundle();
                bundle.putInt("addTaxType",1);
                IntentUtil.startActivity(this,AddNewTaxActivity.class,bundle);
                break;
            case R.id.btn_num_add:
                projectNum++;
                et_num.setText(String.valueOf(projectNum));
                break;
            case R.id.btn_num_delete:
                if(projectNum>1) {
                    projectNum--;
                    et_num.setText(String.valueOf(projectNum));
                }
                break;
            case R.id.btn_confirm:
                submitIQuoteItem();
                break;
        }
    }

    private void submitIQuoteItem() {
        itemName = et_item_name.getText().toString().trim();
        if(!TextUtils.isEmpty(itemName)) {
            mQuoteItemBean.setName(itemName);
        }else{
            ToastUtils.showShortToast(this, "请输入科目名称!");
            return;
        }
        itemDscription = et_item_description.getText().toString().trim();
        if(!TextUtils.isEmpty(itemDscription)) {
            mQuoteItemBean.setDescription(itemDscription);
        }else{
            ToastUtils.showShortToast(this, "请输入描述信息!");
            return;
        }
        if(unitPrice>0.0) {
            mQuoteItemBean.setUnitPrice(unitPrice);
        }else{
            ToastUtils.showShortToast(this, "请输入单价!");
            return;
        }
        if(quantity>0.0) {
            mQuoteItemBean.setQuantity(quantity);
        }else{
            ToastUtils.showShortToast(this, "请输入数量!");
            return;
        }
        if(subTotal>0.0) {
            mQuoteItemBean.setSubTotal(subTotal);
        }
        if(mQuoteTaxItem.size()>0) {
            mQuoteItemBean.setTaxItems(mQuoteTaxItem);
        }
        if(quoteId>0) {
            mQuoteItemBean.setId(quoteId);
        }else{
            mQuoteItemBean.setId(0);
        }
        EventBus.getDefault().post(new AddQuoteEvent(mQuoteItemBean,type));
        this.finish();
    }

    @Override
    public void click(View v) {
        itemTaxBean = mDatas.get(itemPosition);
        String taxName = itemTaxBean.getTaxName();
        String taxRate = itemTaxBean.getTaxRate();
        int taxId = itemTaxBean.getId();
        Bundle bundle = new Bundle();
        bundle.putString("taxName", taxName);
        bundle.putString("taxValue", taxRate);
        bundle.putInt("addTaxType", 2);

        if(changeQuote == 1) {
            bundle.putInt("taxId",taxId);
        }
        IntentUtil.startActivity(this,AddNewTaxActivity.class,bundle);
    }

    @Override
    public void getPosition(int position) {
        itemPosition = position;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
