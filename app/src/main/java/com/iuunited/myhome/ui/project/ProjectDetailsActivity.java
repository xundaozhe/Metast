package com.iuunited.myhome.ui.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.escape.ArrayBasedUnicodeEscaper;
import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.bean.DetailsQuestionBean;
import com.iuunited.myhome.bean.ProjectInfoBean;
import com.iuunited.myhome.bean.QueryProjectDetailsRequest;
import com.iuunited.myhome.event.UpdateProjectAnswerEvent;
import com.iuunited.myhome.event.UploadGeneralEvent;
import com.iuunited.myhome.event.UploadProjectUrlEvent;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.adapter.DetailsQuestionAdapter;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.ui.adapter.ProjectEvaluateAdapter;
import com.iuunited.myhome.ui.project.customer.LoocUpDetailsActivity;
import com.iuunited.myhome.util.DateUtils;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyGridView;
import com.iuunited.myhome.view.MyListView;
import com.iuunited.myhome.view.ProjectCancelDialog;
import com.jauker.widget.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;
import static android.R.attr.phoneNumber;
import static android.R.id.list;
import static com.iuunited.myhome.R.id.gv_revise_project;
import static com.iuunited.myhome.R.id.lv_project_question;
import static com.iuunited.myhome.R.id.tv_location;
import static com.iuunited.myhome.R.id.tv_phone;
import static com.iuunited.myhome.R.id.tv_zip_code;


/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/28 18:38
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 工程---全部---用户详情页
 * Created by xundaozhe on 2016/10/28.
 */
