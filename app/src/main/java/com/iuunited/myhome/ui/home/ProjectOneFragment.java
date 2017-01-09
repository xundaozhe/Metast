package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.bean.FilteredItems;
import com.iuunited.myhome.bean.QueryQuestionRequest;
import com.iuunited.myhome.bean.QuestionsBean;
import com.iuunited.myhome.bean.SearchQuestionRequest;
import com.iuunited.myhome.bean.ValidCodeRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.event.QuestionEvent;
import com.iuunited.myhome.event.QuestionNameEvent;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.event.UserMarkerAddressEvent;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 10:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class ProjectOneFragment extends BaseFragments implements View.OnClickListener, TextWatcher, ServiceClient.IServerRequestable {

    private Button btn_next_one;
    private TextView btn_select_map;
    private String address;
    private String markerAddress = "";

    private EditText et_address;
    private TextView tv_project_name;
    private EditText et_user_phone;
    private EditText et_zip_code;

    private LoadingDialog mLoadingDialog;
    public int questionId = 1;
    private String projectName;
    private String userPhone;
    private String zipCode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_one,null);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userAddress(UserAddressEvent event){
        address = event.address;
        if(markerAddress.equals("")) {
            if(!TextUtils.isEmpty(address)) {
                et_address.setText(address);
            }
        } else{
            et_address.setText(markerAddress);
        }
        if(!TextUtils.isEmpty(address)&&!markerAddress.equals("")) {
            et_address.setText(address);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userMarkerAddress(UserMarkerAddressEvent event){
        markerAddress = event.markerAddress;
        if(!markerAddress.equals("")) {
            et_address.setText(markerAddress);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void questionName(QuestionNameEvent event){
        questionId = event.id;
        projectName= event.name;
        if(!TextUtils.isEmpty(projectName)) {
            tv_project_name.setText(projectName);
            tv_project_name.setTextColor(getResources().getColor(R.color.textBlack));
        }
    }

    private void initView(View view) {
        btn_next_one = (Button) view.findViewById(R.id.btn_next_one);
        btn_next_one.setBackgroundResource(R.drawable.register_text_border_on);
        btn_next_one.setPadding(0,20,0,20);
        btn_next_one.setEnabled(false);
        btn_select_map = (TextView) view.findViewById(R.id.btn_select_map);
        et_address = (EditText) view.findViewById(R.id.et_address);
        et_address.addTextChangedListener(this);
        et_address.setFocusable(true);
        tv_project_name = (TextView) view.findViewById(R.id.tv_project_name);
        tv_project_name.setOnClickListener(this);
        et_user_phone = (EditText) view.findViewById(R.id.et_user_phone);
        et_user_phone.addTextChangedListener(this);
        et_zip_code = (EditText) view.findViewById(R.id.et_zip_code);
        et_zip_code.addTextChangedListener(this);

    }

    private void initData() {
        btn_next_one.setOnClickListener(this);
        btn_select_map.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_one:
                if(!TextUtils.isMobileNO(userPhone)) {
                    ToastUtils.showShortToast(getActivity(), "请输入正确的手机号!");
                    return;
                }
                validZipCode();
//                EventBus.getDefault().post(new ChangeProjectFmEvent(1));
                break;
            case R.id.btn_select_map:
                IntentUtil.startActivity(getActivity(),MapActivity.class);
                break;
            case R.id.tv_project_name:
                IntentUtil.startActivity(getActivity(),SearchQuestionActivity.class);
                break;
        }
    }

    private void validZipCode() {
        ValidCodeRequest request = new ValidCodeRequest();
        if(!TextUtils.isEmpty(zipCode)) {
            request.setPostCode(zipCode);
        }else{
            ToastUtils.showShortToast(getActivity(),"请输入邮编...");
        }
        ServiceClient.requestServer(this,"加载中...",request, ValidCodeRequest.ValidCodeResponse.class,
                new ServiceClient.OnSimpleActionListener<ValidCodeRequest.ValidCodeResponse>() {
                    @Override
                    public void onSuccess(ValidCodeRequest.ValidCodeResponse responseDto) {
                        boolean isValid = responseDto.getIsValid();
                        if(isValid) {
                            ToastUtils.showShortToast(getActivity(),"验证成功!!!");
                            queryQuestion();
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        return false;
                    }
                });

    }

    private void queryQuestion() {
        QueryQuestionRequest request = new QueryQuestionRequest();
        request.setId(questionId);
        ServiceClient.requestServer(this,"加载中...",request, QueryQuestionRequest.QueryQuestionResponse.class,
                new ServiceClient.OnSimpleActionListener<QueryQuestionRequest.QueryQuestionResponse>() {
                    @Override
                    public void onSuccess(QueryQuestionRequest.QueryQuestionResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            List<QuestionsBean> questions = responseDto.getQuestions();
                            int questionNumber = questions.size();
                            if(questions!=null&&questionNumber>0) {
//                                EventBus.getDefault().post(new QuestionEvent(questionNumber));
                                EventBus.getDefault().post(new ChangeProjectFmEvent(1));
                            }
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        return false;
                    }
                });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(tv_project_name) && !TextUtils.isEmpty(et_user_phone) && !TextUtils.isEmpty(et_address) && !TextUtils.isEmpty(et_zip_code)) {
            userPhone = et_user_phone.getText().toString().trim();
            zipCode = et_zip_code.getText().toString().trim();
            btn_next_one.setBackgroundResource(R.drawable.send_idfcode);
            btn_next_one.setPadding(0, 20, 0, 20);
            btn_next_one.setEnabled(true);
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
