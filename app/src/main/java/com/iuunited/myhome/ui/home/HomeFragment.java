package com.iuunited.myhome.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.MyListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 22:59
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/26.
 */
public class HomeFragment extends BaseFragments implements View.OnClickListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout SwipeRefreshLayout;

    private MyListView flv_newly_project;

    private HomeNewlyAdpter mAdpter;

    private Context mContext;

    private ImageView iv_back;

    private RelativeLayout rl_release_project;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        SwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        SwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        SwipeRefreshLayout.setOnRefreshListener(this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        rl_release_project = (RelativeLayout) view.findViewById(R.id.rl_release_project);
        flv_newly_project = (MyListView) view.findViewById(R.id.flv_newly_project);
        flv_newly_project.setFocusable(false);
        if (mAdpter == null) {
            mAdpter = new HomeNewlyAdpter(mContext);
            flv_newly_project.setAdapter(mAdpter);
            mAdpter.notifyDataSetChanged();
        }
    }

    private void initData() {
        iv_back.setVisibility(View.GONE);
        rl_release_project.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_release_project :
                IntentUtil.startActivity(getActivity(),ReleaseProjectActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        //下拉刷新进行加载数据,加载完成后发送消息给handler更新界面
    }
}
