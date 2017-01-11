package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.bean.HomeNewlyBean;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.bean.QueryMyProjectRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.InitProjectEvent;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.adapter.ProjectFinishAdapter;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.id.flv_project_all;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 18:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectFinishFragment extends BaseFragments implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    public static final int QUERY_MY_PROJECT_SUCCESS = 0X001;
    private LoadingDialog mLoadingDialog;

    private FlexiListView flv_project_finish;
    //    private ProjectFinishAdapter mAdapter;
    private HomeNewlyAdpter mAdapter;

    private Context mContext;

    private String userType;

    private int count = 2;
    private List<ProjectInfoBean> mDatas = new ArrayList<>();
    private int[] projects = new int[]{2,4};

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_finish, null);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        flv_project_finish = (FlexiListView) view.findViewById(R.id.flv_project_finish);

    }

    private void initData() {
        userType = DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE, 0 + "");
//        for (int i = 0;i<projects.length;i++){
//            initProject(projects[i]);
//        }
        flv_project_finish.setOnItemClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInitEvent(InitProjectEvent event){
        int states = event.states;
        if(states == 2) {
            ProjectInfoBean datas = event.mDatas;
            mDatas.add(datas);
            if(mDatas.size()>0) {
                setAdapter();
            }else{
                flv_project_finish.setVisibility(View.GONE);
            }
        }
    }

    private void initProject(int state) {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        QueryMyProjectRequest request = new QueryMyProjectRequest();
        request.setStatus(state);
        ServiceClient.requestServer(this, "加载中...", request, QueryMyProjectRequest.QueryMyProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryMyProjectRequest.QueryMyProjectResponse>() {
                    @Override
                    public void onSuccess(QueryMyProjectRequest.QueryMyProjectResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            if(projects.size()>0) {
                                for (int i = 0;i<projects.size();i++){
                                    ProjectInfoBean projectInfoBean = projects.get(i);
                                    mDatas.add(projectInfoBean);
                                }
                            }
                            Message message = new Message();
                            message.what = QUERY_MY_PROJECT_SUCCESS;
                            sendUiMessage(message);
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        return false;
                    }
                });
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
                break;
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new HomeNewlyAdpter(mContext,mDatas);
            flv_project_finish.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (userType.equals("1")) {
            IntentUtil.startActivity(getActivity(), ProjectFinishActivity.class);
        } else if (userType.equals("2")) {
            IntentUtil.startActivity(getActivity(), ProjectFinishActivity.class);
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
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
