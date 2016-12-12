package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AddTaxBean;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;

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

    private TextView tv_cancel;
    private TextView btn_sure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tax);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        et_tax_name = (EditText)findViewById(R.id.et_tax_name);
        et_tax_value = (EditText)findViewById(R.id.et_tax_value);
        tv_cancel = (TextView)findViewById(R.id.tv_cancel);
        btn_sure = (TextView)findViewById(R.id.btn_sure);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        tv_cancel.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                IntentUtil.startActivityAndFinish(this,AddTaxActivity.class);
                break;
            case R.id.tv_cancel:
                IntentUtil.startActivityAndFinish(this,AddTaxActivity.class);
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
                    bean.setTaxValue(taxValue);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newTax",bean);
                    IntentUtil.startActivity(this, AddTaxActivity.class, bundle);
                    finish();
                }
                break;
        }
    }
}
