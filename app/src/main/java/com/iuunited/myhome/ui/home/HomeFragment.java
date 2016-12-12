package com.iuunited.myhome.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.project.ProjectDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.MyListView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static com.iuunited.myhome.base.MyApplication.userType;
import static com.umeng.facebook.FacebookSdk.getApplicationContext;

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
public class HomeFragment extends BaseFragments implements View.OnClickListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private SwipeRefreshLayout SwipeRefreshLayout;
    private MyListView flv_newly_project;
    private HomeNewlyAdpter mAdpter;
    private Context mContext;
    private RelativeLayout iv_back;
    private RelativeLayout rl_release_project;
    private ImageView iv_share;
    
    private ImageView iv_release_project;
    private TextView tv_publish_project;
    private TextView tv_project_depict;
    private String userType;
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            Log.d("plat","platform"+platform);
//
//            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if(t!=null){
//                Log.d("throw","throw:"+t.getMessage());
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
//        }
//    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        mContext = getActivity();
        userType = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_USERTYPE, "");
        initView(view);
        initData();
        //C:\Users\xundaozhe\Desktop\MyHomeKey
        return view;
    }

    private void initView(View view) {
        SwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        SwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        SwipeRefreshLayout.setOnRefreshListener(this);
        iv_back = (RelativeLayout) view.findViewById(R.id.iv_back);
        rl_release_project = (RelativeLayout) view.findViewById(R.id.rl_release_project);
        flv_newly_project = (MyListView) view.findViewById(R.id.flv_newly_project);
        flv_newly_project.setFocusable(false);
       
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        iv_release_project = (ImageView) view.findViewById(R.id.iv_release_project);
        tv_publish_project = (TextView) view.findViewById(R.id.tv_publish_project);
        tv_project_depict = (TextView) view.findViewById(R.id.tv_project_depict);
    }

    private void initData() {
        iv_back.setVisibility(View.GONE);
        iv_share.setOnClickListener(this);
        if (mAdpter == null) {
            mAdpter = new HomeNewlyAdpter(mContext);
            flv_newly_project.setAdapter(mAdpter);
        }
        mAdpter.notifyDataSetChanged();
        if(userType.equals("1")) {
            iv_release_project.setImageResource(R.drawable.release_project);
            tv_publish_project.setText("发布新工程");
            tv_project_depict.setText("发布一个新项目,等待装修商给您报价。");
        }else if(userType.equals("2")) {
            iv_release_project.setImageResource(R.drawable.search_project);
            tv_publish_project.setText("找装修工程");
            tv_project_depict.setText("寻找一个需要你帮助的客户。");

        }
        rl_release_project.setOnClickListener(this);

        flv_newly_project.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_release_project :
                if(userType.equals("1")) {
                    IntentUtil.startActivity(getActivity(),ReleaseProjectActivity.class);
                }else if(userType.equals("2")) {
                    IntentUtil.startActivity(getActivity(),MapActivity.class);
                }
                break;
            case R.id.iv_share:
//                IntentUtil.startActivity(getActivity(),GaoDeMapActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        //下拉刷新进行加载数据,加载完成后发送消息给handler更新界面
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(userType.equals("1")) {
            IntentUtil.startActivity(getActivity(), ProjectDetailsActivity.class);
        }else if(userType.equals("2")) {
            IntentUtil.startActivity(getActivity(),ItemProjectDetailsActivity.class);
        }
    }
}
