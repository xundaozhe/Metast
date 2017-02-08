package com.iuunited.myhome.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * @author xundaozhe
 * @time 2017/2/8 14:57
 * Created by xundaozhe on 2017/2/8.
 */

public class GuideActivity extends BaseFragmentActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    private ViewPager viewPager;
    private LinearLayout indicator;
    private List<View> viewList;
    private ImageView[] indicatorimgs;
    private static final int GUIDE_PAGE_COUNT = 4;
    private int[] imgResArr = new int[]{R.drawable.guide_one,R.drawable.guide_two,R.drawable.guide_three,R.drawable.guide_four};
    private Button btn_taste;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        if(!DefaultShared.getBooleanValue(GuideActivity.this, Config.ISFIRST,false)) {
            DefaultShared.putBooleanValue(GuideActivity.this, Config.ISFIRST, true);
            initView();
            initData();
            return;
        }else{
            IntentUtil.startActivityAndFinish(this,StartActivity.class);
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (LinearLayout) findViewById(R.id.indicator);
        btn_taste = (Button) findViewById(R.id.btn_taste);
    }

    private void setIndicattor(int position) {
        for (int i = 0;i<indicatorimgs.length;i++){
//            indicatorimgs[i].setBackgroundResource(R.drawable.dots_focus);
            indicator.getChildAt(i).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dots_focus);
            if (position != i) {
                indicator.getChildAt(i).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dots_normal);
            }
        }


    }

    private void initData() {
        indicatorimgs = new ImageView[GUIDE_PAGE_COUNT];
        viewList = new ArrayList<>(GUIDE_PAGE_COUNT);
        for (int i = 0;i<GUIDE_PAGE_COUNT;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.guide_item,null);
            view.setBackgroundResource(R.color.textWhite);
            ((ImageView)view.findViewById(R.id.guide_image)).setBackgroundResource(imgResArr[i]);
            viewList.add(view);
            indicator.addView(LayoutInflater.from(this).inflate(R.layout.dot,null));
            if(i == 0) {
                indicator.getChildAt(i).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dots_focus);
            }else{
                indicator.getChildAt(i).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dots_normal);
            }

        }
        btn_taste.setOnClickListener(this);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicattor(position);
                if(position == GUIDE_PAGE_COUNT-1) {
                    btn_taste.setVisibility(View.VISIBLE);
                }else{
                    btn_taste.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_taste :
                IntentUtil.startActivityAndFinish(this,StartActivity.class);
                break;
        }
    }
}
