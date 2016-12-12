package com.iuunited.myhome.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.ui.adapter.ProCommentAdapter;
import com.iuunited.myhome.view.FlexiScrollView;
import com.iuunited.myhome.view.MyListView;

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

    private Context mContext;

    private MyListView lv_pro_comment;
    private ProCommentAdapter mAdapter;

    private LinearLayout ll_view;
    private ScrollView scrollView;

    private boolean isGone = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_grade, null);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        lv_pro_comment = (MyListView) view.findViewById(R.id.lv_pro_comment);
        ll_view = (LinearLayout) view.findViewById(R.id.ll_view);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
    }

    private void initData() {
        ll_view.setFocusable(true);
        ll_view.setFocusableInTouchMode(true);
        ll_view.requestFocus();
        setAdapter();

    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new ProCommentAdapter(mContext);
            lv_pro_comment.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }


}
