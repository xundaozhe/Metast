package com.iuunited.myhome.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.ui.adapter.MessageLvAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/14 14:51
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 首页---消息页面
 * Created by xundaozhe on 2016/11/14.
 */

public class MessageFragment extends BaseFragments implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private SwipeRefreshLayout SwipeRefreshLayout;
    private FlexiListView message_flexLv;
    private MessageLvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        SwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);

        message_flexLv = (FlexiListView) view.findViewById(R.id.message_flexLv);
        initAdapter();
    }

    private void initAdapter() {
        if(mAdapter == null) {
            mAdapter = new MessageLvAdapter(getActivity());
            message_flexLv.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        iv_back.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        tv_title.setText("消息");
        SwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        SwipeRefreshLayout.setOnRefreshListener(this);
        message_flexLv.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        //下拉刷新加载数据,完成后发送消息给handler处理
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentUtil.startActivity(getActivity(),MessageChatActivity.class);
    }
}
