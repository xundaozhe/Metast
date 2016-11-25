package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.event.ChangeProjectFmEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 11:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class ProjectTwoFragment extends BaseFragments implements View.OnClickListener {

    private Button btn_next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_two, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next :
                EventBus.getDefault().post(new ChangeProjectFmEvent(2));
                break;
        }
    }
}
