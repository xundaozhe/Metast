package com.iuunited.myhome.ui.project.customer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.bean.MessagelvBean;
import com.iuunited.myhome.bean.OptionsBean;
import com.iuunited.myhome.bean.QuestionsBean;
import com.iuunited.myhome.bean.UpdateProjectAnswerRequest;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.event.UpdateProjectAnswerEvent;
import com.iuunited.myhome.ui.adapter.ChoiceItemAdapter;
import com.iuunited.myhome.ui.home.ProjectOneFragment;
import com.iuunited.myhome.ui.home.ProjectThreeActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.SingleChoiceView;
import com.iuunited.myhome.view.StepView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;
import static android.R.attr.phoneNumber;
import static com.iuunited.myhome.R.id.listview;
import static com.iuunited.myhome.R.id.view;
import static com.iuunited.myhome.R.id.webView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 16:28
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes H5问题页面
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseQuestionActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private static final int UPDATE_ANSWER_SUCCESS = 0X001;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private StepView stepBar;
    private TextView tv_question_title;
    private ListView lv_choice;
    private RelativeLayout rl_edit;
    private EditText et_options;
    private Button btn_next;

    private int index = 0;
    private List<QuestionsBean> questions;//问题
    private int questionNumber;
    private List<AnswerBean> answerBeans;//已有的答案
    private AnswerBean updateAnswerBean = new AnswerBean();//修改后要提交的答案
    
    private ListAdapter mSingleAdapter;//单选题时候listview的adapter
    private ChoiceItemAdapter choiceAdapter;//多选题
    private boolean isSelected = false;
    private QuestionsBean questionsBean;
    private int questionType;
    private List<AnswerBean> uploadAndwerLists = new ArrayList<>();
    private int projectId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_question);
        initView();
        initData();
    }

    private void initView() {
        projectId = getIntent().getIntExtra("projectId",0);
        questions = (List<QuestionsBean>) getIntent().getSerializableExtra("question");
        questionNumber = getIntent().getIntExtra("questionNumber", 0);
        answerBeans = (List<AnswerBean>) getIntent().getSerializableExtra("answerBeans");
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        /********初始化进度条指示器********/
        stepBar = (StepView)findViewById(R.id.stepBar);
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < questionNumber; i++) {
            titles.add("");
        }
        stepBar.setStepTitles(titles);
        stepBar.setTotalStep(questionNumber);
        tv_question_title = (TextView)findViewById(R.id.tv_question_title);
        lv_choice = (ListView) findViewById(R.id.lv_choice);
        rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);
        et_options = (EditText) findViewById(R.id.et_options);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    private void initData() {
        /********根据不同题目显示不同界面********/
        selectQuestion(index);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        lv_choice.setOnItemClickListener(this);
    }

    private void selectQuestion(int index) {
        questionsBean = questions.get(index);
        questionType = questionsBean.getQuestionType();
        String questionName = questionsBean.getQuestionName();
        int questionId = questionsBean.getId();
        AnswerBean answerBean = answerBeans.get(index);
        int answerId = answerBean.getQuestion_Id();
        String answerName = answerBean.getQuestionName();
        int answerType = answerBean.getQuestionType();

        updateAnswerBean.setQuestion_Id(questionId);
        updateAnswerBean.setQuestionName(questionName);
        updateAnswerBean.setQuestionType(questionType);
        if (questionType == 0) {//问答题
            selectInput(questionsBean,answerBean);
        }else if(questionType == 1) {
            selectSingle(questionsBean,answerBean);
        }else if (questionType == 2) {//多选题
            selectChoice(questionsBean);
        }
    }

    /**
     * 多选题
     * @param questionsBean
     */
    private void selectChoice(QuestionsBean questionsBean) {
        String questionName = questionsBean.getQuestionName();
        List<OptionsBean> options = questionsBean.getOptions();
        if(!TextUtils.isEmpty(questionName)) {
            tv_question_title.setText(questionName);
        }
        choiceAdapter = new ChoiceItemAdapter(ReviseQuestionActivity.this,options);
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_choice.setAdapter(choiceAdapter);
    }

    /**
     * 单选题
     * @param questionsBean
     * @param answerBean
     */
    private void selectSingle(QuestionsBean questionsBean, AnswerBean answerBean) {
        String questionName = questionsBean.getQuestionName();
        if (!TextUtils.isEmpty(questionName)) {
            tv_question_title.setText(questionName);
        }
        final List<OptionsBean> options = questionsBean.getOptions();
        lv_choice.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        OptionsBean bean = null;
        for (int i = 0;i<options.size();i++){
            bean = options.get(i);
            int id = bean.getId();
            int question_id = answerBean.getQuestion_Id();
            if(id == question_id) {
                break;
            }
        }
        final OptionsBean finalBean = bean;
        mSingleAdapter = new ArrayAdapter<OptionsBean>(ReviseQuestionActivity.this,R.layout.item_single_choice,options){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                SingleChoiceView view;
                if(convertView == null) {
                    view = new SingleChoiceView(ReviseQuestionActivity.this);
                }else{
                    view = (SingleChoiceView) convertView;
                }
                view.setText(options.get(position).getOptionText());
                OptionsBean optionsBean = options.get(position);
                int id = optionsBean.getId();
                int beanId = finalBean.getId();
                if(id == beanId) {
                    view.toggle();
                    view.setChecked(true);
                    view.mSingleBtn.setChecked(true);
                }
                return view;
            }
        };
        lv_choice.setAdapter(mSingleAdapter);
    }

    /**
     * 问答题
     * @param questionsBean
     * @param answerBean
     */
    private void selectInput(QuestionsBean questionsBean, AnswerBean answerBean) {
        String questionName = questionsBean.getQuestionName();
        if(!TextUtils.isEmpty(questionName)) {
            tv_question_title.setText(questionName);
        }
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
        String answerId = answerBean.getOptionIds();
        if(String.valueOf(optionId).equals(answerId)) {
            String answerValue = answerBean.getOptionValues();
            if (!TextUtils.isEmpty(answerValue)) {
                et_options.setText(answerValue);
            }
        }else{
            et_options.setHint(optionText);
        }
        updateAnswerBean.setOptionIds(optionId + "");
        updateAnswerBean.setOptionCodes(optionCode);
        lv_choice.setVisibility(View.GONE);
        rl_edit.setVisibility(View.VISIBLE);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_next:
                if(questionType == 2) {
                    isChoiceSelected();
                }
                if(questionType == 0) {
                    updateAnswerBean.setQuestionName(questionsBean.getQuestionName());
                    String optionValue = et_options.getText().toString().trim();
                    if (!TextUtils.isEmpty(optionValue)) {
                        updateAnswerBean.setOptionValues(optionValue);
                    }
                }
                if (isSelected) {
                    if(index < questions.size()) {
                        uploadAndwerLists.add(updateAnswerBean);
                    }
                    updateAnswerBean = new AnswerBean();
                    index++;
                    if(uploadAndwerLists.size() == questions.size()-1) {
                        btn_next.setText("确  定");
                    }
                    if (index != 0 && index < questions.size()) {
                        stepBar.nextStep();
                        selectQuestion(index);
//                        andwerLists.add(answerBean);
//                        answerBean = new AnswerBean();
                    } else {
//                        btn_next.setText("确  定");
                        if (uploadAndwerLists.size() > 0) {
                            if(mLoadingDialog == null) {
                                mLoadingDialog = new LoadingDialog(this);
                                mLoadingDialog.setMessage("修改中...");
                            }
                            mLoadingDialog.show();
                            updateProjectAnswer();
                        }else{
                            ToastUtils.showShortToast(ReviseQuestionActivity.this,"修改失败,请稍后再试!");
                        }
                    }
                } else {
                    ToastUtils.showShortToast(this, "请选择");
                }
                break;
        }
    }

    private void updateProjectAnswer() {
        if(projectId!=0) {
            UpdateProjectAnswerRequest request = new UpdateProjectAnswerRequest();
            request.setId(projectId);
            if(uploadAndwerLists.size()>0) {
                request.setAnswers(uploadAndwerLists);
            }else{
                ToastUtils.showShortToast(this, "修改失败,请稍后再试!");
                this.finish();
            }
            ServiceClient.requestServer(this, "修改中...", request, UpdateProjectAnswerRequest.UpdateProjectAnswerResponse.class,
                    new ServiceClient.OnSimpleActionListener<UpdateProjectAnswerRequest.UpdateProjectAnswerResponse>() {
                        @Override
                        public void onSuccess(UpdateProjectAnswerRequest.UpdateProjectAnswerResponse responseDto) {
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            if (responseDto.getIsSuccessful()) {
                                ToastUtils.showShortToast(ReviseQuestionActivity.this, "修改成功!");
                                EventBus.getDefault().post(new UpdateProjectAnswerEvent(uploadAndwerLists));
                                EventBus.getDefault().post(new AddProjectEvent(1));
                                Message message = new Message();
                                message.what = UPDATE_ANSWER_SUCCESS;
                                sendUiMessageDelayed(message, 1000);
                            } else {
                                ToastUtils.showShortToast(ReviseQuestionActivity.this, "修改失败,请稍后再试!");
                            }
                        }

                        @Override
                        public boolean onFailure(String errorMessage) {
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            ToastUtils.showShortToast(ReviseQuestionActivity.this, "修改失败,请稍后再试!");
                            return false;
                        }
                    });
        }else{
            ToastUtils.showShortToast(this, "修改失败,请稍后再试!");
            this.finish();
        }
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case UPDATE_ANSWER_SUCCESS :
                this.finish();
                break;
        }
    }

    private void isChoiceSelected() {
        if(questionType == 2) {
            if(lv_choice.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                long[] itemIds = lv_choice.getCheckedItemIds();
                if(itemIds.length>0) {
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
                    updateAnswerBean.setOptionIds(optionsIds);
                    updateAnswerBean.setOptionCodes(optionCdodes);
                    updateAnswerBean.setOptionTexts(optionTexts);
                }else{
                    isSelected = false;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isSelected = true;
        if (lv_choice.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
            OptionsBean option = (OptionsBean) mSingleAdapter.getItem(position);
            int id1 = option.getId();
            updateAnswerBean.setOptionIds(id1 + "");
            updateAnswerBean.setOptionCodes(option.getOptionCode());
            updateAnswerBean.setOptionTexts(option.getOptionText());
//            andwerLists.add(answerBean);
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
