package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.content.Intent;
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
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.adapter.ProjectUnderWayAdapter;
import com.iuunited.myhome.ui.home.ItemProjectDetailsActivity;
import com.iuunited.myhome.ui.project.professional.ProUnderWayDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.id.flv_project_all;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 18:06
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 工程进行中
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectUnderWayFragment extends BaseFragments implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    public static final int QUERY_MY_PROJECT_SUCCESS = 0X001;
    private LoadingDialog mLoadingDialog;

    private FlexiListView flv_project_underWay;
    //    private ProjectUnderWayAdapter mAdapter;
    private HomeNewlyAdpter mAdapter;

    private Context mContext;

    private String userType;

    private int count = 4;
    private List<ProjectInfoBean> mDatas = new ArrayList<>();

//    private String[] titles = new String[]{"厨房改造",
//            "制冷系统的维修", "木栅栏的安装","保洁服务","景观设计与安装","浴室装修与设计"};
//    private String[] text = new String[]{"添加&铺面装修,厨房改造"
//            ,"空调与制冷,制冷系统的维修","栅栏、围墙,木栅栏的安装","搬家,保洁服务"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_underway, null);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        flv_project_underWay = (FlexiListView) view.findViewById(R.id.flv_project_underWay);

    }

    private void initData() {
        initProject();
        userType = DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE, 0 + "");
        flv_project_underWay.setOnItemClickListener(this);
    }

    private void initProject() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        QueryMyProjectRequest request = new QueryMyProjectRequest();
        request.setStatus(1);
        ServiceClient.requestServer(this, "加载中...", request, QueryMyProjectRequest.QueryMyProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryMyProjectRequest.QueryMyProjectResponse>() {
                    @Override
                    public void onSuccess(QueryMyProjectRequest.QueryMyProjectResponse responseDto) {
                        if (responseDto.getOperateCode() == 0) {
                            List<ProjectInfoBean> projects = responseDto.getProjects();
                            if (projects.size() > 0) {
                                for (int i = 0; i < projects.size(); i++) {
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
//        for (int i = 0;i<count;i++){
//            HomeNewlyBean newlyBean = new HomeNewlyBean();
//            newlyBean.setTitle(titles[i]);
//            newlyBean.setText(text[i]);
//            mDatas.add(newlyBean);
//        }
        if (mAdapter == null) {
            mAdapter = new HomeNewlyAdpter(mContext, mDatas);
            flv_project_underWay.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (userType.equals("2")) {
            Intent intent = new Intent();
            intent.putExtra("underWay", "underWay");
            intent.setClass(getActivity(), ItemProjectDetailsActivity.class);
            startActivity(intent);
        } else if (userType.equals("1")) {
            Intent intent = new Intent();
            intent.putExtra("underWay", "underWay");
            intent.setClass(getActivity(), ProjectDetailsActivity.class);
            startActivity(intent);
//            IntentUtil.startActivity(getActivity(),ProjectDetailsActivity.class);
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
}
