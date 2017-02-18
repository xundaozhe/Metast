package com.iuunited.myhome.ui.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.QueryQuestionRequest;
import com.iuunited.myhome.bean.QuestionsBean;
import com.iuunited.myhome.bean.ValidCodeRequest;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.event.ChangeProjectFmEvent;
import com.iuunited.myhome.event.QuestionEvent;
import com.iuunited.myhome.event.QuestionNameEvent;
import com.iuunited.myhome.event.UserAddressEvent;
import com.iuunited.myhome.event.UserMarkerAddressEvent;
import com.iuunited.myhome.mapdemo.OneActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.iuunited.myhome.R.id.view;
import static com.umeng.socialize.utils.BitmapUtils.init;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 17:56
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class ProjectOneActivity extends BaseFragmentActivity implements TextWatcher, ServiceClient.IServerRequestable {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

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
    public AMapLocationClient mLocationClient = null;//初始化定位
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_one);
        ActivityCollector.addActivity(this);
        EventBus.getDefault().register(this);
        mLocationClient = new AMapLocationClient(getApplicationContext());//初始化定位
        mLocationClient.setLocationListener(mLocationListener);
        initLocation();
        initView();
        initData();
    }

    private void initLocation() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(10000);
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 声明定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {

                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    address = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    EventBus.getDefault().post(new UserAddressEvent(address));
                    DefaultShared.putStringValue(getApplicationContext(), Config.CONFIG_ADDRESS,address);
                    amapLocation.getCountry();// 国家信息
                    amapLocation.getProvince();//省信息
                    String city = amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    String a= amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();//坐标纬度
                    lon = amapLocation.getLongitude();//坐标经度
//                    lat = 43.4;
//                    lon = 79.22;
                    Log.v("pcw", "lat : " + lat + " lon : " + lon);
                    Log.v("pcw", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());


                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    ToastUtils.showLongToast(ProjectOneActivity.this,"定位失败,请稍后重试!");
                }
            }
        }
    };

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        btn_next_one = (Button)  findViewById(R.id.btn_next_one);
        btn_next_one.setBackgroundResource(R.drawable.register_text_border_on);
        btn_next_one.setPadding(0,20,0,20);
        btn_next_one.setEnabled(false);
        btn_select_map = (TextView)  findViewById(R.id.btn_select_map);
        et_address = (EditText)  findViewById(R.id.et_address);
        et_address.addTextChangedListener(this);
        et_address.setFocusable(true);
        tv_project_name = (TextView)  findViewById(R.id.tv_project_name);
        tv_project_name.setOnClickListener(this);
        et_user_phone = (EditText)  findViewById(R.id.et_user_phone);
        et_user_phone.addTextChangedListener(this);
        et_zip_code = (EditText)  findViewById(R.id.et_zip_code);
        et_zip_code.addTextChangedListener(this);
    }

    private void initData() {
        btn_next_one.setOnClickListener(this);
        btn_select_map.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_one:
                if(!TextUtils.isMobileNO(userPhone)) {
                    ToastUtils.showShortToast(this, "请输入正确的手机号!");
                    return;
                }
                validZipCode();
//                EventBus.getDefault().post(new ChangeProjectFmEvent(1));
                break;
            case R.id.btn_select_map:
//                IntentUtil.startActivity(this,MapActivity.class);
                IntentUtil.startActivity(this,OneActivity.class);
                break;
            case R.id.tv_project_name:
                IntentUtil.startActivity(this,SearchQuestionActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void validZipCode() {
        ValidCodeRequest request = new ValidCodeRequest();
        if(!TextUtils.isEmpty(zipCode)) {
            request.setPostCode(zipCode);
        }else{
            ToastUtils.showShortToast(this,"请输入邮编...");
        }
        ServiceClient.requestServer(this,"加载中...",request, ValidCodeRequest.ValidCodeResponse.class,
                new ServiceClient.OnSimpleActionListener<ValidCodeRequest.ValidCodeResponse>() {
                    @Override
                    public void onSuccess(ValidCodeRequest.ValidCodeResponse responseDto) {
                        boolean isValid = responseDto.getIsValid();
                        if(isValid) {
//                            ToastUtils.showShortToast(ProjectOneActivity.this,"验证成功!!!");
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
                            ArrayList<QuestionsBean> questions = responseDto.getQuestions();
                            int questionNumber = questions.size();
                            if(questions!=null&&questionNumber>0) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("question", questions);
                                if (!TextUtils.isEmpty(projectName)) {
                                    bundle.putString("projectName",projectName);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "请选择项目名称!");
                                    return;
                                }
                                if(!TextUtils.isEmpty(userPhone)) {
                                    bundle.putString("phoneNumber",userPhone);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "请输入电话号码！");
                                    return;
                                }
                                if(!TextUtils.isEmpty(address)) {
                                    bundle.putString("address",address);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "请输入地址信息！");
                                    return;
                                }
                                if (!TextUtils.isEmpty(zipCode)) {
                                    bundle.putString("zipCode",zipCode);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "请输入邮政编码！");
                                    return;
                                }
                                if(lat!=0.0) {
                                    bundle.putDouble("latitude",lat);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "获取定位信息失败，请点击从地图获取或打开定位权限！");
                                    return;
                                }
                                if(lon!=0.0) {
                                    bundle.putDouble("longitude",lon);
                                }else{
                                    ToastUtils.showShortToast(ProjectOneActivity.this, "获取定位信息失败，请点击从地图获取或打开定位权限！");
                                    return;
                                }
                                if(questionId!=-1) {
                                    bundle.putInt("questionId",questionId);
                                }
                                bundle.putInt("questionNumber",questionNumber);
                                IntentUtil.startActivity(ProjectOneActivity.this,ProjectTwoActivity.class,bundle);
//                                EventBus.getDefault().post(new ChangeProjectFmEvent(1));
                            }
                        }else{
                            ToastUtils.showShortToast(ProjectOneActivity.this,"获取问题列表失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        return false;
                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
