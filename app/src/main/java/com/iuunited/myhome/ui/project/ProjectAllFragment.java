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
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.home.ItemProjectDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.SingleChoiceView;

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
public class ProjectAllFragment extends BaseFragments implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    public static final int QUERY_MY_PROJECT_SUCCESS = 0X001;

    private FlexiListView flv_project_all;
    //    private ProjectAlllvAdapter mAdapter;
    private HomeNewlyAdpter mAdapter;

    private Context mContext;

    private String userType;

//    private int count = 8;
    private List<ProjectInfoBean> mDatas = new ArrayList<>();

    private LoadingDialog mLoadingDialog;

//    private String[] titles = new String[]{"门修理", "卫生间改造", "厨房改造",
//            "制冷系统的维修", "木栅栏的安装","保洁服务","景观设计与安装","浴室装修与设计"};
//    private String[] text = new String[]{"家庭维护,外部,门修理","添加&铺面装修,卫生间改造","添加&铺面装修,厨房改造"
//            ,"空调与制冷,制冷系统的维修","栅栏、围墙,木栅栏的安装","搬家,保洁服务",
//            "景观、庭院和花园,景观设计与安装","浴室装修,浴室装修与设计"};

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
        flv_project_all = (FlexiListView) view.findViewById(R.id.flv_project_all);

    }

    private void initData() {
        userType = DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE,0+"");
        initProject();
        flv_project_all.setOnItemClickListener(this);
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

    private void initProject() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setMessage("加载中。。。");
        }
        mLoadingDialog.show();
        QueryMyProjectRequest request = new QueryMyProjectRequest();
        request.setStatus(0);
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

    private void setAdapter() {
//        for (int i = 0;i<count;i++){
//            HomeNewlyBean newlyBean = new HomeNewlyBean();
//            newlyBean.setTitle(titles[i]);
//            newlyBean.setText(text[i]);
//            mDatas.add(newlyBean);
//        }
        if (mAdapter == null) {
            mAdapter = new HomeNewlyAdpter(mContext,mDatas);
            flv_project_all.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(userType.equals("1")) {
            IntentUtil.startActivity(getActivity(),ProjectDetailsActivity.class);
        }else if(userType.equals("2")) {
            IntentUtil.startActivity(getActivity(), ItemProjectDetailsActivity.class);
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
