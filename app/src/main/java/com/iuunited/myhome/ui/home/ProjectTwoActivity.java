package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.bean.OptionsBean;
import com.iuunited.myhome.bean.QuestionsBean;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.ui.adapter.ChoiceItemAdapter;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.SingleChoiceView;
import com.iuunited.myhome.view.StepView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.phoneNumber;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 18:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class ProjectTwoActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener {

    private RelativeLayout iv_back;
    private TextView title;
    private ImageView iv_share;

    private int questionNumber = 2;
    private LayoutInflater mInflater;

    private StepView stepBar;
    private LinearLayout ll_content;
    private LinearLayout layout;

    private TextView tv_title;
    private ListView lv_choice;
    private EditText et_options;

    private Button btn_next_one;
    private RelativeLayout rl_edit;

    private int index = 0;
    private List<QuestionsBean> lists;
    private QuestionsBean questionsBean;
    private ListAdapter adapter;//单选题时候listview的adapter
    private ChoiceItemAdapter choiceAdapter;//多选题
    private List<AnswerBean> andwerLists = new ArrayList<>();
    private boolean isSelected = false;
    private AnswerBean answerBean = new AnswerBean();
    private int questionType;
    private String projectName;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private double latitude;
    private double longitude;
    private int questionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_two);
        ActivityCollector.addActivity(this);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        /********初始化进度条指示器********/
        lists = (List<QuestionsBean>) getIntent().getSerializableExtra("question");
        et_options = (EditText) findViewById(R.id.et_options);
        questionNumber = getIntent().getIntExtra("questionNumber", 0);
        projectName = getIntent().getStringExtra("projectName");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");
        zipCode = getIntent().getStringExtra("zipCode");
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);
        questionId = getIntent().getIntExtra("questionId",-1);
        stepBar = (StepView) findViewById(R.id.stepBar);
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < questionNumber; i++) {
            titles.add("");
        }
        stepBar.setStepTitles(titles);
        stepBar.setTotalStep(questionNumber);

        tv_title = (TextView) findViewById(R.id.tv_question_title);
        lv_choice = (ListView) findViewById(R.id.lv_choice);
        btn_next_one = (Button) findViewById(R.id.btn_next_one);
        rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);

    }

    private void initData() {
        selectQuestion(index);
        btn_next_one.setOnClickListener(this);
        lv_choice.setOnItemClickListener(this);

        iv_back.setOnClickListener(this);
        title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_one:
                if (questionType == 2) {
                    isChoiceSelected();
                }
                if(questionType == 0) {
                    answerBean.setQuestionName(questionsBean.getQuestionName());
                    String optionValue = et_options.getText().toString().trim();
                    if (!TextUtils.isEmpty(optionValue)) {
                        answerBean.setOptionValues(optionValue);
                    }
                }
                if (isSelected) {
                    if(index < lists.size()) {
                        andwerLists.add(answerBean);
                    }
                    answerBean = new AnswerBean();
                    index++;
                    if (index != 0 && index < lists.size()) {
                        stepBar.nextStep();
                        selectQuestion(index);
//                        andwerLists.add(answerBean);
//                        answerBean = new AnswerBean();
                    } else {
                        Bundle bundle = new Bundle();
                        if (andwerLists.size() > 0) {
                            bundle.putSerializable("andwerLists", (Serializable) andwerLists);
                        }
                        if (!TextUtils.isEmpty(projectName)) {
                            bundle.putString("projectName", projectName);
                        }
                        if (!TextUtils.isEmpty(phoneNumber)) {
                            bundle.putString("phoneNumber", phoneNumber);
                        }
                        if (!TextUtils.isEmpty(address)) {
                            bundle.putString("address", address);
                        }
                        if (!TextUtils.isEmpty(zipCode)) {
                            bundle.putString("zipCode", zipCode);
                        }
                        if (latitude != 0.0) {
                            bundle.putDouble("latitude", latitude);
                        }
                        if (longitude != 0.0) {
                            bundle.putDouble("longitude", longitude);
                        }
                        if(questionId!=-1) {
                            bundle.putInt("questionId",questionId);
                        }
                        IntentUtil.startActivity(this, ProjectThreeActivity.class, bundle);
                    }
                } else {
                    ToastUtils.showShortToast(this, "请选择");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void isChoiceSelected() {
        if (questionType == 2) {
            if (lv_choice.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                long[] itemIds = lv_choice.getCheckedItemIds();
                if (itemIds.length > 0) {
                    isSelected = true;
                    OptionsBean bean;
                    String optionsIds = "";
                    String optionCdodes = "";
                    String optionTexts = "";
                    for (int i = 0; i < itemIds.length; i++) {
                        long itemId = itemIds[i];
                        bean = (OptionsBean) choiceAdapter.getItem((int) itemId);
                        optionsIds += "," + bean.getId();
                        optionCdodes += "," + bean.getOptionCode();
                        optionTexts += "," + bean.getOptionText();
                    }
                    optionsIds = optionsIds.substring(1);
                    optionCdodes = optionCdodes.substring(1);
                    optionTexts = optionTexts.substring(1);
                    answerBean.setOptionIds(optionsIds);
                    answerBean.setOptionCodes(optionCdodes);
                    answerBean.setOptionTexts(optionTexts);
//                andwerLists.add(answerBean);
                } else {
                    isSelected = false;
                }
            }
        }
    }

    /********
     * 根据问题类型显示不同页面
     ********/
    private void selectQuestion(int index) {
        questionsBean = lists.get(index);
        questionType = questionsBean.getQuestionType();
        int questionsId = questionsBean.getId();
        String questionName = questionsBean.getQuestionName();
        answerBean.setQuestion_Id(questionsId);
        answerBean.setQuestionName(questionName);
        answerBean.setQuestionType(questionType);
        if (questionType == 0) {//问答题
            selectInput(questionsBean);
        } else if (questionType == 1) {//单选题
            selectSingle(questionsBean);
        } else if (questionType == 2) {//多选题
            selectChoice(questionsBean);
        }
    }

    /********
     * 单选题
     *
     * @param questionsBean
     ********/
    private void selectSingle(QuestionsBean questionsBean) {
        String questionName = questionsBean.getQuestionName();
        tv_title.setText(questionName);
        final List<OptionsBean> options = questionsBean.getOptions();
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ArrayAdapter<OptionsBean>(ProjectTwoActivity.this, R.layout.item_single_choice, options) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                SingleChoiceView view;
                if (convertView == null) {
                    view = new SingleChoiceView(ProjectTwoActivity.this);
                } else {
                    view = (SingleChoiceView) convertView;
                }
                view.setText(options.get(position).getOptionText());
                return view;
            }
        };
        lv_choice.setAdapter(adapter);
    }

    /********
     * 问答题
     *
     * @param questionsBean
     ********/
    private void selectInput(QuestionsBean questionsBean) {
        String questionName = questionsBean.getQuestionName();
        tv_title.setText(questionName);
        List<OptionsBean> options = questionsBean.getOptions();
        int optionId = 0;
        String optionCode = "";
        String optionText = "";
        for (int i = 0; i < options.size(); i++) {
            OptionsBean bean = options.get(i);
            optionId = bean.getId();
            optionCode = bean.getOptionCode();
            optionText = bean.getOptionText();
        }
        answerBean.setOptionIds(optionId + "");
        answerBean.setOptionCodes(optionCode);
        lv_choice.setVisibility(View.GONE);
        rl_edit.setVisibility(View.VISIBLE);
        et_options.setHint(optionText);
//        andwerLists.add(answerBean);
    }

    /*******
     * 多选题
     *
     * @param questionsBean
     *********/
    private void selectChoice(QuestionsBean questionsBean) {
        String questionName = questionsBean.getQuestionName();
        List<OptionsBean> options = questionsBean.getOptions();
        tv_title.setText(questionName);
        choiceAdapter = new ChoiceItemAdapter(ProjectTwoActivity.this, options);
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_choice.setAdapter(choiceAdapter);
//        isChoiceSelected();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isSelected = true;
        if (lv_choice.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
            OptionsBean option = (OptionsBean) adapter.getItem(position);
            int id1 = option.getId();
            answerBean.setOptionIds(id1 + "");
            answerBean.setOptionCodes(option.getOptionCode());
            answerBean.setOptionTexts(option.getOptionText());
//            andwerLists.add(answerBean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
