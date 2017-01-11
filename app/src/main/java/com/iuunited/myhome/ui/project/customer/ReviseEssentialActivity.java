package com.iuunited.myhome.ui.project.customer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.MessagelvBean;
import com.iuunited.myhome.bean.UploadProjectGeneralRequest;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.event.UploadGeneralEvent;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.event.UserMarkerAddressEvent;
import com.iuunited.myhome.ui.home.MapActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.zip.ZipEntry;

import static com.iuunited.myhome.R.id.et_address;
import static com.iuunited.myhome.R.id.text;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 15:55
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseEssentialActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private static final int UPLOAD_PROJECT_GENERAL_SUCCESS = 0X001;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private Button btn_select_map;
    private String address;
    private String markerAddress = "";

    private EditText et_address;
    private EditText et_telephone;
    private EditText et_zipcode;
    private Button btn_upload;

    private int itemId;
    private String telePhone;
    private String oldAddress;
    private String postCode;
    private String projectName;

    private String uploadTelephone;
    private String uploadAddresss;
    private String uploadPostCode;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_essential);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
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

    private void initView() {
        itemId = getIntent().getIntExtra("itemId", this.itemId);
        telePhone = getIntent().getStringExtra("telePhone");
        oldAddress = getIntent().getStringExtra("address");
        postCode = getIntent().getStringExtra("postCode");
        projectName = getIntent().getStringExtra("projectName");
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        btn_select_map = (Button) findViewById(R.id.btn_select_map);
        et_address = (EditText)findViewById(R.id.et_address);
        et_telephone = (EditText)findViewById(R.id.et_telephone);
        et_zipcode = (EditText)findViewById(R.id.et_zipcode);
        btn_upload = (Button) findViewById(R.id.btn_upload);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(telePhone)) {
            et_telephone.setHint(telePhone);
        }
        if(!TextUtils.isEmpty(oldAddress)) {
            et_address.setHint(oldAddress);
        }
        if(!TextUtils.isEmpty(postCode)) {
            et_zipcode.setHint(postCode);
        }

        btn_select_map.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.btn_select_map:
                Intent intent = new Intent();
                intent.putExtra("class", "ReviseEssentialActivity");
                intent.setClass(ReviseEssentialActivity.this,MapActivity.class);
                startActivity(intent);
//                IntentUtil.startActivity(this, MapActivity.class);
                break;
            case R.id.btn_upload:
                uploadProject();
                break;
        }
    }

    private void uploadProject() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("修改中...");
        }
        mLoadingDialog.show();
        final UploadProjectGeneralRequest request = new UploadProjectGeneralRequest();
        String uploadTelePhone = et_telephone.getText().toString().trim();
        if(!TextUtils.isEmpty(uploadTelePhone)) {
            request.setTelephone(uploadTelePhone);
        }else{
            et_telephone.setText(telePhone);
            request.setTelephone(telePhone);
        }
        String uploadAddress = et_address.getText().toString().trim();
        if(!TextUtils.isEmpty(uploadAddress)) {
            request.setAddress(uploadAddress);
        }else{
            et_address.setText(oldAddress);
            request.setAddress(oldAddress);
        }
        String uploadZipCode = et_zipcode.getText().toString().trim();
        if(!TextUtils.isEmpty(uploadZipCode)) {
            request.setPostCode(uploadZipCode);
        }else{
            et_zipcode.setText(postCode);
            request.setPostCode(postCode);
        }
        if(itemId!=0) {
            request.setId(itemId);
        }else{
            ToastUtils.showShortToast(ReviseEssentialActivity.this, "获取信息失败,请稍后再试!");
            return;
        }
        if (!TextUtils.isEmpty(projectName)) {
            request.setName(projectName);
        }else{
            ToastUtils.showShortToast(ReviseEssentialActivity.this, "获取信息失败,请稍后再试!");
            return;
        }
        ServiceClient.requestServer(this, "修改中...", request, UploadProjectGeneralRequest.UploadProjectGeneralResponse.class,
                new ServiceClient.OnSimpleActionListener<UploadProjectGeneralRequest.UploadProjectGeneralResponse>() {
                    @Override
                    public void onSuccess(UploadProjectGeneralRequest.UploadProjectGeneralResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getIsSuccessful()) {
                            ToastUtils.showShortToast(ReviseEssentialActivity.this,"修改成功!");
                            uploadTelephone = request.getTelephone();
                            uploadAddresss = request.getAddress();
                            uploadPostCode = request.getPostCode();
//                            if(!TextUtils.isEmpty(uploadTelephone)&&!TextUtils.isEmpty(uploadAddresss)&&!TextUtils.isEmpty(uploadPostCode)) {

//                            }
                            Message message = new Message();
                            message.what = UPLOAD_PROJECT_GENERAL_SUCCESS;
                            sendUiMessage(message);
                        }else{
                            ToastUtils.showShortToast(ReviseEssentialActivity.this,"修改失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case UPLOAD_PROJECT_GENERAL_SUCCESS :
                EventBus.getDefault().post(new UploadGeneralEvent(uploadTelephone,uploadAddresss,uploadPostCode));
                EventBus.getDefault().post(new AddProjectEvent(1));
                this.finish();
                break;
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
