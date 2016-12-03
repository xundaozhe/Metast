package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.adapter.ProjectUnderWayAdapter;
import com.iuunited.myhome.ui.project.professional.ProUnderWayDetailsActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;

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
public class ProjectUnderWayFragment extends BaseFragments implements AdapterView.OnItemClickListener {

    private FlexiListView flv_project_underWay;
    private ProjectUnderWayAdapter mAdapter;
    private Context mContext;

    private String userType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_underway, null);
        mContext = getActivity();
        initView(view);
        initData();
        return  view;
    }

    private void initView(View view) {
        flv_project_underWay = (FlexiListView) view.findViewById(R.id.flv_project_underWay);

    }

    private void initData() {
        if(mAdapter == null) {
            mAdapter = new ProjectUnderWayAdapter(mContext);
            flv_project_underWay.setAdapter(mAdapter);
        }
        userType = DefaultShared.getStringValue(mContext, Config.CONFIG_USERTYPE,0+"");
        flv_project_underWay.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(userType.equals("2")) {
            IntentUtil.startActivity(getActivity(),ProUnderWayDetailsActivity.class);
        }
    }
}
