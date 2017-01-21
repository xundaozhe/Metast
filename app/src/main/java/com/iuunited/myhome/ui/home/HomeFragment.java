package com.iuunited.myhome.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.base.MyApplication;
import com.iuunited.myhome.bean.HomeNewlyBean;
import com.iuunited.myhome.bean.ProProjectSearchRequest;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.bean.QueryMyProjectRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.mapdemo.OneActivity;
import com.iuunited.myhome.mapdemo.TwoActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.project.ProjectDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.GDLocationUtil;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyListView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class HomeFragment extends BaseFragments implements View.OnClickListener, AdapterView.OnItemClickListener, ServiceClient.IServerRequestable, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private static final int QUERY_MY_PROJECT_SUCCESS = 0X001;
    private static final int SEARCH_PRO_PROJECTS_SUCCESS = 0X002;

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
    private LoadingDialog mLoadingDialog;

    private List<ProjectInfoBean> mDatas = new ArrayList<>();

    private double lat;
    private double lon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        mContext = getActivity();
        userType = DefaultShared.getStringValue(MyApplication.getContext(), Config.CONFIG_USERTYPE, "");

        initView(view);
        initData();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddProjectEvent(AddProjectEvent event){
        int state = event.state;
        if(state == 1) {
            if(userType.equals("1")) {
                queryMyProject();
            }else{
                proProjectSearch();
            }
        }
    }

    private void initView(View view) {
        lat = getActivity().getIntent().getDoubleExtra("lat", 0.0);
        lon = getActivity().getIntent().getDoubleExtra("lon", 0.0);
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
        if(userType.equals("1")) {
            iv_release_project.setImageResource(R.drawable.release_project);
            tv_publish_project.setText("发布新工程");
            tv_project_depict.setText("发布一个新项目,等待装修商给您报价。");
        }else if(userType.equals("2")) {
            iv_release_project.setImageResource(R.drawable.search_project);
            tv_publish_project.setText("找装修工程");
            tv_project_depict.setText("寻找一个需要你帮助的客户。");
        }
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        if(userType.equals("1")) {
            queryMyProject();
        }else{
            proProjectSearch();
        }
        rl_release_project.setOnClickListener(this);

        flv_newly_project.setOnItemClickListener(this);

    }

    /**
     * 装修商---附近的项目
     */
    private void proProjectSearch() {
        ProProjectSearchRequest request = new ProProjectSearchRequest();
        if (lon != 0.0&&lat!=0.0) {
            request.setLongitude(lon);
            request.setLatitude(lat);
            request.setRadius(0);
        }else{
            if(mLoadingDialog!=null) {
                mLoadingDialog.dismiss();
            }
            ToastUtils.showShortToast(getActivity(),"获取位置信息失败,请开启定位权限后重试!");
            return;
        }
        ServiceClient.requestServer(this, "加载中...", request, ProProjectSearchRequest.ProProjectSearchResponse.class,
                new ServiceClient.OnSimpleActionListener<ProProjectSearchRequest.ProProjectSearchResponse>() {
                    @Override
                    public void onSuccess(ProProjectSearchRequest.ProProjectSearchResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getOperateCode() == 0) {
                            if(mDatas.size()>0) {
                                mDatas.clear();
                            }
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            for (int i = 0;i<projects.size();i++){
                                ProjectInfoBean projectInfoBean = projects.get(i);
                                mDatas.add(projectInfoBean);
                            }
                            Message message = new Message();
                            message.what = SEARCH_PRO_PROJECTS_SUCCESS;
                            sendUiMessage(message);
                        }else{
                            if (mLoadingDialog != null) {
                                mLoadingDialog.dismiss();
                            }
                            SwipeRefreshLayout.setRefreshing(false);
                            ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                        SwipeRefreshLayout.setRefreshing(false);
                        ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        return false;
                    }
                });
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case QUERY_MY_PROJECT_SUCCESS :
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                }
                SwipeRefreshLayout.setRefreshing(false);
                if(mDatas.size()>0) {
                    setAdapter();
                }
                break;
            case SEARCH_PRO_PROJECTS_SUCCESS:
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                SwipeRefreshLayout.setRefreshing(false);
                if(mDatas.size()>0) {
                    setAdapter();
                }
                break;
        }
    }

    /**
     * 客户---我的项目
     */
    private void queryMyProject() {
        QueryMyProjectRequest request = new QueryMyProjectRequest();
        request.setStatus(0);
        ServiceClient.requestServer(this, "加载中...", request, QueryMyProjectRequest.QueryMyProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryMyProjectRequest.QueryMyProjectResponse>() {
                    @Override
                    public void onSuccess(QueryMyProjectRequest.QueryMyProjectResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            if(mDatas.size()>0) {
                                mDatas.clear();
                            }
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            for (int i = 0;i<projects.size();i++){
                                ProjectInfoBean projectInfoBean = projects.get(i);
                                mDatas.add(projectInfoBean);
                            }
//                            if(mDatas.size()>0) {
                                Message message = new Message();
                                message.what = QUERY_MY_PROJECT_SUCCESS;
                                sendUiMessage(message);
//                            }
                        }else{
                            if (mLoadingDialog != null) {
                                mLoadingDialog.dismiss();
                            }
                            SwipeRefreshLayout.setRefreshing(false);
                            ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                        SwipeRefreshLayout.setRefreshing(false);
                        ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        return false;
                    }
                });
    }

    private void setAdapter() {
        if (mAdpter == null) {
            mAdpter = new HomeNewlyAdpter(mContext,mDatas);
            flv_newly_project.setAdapter(mAdpter);
        }
        mAdpter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_release_project :
                if(userType.equals("1")) {
//                    IntentUtil.startActivity(getActivity(),ReleaseProjectActivity.class);
                    IntentUtil.startActivity(getActivity(),ProjectOneActivity.class);
                }else if(userType.equals("2")) {
                    IntentUtil.startActivity(getActivity(),MapActivity.class);
                }
                break;
            case R.id.iv_share:
//                IntentUtil.startActivity(getActivity(),GaoDeMapActivity.class);
                IntentUtil.startActivity(getActivity(),OneActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        //下拉刷新进行加载数据,加载完成后发送消息给handler更新界面
        if(mDatas.size()>0) {
            mDatas.clear();
        }
        if(userType.equals("1")) {
            queryMyProject();
        }else{
            proProjectSearch();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProjectInfoBean projectInfoBean = mDatas.get(position);
        int itemId = projectInfoBean.getId();
        Bundle bundle = new Bundle();
        if(itemId!=0) {
            bundle.putInt("itemId", itemId);
        }else{
            ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
            return;
        }
        long createTime = projectInfoBean.getCreateTime();
        if(createTime!=0L) {
            bundle.putLong("itemCreateTime", createTime);
        }else{
            ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
            return;
        }
        if(userType.equals("1")) {
//            ProjectInfoBean projectInfoBean = mDatas.get(position);
//            int itemId = projectInfoBean.getId();
//            Bundle bundle = new Bundle();
//            if(itemId!=0) {
//                bundle.putInt("itemId", itemId);
//            }else{
//                ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
//                return;
//            }
//            long createTime = projectInfoBean.getCreateTime();
//            if(createTime!=0L) {
//                bundle.putLong("itemCreateTime", createTime);
//            }else{
//                ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
//                return;
//            }
            IntentUtil.startActivity(getActivity(), ProjectDetailsActivity.class,bundle);
        }else if(userType.equals("2")) {
            IntentUtil.startActivity(getActivity(),ProjectDetailsActivity.class,bundle);
        }
    }

    @Override
    public void showLoadingDialog(String text) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showCustomToast(String text) {

    }

    @Override
    public boolean getSuccessful() {
        return false;
    }

    @Override
    public void setSuccessful(boolean isSuccessful) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
