package com.iuunited.myhome.ui.project.professional;

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
import com.iuunited.myhome.util.IntentUtil;

import static android.R.attr.button;

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

public class EditProjectActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private Button btn_add_tax;
    private Button btn_num_add;
    private Button btn_num_delete;
    private TextView et_num;
    private int projectNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        btn_add_tax = (Button)findViewById(R.id.btn_add_tax);
        btn_num_add = (Button) findViewById(R.id.btn_num_add);
        btn_num_delete = (Button) findViewById(R.id.btn_num_delete);
        et_num = (TextView)findViewById(R.id.et_num);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_add_tax.setOnClickListener(this);
        btn_num_add.setOnClickListener(this);
        btn_num_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_add_tax:
                IntentUtil.startActivity(this,AddTaxActivity.class);
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
        }
    }
}