public class ProjectDetailsActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private static final int QUERY_PROJECT_DETAILS_SUCCESS = 0X001;
    private static final int QUERY_PROJECT_DETAILS_FAILURE = 0X002;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private LinearLayout ll_edit_project;
    private LinearLayout ll_cancel_project;
    private LinearLayout ll_check_details;
    private ProjectCancelDialog mCancelDialog;

    private TextView tv_project_name;
    private TextView tv_title_name;
    private TextView tv_telephone;
    private TextView tv_address;
    private TextView tv_description;
    private TextView tv_date;

    private MyGridView gv_project_details;
    private EditProjectGvAdapter mAdapter;
    private List<String> imageUrls = new ArrayList<>();
    private MyListView lv_evaluate;
    private ProjectEvaluateAdapter mEvaluateAdapter;
    private MyListView lv_details_question;
    private List<AnswerBean> mQuestionData = new ArrayList<>();
    private DetailsQuestionAdapter mQuestionAdapter;

    private String claz = "";
    private BadgeView badgeView;
    private ImageView iv_details;
    private TextView tv_postcode;

    private String projecttName;
    private String telePhone;
    private String address;
    private String postCode;
    private long createTime;
    private String description;

    private int itemId;
    private String updateDescription;
    private List<String> updateImageUrls;
    private int questionId;
    private List<AnswerBean> updateAnswerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        EventBus.getDefault().register(this);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        ActivityCollector.finishAll();
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadGeneralEvent(UploadGeneralEvent event){
        String uploadTelePhone = event.uploadTelePhone;
        String uploadAddress = event.uploadAddress;
        String uploadZipCode = event.uploadZipCode;
        tv_telephone.setText(uploadTelePhone);
        tv_address.setText(uploadAddress);
        tv_postcode.setText(uploadZipCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadProjectUrlEvent(UploadProjectUrlEvent event){
        updateDescription = event.description;
        updateImageUrls = event.imageUrls;
        if(!TextUtils.isEmpty(updateDescription)) {
            tv_description.setText(updateDescription);
        }
        if(updateImageUrls.size()>0) {
            mAdapter = new EditProjectGvAdapter(this,updateImageUrls);
            gv_project_details.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProjectAnswerEvent(UpdateProjectAnswerEvent event){
        updateAnswerList = event.mAnswerBeanList;
        if(updateAnswerList.size()>0) {
            mQuestionAdapter = new DetailsQuestionAdapter(this, updateAnswerList);
            lv_details_question.setAdapter(mQuestionAdapter);
            mQuestionAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        itemId = getIntent().getIntExtra("itemId",0);
        createTime = getIntent().getLongExtra("itemCreateTime",0L);
        claz = getIntent().getStringExtra("underWay");
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        iv_details = (ImageView)findViewById(R.id.iv_details);
        ll_edit_project = (LinearLayout)findViewById(R.id.ll_edit_project);
        ll_cancel_project = (LinearLayout) findViewById(R.id.ll_cancel_project);
        ll_check_details = (LinearLayout)findViewById(R.id.ll_check_details);
        tv_project_name = (TextView)findViewById(R.id.tv_project_name);
        tv_title_name = (TextView)findViewById(R.id.tv_title_name);
        tv_telephone = (TextView) findViewById(R.id.tv_telephone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_postcode = (TextView) findViewById(R.id.tv_postcode);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_date = (TextView) findViewById(R.id.tv_date);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(iv_details);
        badgeView.setBadgeMargin(24,0,0,24);
        badgeView.setBadgeCount(2);
        if(claz!=null) {
            if(claz.equals("underWay")) {
                ll_edit_project.setVisibility(View.GONE);
                ll_cancel_project.setVisibility(View.GONE);
                ll_check_details.setVisibility(View.VISIBLE);
            }
        }
        lv_evaluate = (MyListView)findViewById(R.id.lv_evaluate);
        lv_details_question = (MyListView) findViewById(R.id.lv_details_question);

        gv_project_details = (MyGridView)findViewById(R.id.gv_project_details);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("工程详情");
        ll_edit_project.setOnClickListener(this);
        ll_cancel_project.setOnClickListener(this);
        ll_check_details.setOnClickListener(this);
        initProjectDetails();
        setImageAdapter();
        setEvaluateAdapter();
        lv_evaluate.setOnItemClickListener(this);
    }

    private void initProjectDetails() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        if(createTime != 0L) {
            String date = DateUtils.getDateToString(createTime);
            if(!TextUtils.isEmpty(date)) {
                tv_date.setText(date+"");
            }
        }
        QueryProjectDetailsRequest request = new QueryProjectDetailsRequest();
        request.setId(itemId);
        ServiceClient.requestServer(this, "加载中...", request, QueryProjectDetailsRequest.QueryProjectDetailsResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryProjectDetailsRequest.QueryProjectDetailsResponse>() {
                    @Override
                    public void onSuccess(QueryProjectDetailsRequest.QueryProjectDetailsResponse responseDto) {
                        Message message = new Message();
                        if (responseDto.getOperateCode() == 0) {
                            projecttName = responseDto.getName();
                            if (!TextUtils.isEmpty(projecttName)) {
                                tv_project_name.setText(projecttName);
                                tv_title_name.setText(projecttName);
                            }
                            telePhone = responseDto.getTelephone();
                            if (!TextUtils.isEmpty(telePhone)) {
                                tv_telephone.setText(telePhone);
                            }
                            address = responseDto.getAddress();
                            if (!TextUtils.isEmpty(address)) {
                                tv_address.setText(address);
                            }
                            questionId = responseDto.getCategoryId();
                            postCode = responseDto.getPostCode();
                            if (!TextUtils.isEmpty(postCode)) {
                                tv_postcode.setText(postCode);
                            }
                            List<AnswerBean> answers = responseDto.getAnswers();
                            for (int i = 0; i < answers.size(); i++) {
                                AnswerBean answerBean = answers.get(i);
                                mQuestionData.add(answerBean);
                            }
                            if (mQuestionData.size() == answers.size()) {
                                setQuestionAdapter();
                            }
                            description = responseDto.getDescription();
                            if (!TextUtils.isEmpty(description)) {
                                tv_description.setText(description);
                            } else {
                                tv_description.setVisibility(View.GONE);
                            }
                            List<String> urls = responseDto.getUrls();
                            if (urls != null && urls.size() > 0) {
                                for (int i = 0; i < urls.size(); i++) {
                                    String url = urls.get(i);
                                    imageUrls.add(url);
                                }
                                if (imageUrls.size() > 0) {
                                    if (imageUrls.size() == urls.size()) {
                                        setImageAdapter();
                                    }
                                } else {
                                    gv_project_details.setVisibility(View.GONE);
                                }
                            }
                            message.what = QUERY_PROJECT_DETAILS_SUCCESS;
                            sendUiMessage(message);
                        } else {
                            message.what = QUERY_PROJECT_DETAILS_FAILURE;
                            sendUiMessage(message);
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        Message message = new Message();
                        message.what = QUERY_PROJECT_DETAILS_FAILURE;
                        sendUiMessage(message);
                        return false;
                    }
                });
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case QUERY_PROJECT_DETAILS_SUCCESS :
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                break;
            case QUERY_PROJECT_DETAILS_FAILURE:
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                }
                ToastUtils.showShortToast(this, "加载失败,请稍后重试!");
                this.finish();
                break;
        }
    }

    private void setQuestionAdapter() {
        if(mQuestionAdapter == null) {
            mQuestionAdapter = new DetailsQuestionAdapter(this, mQuestionData);
            lv_details_question.setAdapter(mQuestionAdapter);
        }
        mQuestionAdapter.notifyDataSetChanged();
    }

    private void setEvaluateAdapter() {
        if(mEvaluateAdapter == null) {
            mEvaluateAdapter = new ProjectEvaluateAdapter(this);
            lv_evaluate.setAdapter(mEvaluateAdapter);
        }
        mEvaluateAdapter.notifyDataSetChanged();
    }

    private void setImageAdapter() {
        if(mAdapter == null) {
            mAdapter = new EditProjectGvAdapter(this,imageUrls);
            gv_project_details.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",1);
                intent.setClass(ProjectDetailsActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_edit_project:
                Bundle bundle = new Bundle();
                if(!TextUtils.isEmpty(projecttName)) {
                    bundle.putString("projectName",projecttName);
                }
                if(!TextUtils.isEmpty(telePhone)) {
                    bundle.putString("phoneNumber",telePhone);
                }
                if(!TextUtils.isEmpty(address)) {
                    bundle.putString("address",address);
                }
                if(!TextUtils.isEmpty(postCode)) {
                    bundle.putString("zipCode",postCode);
                }
                if(updateAnswerList!=null) {
                    if(updateAnswerList.size()>0) {
                        bundle.putSerializable("answerLists", (Serializable) updateAnswerList);
                    }
                }else{
                    if(mQuestionData.size()>0) {
                        bundle.putSerializable("answerLists", (Serializable) mQuestionData);
                    }
                }
                if(!TextUtils.isEmpty(updateDescription)) {
                    bundle.putString("decription",updateDescription);
                }else{
                    if(!TextUtils.isEmpty(description)) {
                        bundle.putString("decription",description);
                    }
                }
                if(updateImageUrls!=null) {
                    if(updateImageUrls.size()>0) {
                        bundle.putStringArrayList("imageUrls", (ArrayList<String>) updateImageUrls);
                    }
                }else{
                    if(imageUrls.size()>0) {
                        bundle.putStringArrayList("imageUrls", (ArrayList<String>) imageUrls);
                    }
                }
                bundle.putInt("class",1);
                if(createTime!=0L) {
                    bundle.putLong("createTime",createTime);
                }
                if(itemId!=0) {
                    bundle.putInt("itemId",itemId);
                }
                if(questionId!=0) {
                    bundle.putInt("questionId",questionId);
                }
                IntentUtil.startActivity(this,ReviseProjectActivity.class,bundle);
                break;
            case R.id.ll_cancel_project:
                mCancelDialog = new ProjectCancelDialog(this, new ICancelListener() {
                    @Override
                    public void cancelClick(int id, Context activity) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
                                finish();
                                break;
                        }
                    }
                },"取消工程","您确定要取消这个工程吗?");
                mCancelDialog.show();
                break;
            case R.id.ll_check_details:
                IntentUtil.startActivity(this,LoocUpDetailsActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentUtil.startActivity(this,QuotedPriceActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
