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
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.view.FlexiListView;

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
public class ProjectAllFragment extends BaseFragments implements AdapterView.OnItemClickListener {

    private FlexiListView flv_project_all;
    private ProjectAlllvAdapter mAdapter;
    private Context mContext;

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
        if(mAdapter == null) {
            mAdapter = new ProjectAlllvAdapter(mContext);
            flv_project_all.setAdapter(mAdapter);
        }
        flv_project_all.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentUtil.startActivity(getActivity(),ProjectDetailsActivity.class);
    }
}
