package com.iuunited.myhome.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 10:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/29.
 */
public class ProjectGradeActivity extends BaseFragmentActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private int star = R.drawable.icon_star_no;
    private int star_check = R.drawable.icon_grade;

    private ImageView iv_price_one;
    private ImageView iv_price_two;
    private ImageView iv_price_three;
    private ImageView iv_price_four;
    private ImageView iv_price_five;

    private ImageView iv_velocity_one,iv_velocity_two,iv_velocity_three,iv_velocity_four,iv_velocity_five;

    private ImageView iv_value_one,iv_value_two,iv_value_three,iv_value_four,iv_value_five;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_grade);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);

        iv_price_one = (ImageView)findViewById(R.id.iv_price_one);
        iv_price_two = (ImageView)findViewById(R.id.iv_price_two);
        iv_price_three = (ImageView) findViewById(R.id.iv_price_three);
        iv_price_four = (ImageView)findViewById(R.id.iv_price_four);
        iv_price_five = (ImageView)findViewById(R.id.iv_price_five);

        iv_velocity_one = (ImageView)findViewById(R.id.iv_velocity_one);
        iv_velocity_two = (ImageView)findViewById(R.id.iv_velocity_two);
        iv_velocity_three = (ImageView)findViewById(R.id.iv_velocity_three);
        iv_velocity_four = (ImageView)findViewById(R.id.iv_velocity_four);
        iv_velocity_five = (ImageView)findViewById(R.id.iv_velocity_five);

        iv_value_one = (ImageView)findViewById(R.id.iv_value_one);
        iv_value_two = (ImageView) findViewById(R.id.iv_value_two);
        iv_value_three = (ImageView) findViewById(R.id.iv_value_three);
        iv_value_four = (ImageView)findViewById(R.id.iv_value_four);
        iv_value_five = (ImageView)findViewById(R.id.iv_value_five);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("Pawtrick Metast");
        iv_share.setVisibility(View.GONE);

        iv_price_one.setOnClickListener(this);
        iv_price_two.setOnClickListener(this);
        iv_price_three.setOnClickListener(this);
        iv_price_four.setOnClickListener(this);
        iv_price_five.setOnClickListener(this);

        iv_velocity_one.setOnClickListener(this);
        iv_velocity_two.setOnClickListener(this);
        iv_velocity_three.setOnClickListener(this);
        iv_velocity_four.setOnClickListener(this);
        iv_velocity_five.setOnClickListener(this);

        iv_value_one.setOnClickListener(this);
        iv_value_two.setOnClickListener(this);
        iv_value_three.setOnClickListener(this);
        iv_value_four.setOnClickListener(this);
        iv_value_five.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                IntentUtil.startActivityAndFinish(this,ProjectFinishActivity.class);
                break;
            case R.id.iv_price_one:
                iv_price_one.setImageResource(star_check);
                iv_price_two.setImageResource(star);
                iv_price_three.setImageResource(star);
                iv_price_four.setImageResource(star);
                iv_price_five.setImageResource(star);
                break;
            case R.id.iv_price_two:
                iv_price_one.setImageResource(star_check);
                iv_price_two.setImageResource(star_check);
                iv_price_three.setImageResource(star);
                iv_price_four.setImageResource(star);
                iv_price_five.setImageResource(star);
                break;
            case R.id.iv_price_three:
                iv_price_one.setImageResource(star_check);
                iv_price_two.setImageResource(star_check);
                iv_price_three.setImageResource(star_check);
                iv_price_four.setImageResource(star);
                iv_price_five.setImageResource(star);
                break;
            case R.id.iv_price_four:
                iv_price_one.setImageResource(star_check);
                iv_price_two.setImageResource(star_check);
                iv_price_three.setImageResource(star_check);
                iv_price_four.setImageResource(star_check);
                iv_price_five.setImageResource(star);
                break;
            case R.id.iv_price_five:
                iv_price_one.setImageResource(star_check);
                iv_price_two.setImageResource(star_check);
                iv_price_three.setImageResource(star_check);
                iv_price_four.setImageResource(star_check);
                iv_price_five.setImageResource(star_check);
                break;


            case R.id.iv_velocity_one:
                iv_velocity_one.setImageResource(star_check);
                iv_velocity_two.setImageResource(star);
                iv_velocity_three.setImageResource(star);
                iv_velocity_four.setImageResource(star);
                iv_velocity_five.setImageResource(star);
                break;
            case R.id.iv_velocity_two:
                iv_velocity_one.setImageResource(star_check);
                iv_velocity_two.setImageResource(star_check);
                iv_velocity_three.setImageResource(star);
                iv_velocity_four.setImageResource(star);
                iv_velocity_five.setImageResource(star);
                break;
            case R.id.iv_velocity_three:
                iv_velocity_one.setImageResource(star_check);
                iv_velocity_two.setImageResource(star_check);
                iv_velocity_three.setImageResource(star_check);
                iv_velocity_four.setImageResource(star);
                iv_velocity_five.setImageResource(star);
                break;
            case R.id.iv_velocity_four:
                iv_velocity_one.setImageResource(star_check);
                iv_velocity_two.setImageResource(star_check);
                iv_velocity_three.setImageResource(star_check);
                iv_velocity_four.setImageResource(star_check);
                iv_velocity_five.setImageResource(star);
                break;
            case R.id.iv_velocity_five:
                iv_velocity_one.setImageResource(star_check);
                iv_velocity_two.setImageResource(star_check);
                iv_velocity_three.setImageResource(star_check);
                iv_velocity_four.setImageResource(star_check);
                iv_velocity_five.setImageResource(star_check);
                break;

            case R.id.iv_value_one:
                iv_value_one.setImageResource(star_check);
                iv_value_two.setImageResource(star);
                iv_value_three.setImageResource(star);
                iv_value_four.setImageResource(star);
                iv_value_five.setImageResource(star);
                break;
            case R.id.iv_value_two:
                iv_value_one.setImageResource(star_check);
                iv_value_two.setImageResource(star_check);
                iv_value_three.setImageResource(star);
                iv_value_four.setImageResource(star);
                iv_value_five.setImageResource(star);
                break;
            case R.id.iv_value_three:
                iv_value_one.setImageResource(star_check);
                iv_value_two.setImageResource(star_check);
                iv_value_three.setImageResource(star_check);
                iv_value_four.setImageResource(star);
                iv_value_five.setImageResource(star);
                break;
            case R.id.iv_value_four:
                iv_value_one.setImageResource(star_check);
                iv_value_two.setImageResource(star_check);
                iv_value_three.setImageResource(star_check);
                iv_value_four.setImageResource(star_check);
                iv_value_five.setImageResource(star);
                break;
            case R.id.iv_value_five:
                iv_value_one.setImageResource(star_check);
                iv_value_two.setImageResource(star_check);
                iv_value_three.setImageResource(star_check);
                iv_value_four.setImageResource(star_check);
                iv_value_five.setImageResource(star_check);
                break;
        }
    }
}
