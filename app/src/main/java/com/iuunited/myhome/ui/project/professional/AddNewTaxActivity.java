package com.iuunited.myhome.ui.project.professional;

import android.content.Context;
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
import com.iuunited.myhome.bean.AddTaxBean;
import com.iuunited.myhome.event.AddTaxEvent;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.ProjectCancelDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/29 17:41
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/29.
 */
public class AddNewTaxActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private EditText et_tax_name;
    private EditText et_tax_value;
    private String taxName;
    private String taxValue;

    private Button btn_cancel;
    private Button btn_sure;
    
    private int addTax;
    private String editTaxName;
    private String editTaxValu;
    private int taxId;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tax);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        addTax = getIntent().getIntExtra("addTaxType",0);
        editTaxName = getIntent().getStringExtra("taxName");
        editTaxValu = getIntent().getStringExtra("taxValue");
        taxId = getIntent().getIntExtra("taxId",-1);
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        et_tax_name = (EditText)findViewById(R.id.et_tax_name);
        et_tax_value = (EditText)findViewById(R.id.et_tax_value);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_sure = (Button)findViewById(R.id.btn_sure);
    }

    private void initData() {
        if(addTax == 1) {
            btn_cancel.setText("取消");
        }else if(addTax == 2) {
            if(!TextUtils.isEmpty(editTaxName)) {
                et_tax_name.setText(editTaxName);
            }
            if(!TextUtils.isEmpty(editTaxValu)) {
                et_tax_value.setText(editTaxValu);
            }
        }
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_cancel.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_cancel:
                if(addTax == 1) {
                    finish();
                }else{
                    final AddTaxBean addTaxBean = new AddTaxBean();
                    ProjectCancelDialog cancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                        @Override
                        public void cancelClick(int id, Context context) {
                            switch (id) {
                                case R.id.dialog_btn_sure:
                                    EventBus.getDefault().post(new AddTaxEvent(addTaxBean, 3));
                                    AddNewTaxActivity.this.finish();
                                    break;
                            }
                        }
                    }, "删除税务", "您确定删除该条税务吗？");
                    cancelDialog.show();
                }
                break;
            case R.id.btn_sure:
                taxName = et_tax_name.getText().toString().trim();
                taxValue = et_tax_value.getText().toString().trim();
                if(TextUtils.isEmpty(taxName)) {
                    ToastUtils.showShortToast(this, "请输入要添加新税的名称!");
                    return;
                }
                if (TextUtils.isEmpty(taxValue)) {
                    ToastUtils.showShortToast(this, "请输入税率!");
                    return;
                }
                if (!TextUtils.isEmpty(taxName) && !TextUtils.isEmpty(taxValue)) {
                    AddTaxBean bean = new AddTaxBean();
                    bean.setTaxName(taxName);
                    bean.setTaxRate(taxValue);
                    if(taxId>0) {
                        bean.setId(taxId);
                    }else{
                        bean.setId(0);
                    }
                    EventBus.getDefault().post(new AddTaxEvent(bean,addTax));
                    finish();
                }
                break;
        }
    }
}
