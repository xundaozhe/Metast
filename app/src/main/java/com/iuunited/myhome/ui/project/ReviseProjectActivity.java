package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.ui.adapter.EditQuestionAdapter;
import com.iuunited.myhome.ui.project.customer.ReviseEssentialActivity;
import com.iuunited.myhome.ui.project.customer.RevisePhotoActivity;
import com.iuunited.myhome.ui.project.customer.ReviseQuestionActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.MyGridView;
import com.iuunited.myhome.view.MyListView;

import static android.R.string.no;
import static com.iuunited.myhome.R.id.btn_revise_two;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 14:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseProjectActivity extends BaseFragmentActivity implements EditQuestionAdapter.CallBack {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private MyGridView gv_revise_project;
    private EditProjectGvAdapter mAdapter;
    
    private Button btn_revise_one;
//    private Button btn_revise_two;
    private Button btn_revise_three;
    private MyListView lv_project_question;
    private EditQuestionAdapter mQuestionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_project);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        gv_revise_project = (MyGridView)findViewById(R.id.gv_revise_project);
        
        btn_revise_one = (Button)findViewById(R.id.btn_revise_one);
//        btn_revise_two = (Button)findViewById(btn_revise_two);
        btn_revise_three = (Button) findViewById(R.id.btn_revise_three);
        lv_project_question = (MyListView)findViewById(R.id.lv_project_question);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("工程");
        iv_share.setOnClickListener(this);
//        setAdapter();

        btn_revise_one.setOnClickListener(this);
//        btn_revise_two.setOnClickListener(this);
        btn_revise_three.setOnClickListener(this);
        setQuestionAdapter();
    }

    private void setQuestionAdapter() {
        if(mQuestionAdapter == null) {
            mQuestionAdapter = new EditQuestionAdapter(this,this);
            lv_project_question.setAdapter(mQuestionAdapter);
        }
        mQuestionAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        if(mAdapter == null) {
//            mAdapter = new EditProjectGvAdapter(this);
            gv_revise_project.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                break;
            case R.id.btn_revise_one:
                IntentUtil.startActivity(this, ReviseEssentialActivity.class);
                break;
//            case R.id.btn_revise_two:
//                IntentUtil.startActivity(this, ReviseQuestionActivity.class);
//                break;
            case R.id.btn_revise_three:
                IntentUtil.startActivity(this, RevisePhotoActivity.class);
                break;
        }
    }

    @Override
    public void click(View v) {
        IntentUtil.startActivity(this, ReviseQuestionActivity.class);
    }
}
