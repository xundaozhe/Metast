package com.iuunited.myhome.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.home.ReleaseProjectActivity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 11:09
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/28.
 */
public class FeedBackActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private ImageView iv_share;
    private TextView tv_title;
    private int checkSelect;

    private RelativeLayout rl_propose;
    private RelativeLayout rl_report;
    private RelativeLayout rl_other;
    private ImageView iv_propose,iv_report_problem,iv_other_feedback;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_feedback);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_title = (TextView)findViewById(R.id.tv_title);
        rl_propose = (RelativeLayout)findViewById(R.id.rl_propose);
        rl_report = (RelativeLayout) findViewById(R.id.rl_report);
        rl_other = (RelativeLayout)findViewById(R.id.rl_other);
        iv_propose = (ImageView)findViewById(R.id.iv_propose);
        iv_report_problem = (ImageView)findViewById(R.id.iv_report_problem);
        iv_other_feedback = (ImageView)findViewById(R.id.iv_other_feedback);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        iv_share.setVisibility(View.GONE);
        tv_title.setText("反馈");
        rl_propose.setOnClickListener(this);
        rl_report.setOnClickListener(this);
        rl_other.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",4);
                intent.setClass(FeedBackActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_propose:
                checkSelect = 1;
                iv_propose.setImageResource(R.drawable.react_yes);
                iv_report_problem.setImageResource(R.drawable.racte);
                iv_other_feedback.setImageResource(R.drawable.racte);
                break;
            case R.id.rl_report:
                checkSelect = 2;
                iv_propose.setImageResource(R.drawable.racte);
                iv_report_problem.setImageResource(R.drawable.react_yes);
                iv_other_feedback.setImageResource(R.drawable.racte);
                break;
            case R.id.rl_other:
                checkSelect = 3;
                iv_propose.setImageResource(R.drawable.racte);
                iv_report_problem.setImageResource(R.drawable.racte);
                iv_other_feedback.setImageResource(R.drawable.react_yes);
                break;
        }
    }
}
