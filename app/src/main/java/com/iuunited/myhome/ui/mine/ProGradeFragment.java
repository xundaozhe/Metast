package com.iuunited.myhome.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 16:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class ProGradeFragment extends BaseFragments {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_grade,null);
        return view;
    }
}
