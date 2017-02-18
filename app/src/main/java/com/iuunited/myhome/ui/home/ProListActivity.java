package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.ProProjectSearchRequest;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.ui.adapter.HomeNewlyAdpter;
import com.iuunited.myhome.ui.adapter.ProjectNewAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.FlexiListView;
import com.iuunited.myhome.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 19:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修人员选择工作界面
 * Created by xundaozhe on 2016/11/27.
 */
public class ProListActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private FlexiListView flv_project_new;
//    private ProjectNewAdapter mAdapter;
    private RelativeLayout iv_back;
    private TextView tv_map;
    private double lat;
    private double lon;
    private List<ProjectInfoBean> mDatas = new ArrayList<>();
    private HomeNewlyAdpter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_list);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        flv_project_new = (FlexiListView) findViewById(R.id.flv_project_new);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_map = (TextView) findViewById(R.id.tv_map);
    }

    private void initData() {
        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        proProject();
        flv_project_new.setOnItemClickListener(this);
        iv_back.setOnClickListener(this);
        tv_map.setOnClickListener(this);
    }

    private void proProject() {
        ProProjectSearchRequest request = new ProProjectSearchRequest();
        if(lat!=0.0&&lon!=0.0) {
            request.setLatitude(lat);
            request.setLongitude(lon);
            request.setRadius(0);
        }else{
            if(mLoadingDialog!=null) {
                mLoadingDialog.dismiss();
            }
            ToastUtils.showShortToast(this, "获取位置信息失败,请开启定位权限后重试!");
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
                            for (int i = 0;i<projects.size();i++){
                                ProjectInfoBean bean = projects.get(i);
                                mDatas.add(bean);
                            }
                            if(mDatas.size()>0) {
                                setAdapter();
                            }else{
                                ToastUtils.showShortToast(ProListActivity.this, "抱歉,周边暂无工程。");
                            }
                        }else{
                            ToastUtils.showShortToast(ProListActivity.this,"获取信息失败,请稍后重试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(ProListActivity.this,"获取信息失败,请稍后重试!");
                        return false;
                    }
                });
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new HomeNewlyAdpter(this, mDatas);
            flv_project_new.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.tv_map:
                finish();
                break;
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentUtil.startActivity(this,ItemProjectDetailsActivity.class);
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
