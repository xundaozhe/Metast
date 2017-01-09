package com.iuunited.myhome.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.event.QuestionEvent;
import com.iuunited.myhome.ui.adapter.ChoiceItemAdapter;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.SingleChoiceView;
import com.iuunited.myhome.view.StepBar;
import com.iuunited.myhome.view.StepView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.layout.fragment_project_two;
import static com.iuunited.myhome.R.layout.item_single_choice;


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

    private int images = 2;
    private LayoutInflater mInflater;

    private StepView stepBar;
    private LinearLayout ll_content;
    private LinearLayout layout;

    private TextView tv_title;
    private ListView lv_choice;

    private Button btn_next_one;
    private RelativeLayout rl_edit;

    private int index = 1;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_two, null);
        mInflater = LayoutInflater.from(getActivity());
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        /********初始化进度条指示器********/
        stepBar = (StepView) view.findViewById(R.id.stepBar);
        List<String> titles=new ArrayList<String>();
        for (int i = 0;i<new ProjectOneFragment().questionId;i++){
            titles.add("");
        }
        stepBar.setStepTitles(titles);
        stepBar.setTotalStep(new ProjectOneFragment().questionId);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        lv_choice = (ListView) view.findViewById(R.id.lv_choice);
        btn_next_one = (Button) view.findViewById(R.id.btn_next_one);
        rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
    }


    private void initData() {
        /********单选题********/
        selectSingle();
        btn_next_one.setOnClickListener(this);
    }

    private void selectSingle() {
        List<String> singleData = new ArrayList<>();
        for (int i=0;i<5;i++){
            singleData.add("singleText"+i);
        }
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_single_choice,singleData){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                SingleChoiceView view;
                if(convertView == null) {
                    view = new SingleChoiceView(getActivity());
                }else{
                    view = (SingleChoiceView) convertView;
                }
                view.setText(getItem(position));
                return view;
            }
        };
        lv_choice.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_one:
                stepBar.nextStep();
                if(index == 1) {
                   selectChoice();//多选题
                }
                if(index == 2) {
                    tv_title.setText("问答题问答题问答题问答题问答题问答题");
                    lv_choice.setVisibility(View.GONE);
                    rl_edit.setVisibility(View.VISIBLE);
                }
                if(index == 3) {
                    rl_edit.setVisibility(View.GONE);
                    lv_choice.setVisibility(View.VISIBLE);
                    tv_title.setText("单选题单选题单独安替单选题");
                    selectSingle();
                }
                if(index ==4||index>4) {
                    EventBus.getDefault().post(new ChangeProjectFmEvent(2));
                }
                index++;
                break;
        }
    }

    private void selectChoice() {
        String[] names = new String[] { "芥川龙之介", "东野圭吾", "张爱玲", "金庸" };
        tv_title.setText("多选题多选题多选题多选题多选题多选题");
//        ChoiceItemAdapter adapter = new ChoiceItemAdapter(getActivity(), names);
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        lv_choice.setAdapter(adapter);
        long[] authorsId = lv_choice.getCheckedItemIds();
        String name = "";
        String message;
        if (authorsId.length > 0) {
            // 用户至少选择了一位作家
            for (int i = 0; i < authorsId.length; i++) {
                name += "," + names[(int) authorsId[i]];
            }
            // 将第一个作家前面的“，”去掉
            message = name.substring(1);
        } else {
            message = "请至少选择一位作家！";
        }
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG)
                .show();
    }

}
