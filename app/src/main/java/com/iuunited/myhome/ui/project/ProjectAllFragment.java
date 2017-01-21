package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.bean.HomeNewlyBean;
import com.iuunited.myhome.bean.ProProjectSearchRequest;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.bean.QueryMyProjectRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.event.InitProjectEvent;
import com.iuunited.myhome.event.InitProjectFailureEvent;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.home.ItemProjectDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.SingleChoiceView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.id.count;
import static com.iuunited.myhome.R.id.flv_newly_project;
import static com.iuunited.myhome.R.id.query;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 17:13
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectAllFragment extends BaseFragments implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    public static final int QUERY_MY_PROJECT_SUCCESS = 0X001;
    private static final int SEARCH_PRO_PROJECTS_SUCCESS = 0X002;

    private FlexiListView flv_project_all;
    //    private ProjectAlllvAdapter mAdapter;
    private HomeNewlyAdpter mAdapter;

    private Context mContext;

    private String userType;

    private List<ProjectInfoBean> mDatas = new ArrayList<>();
    private List<ProjectInfoBean> mNewDatas = new ArrayList<>();
    private List<ProjectInfoBean> mUnderWayDatas = new ArrayList<>();
    private List<ProjectInfoBean> mFinishDatas = new ArrayList<>();

    private LoadingDialog mLoadingDialog;
    private SwipeRefreshLayout SwipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.fragment_project_all, null);
        mContext = getActivity();
        initView(view);
        initData();
        return  view;
    }

    private void initView(View view) {
        lat = getActivity().getIntent().getDoubleExtra("lat", 0.0);
        lon = getActivity().getIntent().getDoubleExtra("lon", 0.0);
        SwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        SwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        SwipeRefreshLayout.setOnRefreshListener(this);
        flv_project_all = (FlexiListView) view.findViewById(R.id.flv_project_all);

    }

    private void initData() {
        userType = DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE,0+"");
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        if (userType.equals("1")) {
            initProject();
        }else{
            proProjectSearch();
        }
        flv_project_all.setOnItemClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddProjectEvent(AddProjectEvent event){
        int state = event.state;
        if(state == 1) {
            if(userType.equals("1")) {
                initProject();
            }else{
                proProjectSearch();
            }
        }
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case QUERY_MY_PROJECT_SUCCESS :
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                setAdapter();
                SwipeRefreshLayout.setRefreshing(false);
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
                        if(responseDto.getOperateCode() == 0) {
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            if(projects.size()>0) {
                                if(mDatas.size()>0) {
                                    mDatas.clear();
                                    mNewDatas.clear();
                                    mUnderWayDatas.clear();
                                    mFinishDatas.clear();

                                }
                                for (int i = 0;i<projects.size();i++){
                                    ProjectInfoBean projectInfoBean = projects.get(i);
                                    mDatas.add(projectInfoBean);
                                    int status = projectInfoBean.getStatus();
                                    if(status == 0) {
                                        mNewDatas.add(projectInfoBean);
                                    }
                                    if(status == 1) {
                                        mUnderWayDatas.add(projectInfoBean);
                                    }
                                    if(status == 2|| status == 4) {
                                        mFinishDatas.add(projectInfoBean);
                                    }
                                }
                                EventBus.getDefault().post(new InitProjectEvent(mNewDatas,0));
                                EventBus.getDefault().post(new InitProjectEvent(mUnderWayDatas,1));
                                EventBus.getDefault().post(new InitProjectEvent(mFinishDatas,2));

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

    /**
     * 客户---我的项目
     */
    private void initProject() {
        QueryMyProjectRequest request = new QueryMyProjectRequest();
        request.setStatus(-1);
        ServiceClient.requestServer(this, "加载中...", request, QueryMyProjectRequest.QueryMyProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryMyProjectRequest.QueryMyProjectResponse>() {
                    @Override
                    public void onSuccess(QueryMyProjectRequest.QueryMyProjectResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            if(projects.size()>0) {
                                if(mDatas.size()>0) {
                                    mDatas.clear();
                                    mNewDatas.clear();
                                    mUnderWayDatas.clear();
                                    mFinishDatas.clear();

                                }
                                for (int i = 0;i<projects.size();i++){
                                    ProjectInfoBean projectInfoBean = projects.get(i);
                                    mDatas.add(projectInfoBean);
                                    int status = projectInfoBean.getStatus();
                                    if(status == 0) {
                                        mNewDatas.add(projectInfoBean);
                                    }
                                    if(status == 1) {
                                        mUnderWayDatas.add(projectInfoBean);
                                    }
                                    if(status == 2|| status == 4) {
                                        mFinishDatas.add(projectInfoBean);
                                    }
                                }
                                EventBus.getDefault().post(new InitProjectEvent(mNewDatas,0));
                                EventBus.getDefault().post(new InitProjectEvent(mUnderWayDatas,1));
                                EventBus.getDefault().post(new InitProjectEvent(mFinishDatas,2));

                            }
                            Message message = new Message();
                            message.what = QUERY_MY_PROJECT_SUCCESS;
                            sendUiMessage(message);
                        }else{
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            SwipeRefreshLayout.setRefreshing(false);
                            EventBus.getDefault().post(new InitProjectFailureEvent(-1));
                            ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        SwipeRefreshLayout.setRefreshing(false);
                        EventBus.getDefault().post(new InitProjectFailureEvent(-1));
                        ToastUtils.showShortToast(getActivity(),"获取信息失败,请稍后再试!");
                        return false;
                    }
                });
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new HomeNewlyAdpter(mContext,mDatas);
            flv_project_all.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProjectInfoBean projectInfoBean = mDatas.get(position);
        int itemId = projectInfoBean.getId();
        Bundle bundle = new Bundle();
        if(itemId != 0) {
            bundle.putInt("itemId", itemId);
        }else{
            ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
            return;
        }
        long createTime = projectInfoBean.getCreateTime();
        if(createTime != 0L) {
            bundle.putLong("itemCreateTime",createTime);
        }else{
            ToastUtils.showShortToast(getActivity(), "获取项目详情失败,请稍后重试!");
            return;
        }
        if (userType.equals("1")) {
            IntentUtil.startActivity(getActivity(), ProjectDetailsActivity.class, bundle);
        } else if (userType.equals("2")) {
            IntentUtil.startActivity(getActivity(), ProjectDetailsActivity.class, bundle);
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

    @Override
    public void onRefresh() {
        if(userType.equals("1")) {
            initProject();
        }else{
            proProjectSearch();
        }
    }
}
