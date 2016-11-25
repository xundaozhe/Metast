package com.iuunited.myhome.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.ui.adapter.ProjectAlllvAdapter;
import com.iuunited.myhome.ui.adapter.ProjectNewAdapter;
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
public class ProjectNewFragment extends BaseFragments {

    private FlexiListView flv_project_new;
    private ProjectNewAdapter mAdapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_new, null);
        mContext = getActivity();
        initView(view);
        return  view;
    }

    private void initView(View view) {
        flv_project_new = (FlexiListView) view.findViewById(R.id.flv_project_new);
        if(mAdapter == null) {
            mAdapter = new ProjectNewAdapter(mContext);
            flv_project_new.setAdapter(mAdapter);
        }
    }
}
