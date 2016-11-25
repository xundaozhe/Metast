package com.iuunited.myhome.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.NoSwipeableViewPager;
import com.iuunited.myhome.base.ViewPagerAdapter;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 10:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class ReleaseProjectActivity extends FragmentActivity implements View.OnClickListener
{

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private NoSwipeableViewPager vp_project;
    private ArrayList<Fragment> fragments = null;
    private ProjectOneFragment mProjectOneFragment;
    private ProjectTwoFragment mProjectTwoFragment;
    private ProjectThreeFragment mProjectThreeFragment;
    private ViewPagerAdapter pagerAdapter;

    private RadioGroup rg_project;
    private RadioButton rb_project_three,rb_project_two,rb_project_one;
    private FrameLayout fragment_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_project);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePrijectFm(ChangeProjectFmEvent event){
            swichTab(event.index);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        vp_project = (NoSwipeableViewPager) findViewById(R.id.vp_project);
        fragments = new ArrayList<>();
        rg_project = (RadioGroup)findViewById(R.id.rg_project);
        rb_project_one = (RadioButton)findViewById(R.id.rb_project_one);
        rb_project_two = (RadioButton)findViewById(R.id.rb_project_two);
        rb_project_three = (RadioButton)findViewById(R.id.rb_project_three);
//        fragment_content = (FrameLayout)findViewById(R.id.fragment_content);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        mProjectOneFragment = new ProjectOneFragment();
        mProjectTwoFragment = new ProjectTwoFragment();
        mProjectThreeFragment = new ProjectThreeFragment();
        fragments.add(mProjectOneFragment);
        fragments.add(mProjectTwoFragment);
        fragments.add(mProjectThreeFragment);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragments(fragments);
        vp_project.setAdapter(pagerAdapter);
        swichTab(0);
    }

    public void swichTab(int index) {
        vp_project.setCurrentItem(index, false);
        pagerAdapter.notifyDataSetChanged();
//        rg_project.check(rg_project.getChildAt(index).getId());
        if(index == 0) {
            rb_project_one.setBackgroundResource(R.drawable.project_one_fill);
            rb_project_two.setBackgroundResource(R.drawable.project_two);
            rb_project_three.setBackgroundResource(R.drawable.project_three);
        }else if(index == 1) {
            rb_project_one.setBackgroundResource(R.drawable.project_one);
            rb_project_two.setBackgroundResource(R.drawable.project_two_fill);
            rb_project_three.setBackgroundResource(R.drawable.project_three);
        }else{
            rb_project_one.setBackgroundResource(R.drawable.project_one);
            rb_project_two.setBackgroundResource(R.drawable.project_two);
            rb_project_three.setBackgroundResource(R.drawable.project_three_fill);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",0);
                intent.setClass(ReleaseProjectActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private static List<Activity> activitys = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        if (!activitys.contains(activity)) {
            activitys.add(activity);
        }

    }

    public static void clearActivity() {
        for (Activity activity : activitys) {
            activity.finish();
        }
    }

    public static void clearOneAcitvity(Activity activity) {
        activitys.remove(activity);
    }

}
