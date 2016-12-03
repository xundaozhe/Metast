package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.base.ViewPagerAdapter;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;

import java.util.ArrayList;

import static com.iuunited.myhome.R.color.textWhite;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 16:10
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 首页-----工程页面
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectFragment extends BaseFragments implements View.OnClickListener {

    private RadioGroup project_rg_banner;
    private RadioButton rb_project_all,rg_project_new,rg_project_underWay,rb_project_finish;
    private ViewPager viewPager;

    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> fragments = null;
    private ProjectAllFragment mProjectAllFragment;
    private ProjectNewFragment mProjectNewFragment;
    private ProjectUnderWayFragment mProjectUnderWayFragment;
    private ProjectFinishFragment mProjectFinishFragment;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        iv_back = (RelativeLayout) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        project_rg_banner = (RadioGroup) view.findViewById(R.id.project_rg_banner);
        rb_project_all = (RadioButton) view.findViewById(R.id.rb_project_all);
        rg_project_new = (RadioButton) view.findViewById(R.id.rg_project_new);
        rg_project_underWay = (RadioButton) view.findViewById(R.id.rg_project_underWay);
        rb_project_finish = (RadioButton) view.findViewById(R.id.rb_project_finish);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

    }

    private void initData() {
        iv_back.setVisibility(View.GONE);
        tv_title.setText("工程");
        iv_share.setVisibility(View.GONE);

        rb_project_all.setOnClickListener(this);
        rg_project_new.setOnClickListener(this);
        rg_project_underWay.setOnClickListener(this);
        rb_project_finish.setOnClickListener(this);
        initPager();
    }

    private void initPager() {
        fragments = new ArrayList<>();
        mProjectAllFragment = new ProjectAllFragment();
        mProjectNewFragment = new ProjectNewFragment();
        mProjectUnderWayFragment = new ProjectUnderWayFragment();
        mProjectFinishFragment = new ProjectFinishFragment();
        fragments.add(mProjectAllFragment);
        fragments.add(mProjectNewFragment);
        fragments.add(mProjectUnderWayFragment);
        fragments.add(mProjectFinishFragment);
        viewPager.setOffscreenPageLimit(fragments.size());
        mAdapter = new ViewPagerAdapter(getFragmentManager());
        mAdapter.setFragments(fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
        swichTab(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_project_all :
                swichTab(0);
                break;
            case R.id.rg_project_new :
                swichTab(1);
                break;
            case R.id.rg_project_underWay :
                swichTab(2);
                break;
            case R.id.rb_project_finish :
                swichTab(3);
                break;
        }
    }

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            if(project_rg_banner.getCheckedRadioButtonId() != project_rg_banner.getChildAt(position).getId()) {
                swichTab(position);
            }
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }
    private void swichTab(int index) {
        viewPager.setCurrentItem(index, true);
        mAdapter.notifyDataSetChanged();
        project_rg_banner.check(project_rg_banner.getChildAt(index).getId());
        switch (index) {
            case 0 :
                rb_project_all.setTextColor(getResources().getColor(R.color.textWhite));
                rb_project_all.setBackgroundResource(R.drawable.progect_one_shape_check);
                rg_project_new.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_new.setBackgroundResource(R.drawable.project_two_shape);
                rg_project_underWay.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_underWay.setBackgroundResource(R.drawable.project_two_shape);
                rb_project_finish.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_finish.setBackgroundResource(R.drawable.project_four_shape);
                break;
            case 1:
                rb_project_all.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_all.setBackgroundResource(R.drawable.progect_one_shape);
                rg_project_new.setTextColor(getResources().getColor(R.color.textWhite));
                rg_project_new.setBackgroundResource(R.drawable.project_two_shape_check);
                rg_project_underWay.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_underWay.setBackgroundResource(R.drawable.project_two_shape);
                rb_project_finish.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_finish.setBackgroundResource(R.drawable.project_four_shape);
                break;
            case 2:
                rb_project_all.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_all.setBackgroundResource(R.drawable.progect_one_shape);
                rg_project_new.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_new.setBackgroundResource(R.drawable.project_two_shape);
                rg_project_underWay.setTextColor(getResources().getColor(R.color.textWhite));
                rg_project_underWay.setBackgroundResource(R.drawable.project_two_shape_check);
                rb_project_finish.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_finish.setBackgroundResource(R.drawable.project_four_shape);
                break;
            case 3:
                rb_project_all.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_project_all.setBackgroundResource(R.drawable.progect_one_shape);
                rg_project_new.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_new.setBackgroundResource(R.drawable.project_two_shape);
                rg_project_underWay.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rg_project_underWay.setBackgroundResource(R.drawable.project_two_shape);
                rb_project_finish.setTextColor(getResources().getColor(R.color.textWhite));
                rb_project_finish.setBackgroundResource(R.drawable.project_four_shape_check);
                break;
        }
    }
}
