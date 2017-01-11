package com.iuunited.myhome.ui.project;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.bean.MessagelvBean;
import com.iuunited.myhome.event.UploadGeneralEvent;
import com.iuunited.myhome.ui.adapter.DetailsQuestionAdapter;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.ui.adapter.EditQuestionAdapter;
import com.iuunited.myhome.ui.project.customer.ReviseEssentialActivity;
import com.iuunited.myhome.ui.project.customer.RevisePhotoActivity;
import com.iuunited.myhome.ui.project.customer.ReviseQuestionActivity;
import com.iuunited.myhome.util.Bimp;
import com.iuunited.myhome.util.DateUtils;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.MyGridView;
import com.iuunited.myhome.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static android.R.attr.phoneNumber;
import static android.R.string.no;
import static com.iuunited.myhome.R.id.btn_revise_two;
import static com.iuunited.myhome.R.id.main;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 14:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseProjectActivity extends BaseFragmentActivity implements EditQuestionAdapter.CallBack {

    private static final int UPLOAD_DETAILS_SUCCESS = 0X001;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private MyGridView gv_revise_project;
    private EditProjectGvAdapter mImageAdapter;
    
    private TextView tv_project_name;
    private TextView tv_phone;
    private TextView tv_location;
    private TextView tv_zip_code;
    private TextView tv_date;
    private TextView tv_description;

    private Button btn_revise_one;
    private Button btn_revise_two;
    private Button btn_revise_three;
    private MyListView lv_project_question;
    private DetailsQuestionAdapter mQuestionAdapter;
    
    private String projectName;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private ArrayList<AnswerBean> mAnswerBeens;
    private String decription;
    private int claz;
    private ArrayList<String> imageUrls;
    private long createTime;
    private int itemId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_project);
        EventBus.getDefault().register(this);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        itemId = getIntent().getIntExtra("itemId",0);
        projectName = getIntent().getStringExtra("projectName");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        zipCode = getIntent().getStringExtra("zipCode");
        address = getIntent().getStringExtra("address");
        mAnswerBeens = (ArrayList<AnswerBean>) getIntent().getSerializableExtra("answerLists");
        decription = getIntent().getStringExtra("decription");
        claz = getIntent().getIntExtra("class",0);
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        createTime = getIntent().getLongExtra("createTime",0L);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        gv_revise_project = (MyGridView)findViewById(R.id.gv_revise_project);
        tv_project_name = (TextView) findViewById(R.id.tv_project_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_zip_code = (TextView) findViewById(R.id.tv_zip_code);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_description = (TextView) findViewById(R.id.tv_description);
        
        btn_revise_one = (Button)findViewById(R.id.btn_revise_one);
        btn_revise_two = (Button)findViewById(R.id.btn_revise_two);
        btn_revise_three = (Button) findViewById(R.id.btn_revise_three);
        lv_project_question = (MyListView)findViewById(R.id.lv_project_question);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadGeneralEvent(UploadGeneralEvent event){
        String uploadTelePhone = event.uploadTelePhone;
        String uploadAddress = event.uploadAddress;
        String uploadZipCode = event.uploadZipCode;
        tv_phone.setText(uploadTelePhone);
        tv_location.setText(uploadAddress);
        tv_zip_code.setText(uploadZipCode);
    }

    private void initData() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        if(!TextUtils.isEmpty(projectName)) {
            tv_project_name.setText(projectName);
        }
        if(!TextUtils.isEmpty(phoneNumber)) {
            tv_phone.setText(phoneNumber);
        }
        if(!TextUtils.isEmpty(zipCode)) {
            tv_zip_code.setText(zipCode);
        }
        if(!TextUtils.isEmpty(address)) {
            tv_location.setText(address);
        }
        if(claz != 0) {
            if(createTime != 0L) {
                String date = DateUtils.getDateToString(createTime);
                if(!TextUtils.isEmpty(date)) {
                    tv_date.setText(date+"");
                }
            }
            if(imageUrls!=null) {
                if(imageUrls.size()>0) {
                    setImageAdapters();
                    btn_revise_three.setVisibility(View.VISIBLE);
                }else{
                    gv_revise_project.setVisibility(View.GONE);
                }
            }else{
                gv_revise_project.setVisibility(View.GONE);
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
            }
            btn_revise_one.setVisibility(View.VISIBLE);
            btn_revise_two.setVisibility(View.VISIBLE);

        }else{
            String date = DateUtils.getCurrentDate();
            if(!TextUtils.isEmpty(date)) {
                tv_date.setText(date);
            }
            if(Bimp.drr.size()>0) {
                if(claz == 0) {
                    btn_revise_three.setVisibility(View.GONE);
                }else{
                    btn_revise_three.setVisibility(View.VISIBLE);
                }
                setImageAdapter();
            }else{
                gv_revise_project.setVisibility(View.GONE);
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
            }
        }
        if(mAnswerBeens.size()>0) {
            setQuestionAdapter();
        }

        if (!TextUtils.isEmpty(decription)) {
            tv_description.setText(decription);
        }else{
            tv_description.setVisibility(View.GONE);
        }
        iv_back.setOnClickListener(this);
        if(claz != 0) {
            tv_title.setText("工程");
        }else{
            tv_title.setText("预览");
        }
        iv_share.setVisibility(View.GONE);

        btn_revise_one.setOnClickListener(this);
        btn_revise_two.setOnClickListener(this);
        btn_revise_three.setOnClickListener(this);
    }

    private void setImageAdapters() {
        if(mImageAdapter == null) {
            mImageAdapter = new EditProjectGvAdapter(this,imageUrls);
            gv_revise_project.setAdapter(mImageAdapter);
        }
        mImageAdapter.notifyDataSetChanged();
        Message message = new Message();
        message.what = UPLOAD_DETAILS_SUCCESS;
        sendUiMessage(message);
    }

    private void setImageAdapter() {
        if(mImageAdapter == null) {
            mImageAdapter = new EditProjectGvAdapter(this,Bimp.drr);
            gv_revise_project.setAdapter(mImageAdapter);
        }
        mImageAdapter.notifyDataSetChanged();
        Message message = new Message();
        message.what = UPLOAD_DETAILS_SUCCESS;
        sendUiMessage(message);
    }

    private void setQuestionAdapter() {
        if(mQuestionAdapter == null) {
            mQuestionAdapter = new DetailsQuestionAdapter(this,mAnswerBeens);
            lv_project_question.setAdapter(mQuestionAdapter);
        }
        mQuestionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case UPLOAD_DETAILS_SUCCESS :
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                break;
            case R.id.btn_revise_one:
                Bundle bundle = new Bundle();
                bundle.putString("projectName", projectName);
                bundle.putString("telePhone", phoneNumber);
                bundle.putString("address", address);
                bundle.putString("postCode", zipCode);
                bundle.putInt("itemId",itemId);
                IntentUtil.startActivity(this, ReviseEssentialActivity.class,bundle);
                break;
            case R.id.btn_revise_two:

                //项目描述    图片数组  TODO
                IntentUtil.startActivity(this, ReviseQuestionActivity.class);
                break;
            case R.id.btn_revise_three:
                Bundle questionBundle = new Bundle();
                questionBundle.putInt("itemId", itemId);
                questionBundle.putString("decription", decription);
                questionBundle.putStringArrayList("imageUrls",imageUrls);
                IntentUtil.startActivity(this, RevisePhotoActivity.class);
                break;
        }
    }

    @Override
    public void click(View v) {
        IntentUtil.startActivity(this, ReviseQuestionActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
