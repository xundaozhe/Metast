package com.iuunited.myhome.ui.project.professional;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/12 17:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/12.
 */

public class UserRatingActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private int star = R.drawable.icon_star_no;
    private int star_check = R.drawable.icon_grade;

    private int priceStarNum = 3;
    private int velocityNum = 4;
    private int valueNum = 5;

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
        setContentView(R.layout.activity_user_rating);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
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

        switchPrice(priceStarNum);
        switchVelocity(velocityNum);
        switchValue(valueNum);
    }

    private void switchPrice(int priceNum){
        if(priceNum == 1) {
            iv_price_one.setImageResource(star_check);
        } else if (priceNum == 2) {
            iv_price_one.setImageResource(star_check);
            iv_price_two.setImageResource(star_check);
        }else if(priceNum == 3) {
            iv_price_one.setImageResource(star_check);
            iv_price_two.setImageResource(star_check);
            iv_price_three.setImageResource(star_check);
        }else if(priceNum == 4) {
            iv_price_one.setImageResource(star_check);
            iv_price_two.setImageResource(star_check);
            iv_price_three.setImageResource(star_check);
            iv_price_four.setImageResource(star_check);
        }else if(priceNum == 5) {
            iv_price_one.setImageResource(star_check);
            iv_price_two.setImageResource(star_check);
            iv_price_three.setImageResource(star_check);
            iv_price_four.setImageResource(star_check);
            iv_price_five.setImageResource(star_check);
        }
    }

    private void switchVelocity(int velocityNum){
        if(velocityNum == 1) {
            iv_velocity_one.setImageResource(star_check);
        } else if (velocityNum == 2) {
            iv_velocity_one.setImageResource(star_check);
            iv_velocity_two.setImageResource(star_check);
        }else if(velocityNum == 3) {
            iv_velocity_one.setImageResource(star_check);
            iv_velocity_two.setImageResource(star_check);
            iv_velocity_three.setImageResource(star_check);
        }else if(velocityNum == 4) {
            iv_velocity_one.setImageResource(star_check);
            iv_velocity_two.setImageResource(star_check);
            iv_velocity_three.setImageResource(star_check);
            iv_velocity_four.setImageResource(star_check);
        }else if(velocityNum == 5) {
            iv_velocity_one.setImageResource(star_check);
            iv_velocity_two.setImageResource(star_check);
            iv_velocity_three.setImageResource(star_check);
            iv_velocity_four.setImageResource(star_check);
            iv_velocity_five.setImageResource(star_check);
        }
    }

    private void switchValue(int valueNum){
        if(valueNum == 1) {
            iv_value_one.setImageResource(star_check);
        } else if (valueNum == 2) {
            iv_value_one.setImageResource(star_check);
            iv_value_two.setImageResource(star_check);
        }else if(valueNum == 3) {
            iv_value_one.setImageResource(star_check);
            iv_value_two.setImageResource(star_check);
            iv_value_three.setImageResource(star_check);
        }else if(valueNum == 4) {
            iv_value_one.setImageResource(star_check);
            iv_value_two.setImageResource(star_check);
            iv_value_three.setImageResource(star_check);
            iv_value_four.setImageResource(star_check);
        }else if(valueNum == 5) {
            iv_value_one.setImageResource(star_check);
            iv_value_two.setImageResource(star_check);
            iv_value_three.setImageResource(star_check);
            iv_value_four.setImageResource(star_check);
            iv_value_five.setImageResource(star_check);
        }
    }


    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("Pawtrick Metast");
        iv_share.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
        }
    }
}
