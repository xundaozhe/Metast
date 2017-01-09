package com.iuunited.myhome.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.bean.CreateProjectRequest;
import com.iuunited.myhome.bean.UpLoadHeadRequest;
import com.iuunited.myhome.ui.adapter.GridAdapter;
import com.iuunited.myhome.ui.project.ProjectDetailsActivity;
import com.iuunited.myhome.ui.project.ReviseProjectActivity;
import com.iuunited.myhome.util.Bimp;
import com.iuunited.myhome.util.FileUtils;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.PermissionUtils;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.ProjectCancelDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.iuunited.myhome.R.id.view;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 18:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class ProjectThreeActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private final static String TAG = "ProjectThreeFragment";
    private static final int TAKE_PICTURE = 0x000000;
    private static final int IMAGE_LOADING = 0x001;
    private static final int IMAGE_UPLOAD_SUCCESS = 0X002;

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private Context mContext;
    private GridView gv_publish_image;
    private Button btn_publish;
    private Button btn_preview;

    private Uri photoUri;
    private PopupWindows mPopupWindow;
    private GridAdapter mAdapter;

    private ProjectCancelDialog mCancelDialog;
    private EditText et_description;

    private String projectName;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private double latitude;
    private double longitude;
    private String decription;

    private ArrayList<AnswerBean> mAnswerBeen;
    private List<String> imageKeys = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_three);
        ActivityCollector.addActivity(this);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        projectName = getIntent().getStringExtra("projectName");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");
        zipCode = getIntent().getStringExtra("zipCode");
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);
        mAnswerBeen = (ArrayList<AnswerBean>) getIntent().getSerializableExtra("andwerLists");
        gv_publish_image = (GridView) findViewById(R.id.gv_publish_image);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        btn_preview = (Button) findViewById(R.id.btn_preview);
        et_description = (EditText)findViewById(R.id.et_description);
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new GridAdapter(mContext);
        gv_publish_image.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        loadingImg();
        gv_publish_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.bmp.size()) {
                    if (Bimp.bmp.size() > 0) {
                        mPopupWindow = new PopupWindows(ProjectThreeActivity.this, gv_publish_image, 1);
                    } else {
                        mPopupWindow = new PopupWindows(ProjectThreeActivity.this, gv_publish_image, 3);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(ProjectThreeActivity.this, PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });
        btn_publish.setOnClickListener(this);
        btn_preview.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        loadingImg();
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                Uri selectedImage = photoUri;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                final String picturePath = cursor.getString(columnIndex);
                cursor.close();
                if (Bimp.drr.size() < 9 && resultCode == -1) {
                    Bimp.drr.add(picturePath);
                }
                break;
        }
    }

    @Override
    protected void handleUiMessage(Message msg) {
        switch (msg.what) {
            case IMAGE_LOADING:
                Log.e(TAG, "图片有" + Bimp.bmp.size() + "张++++");//图片的bitmap对象集合
                setAdapter();

                break;
            case IMAGE_UPLOAD_SUCCESS:
                createProject();
                break;
        }
    }

    private void createProject() {
        final CreateProjectRequest request = new CreateProjectRequest();
        if(!TextUtils.isEmpty(projectName)) {
            request.setName(projectName);
        }
        if(!TextUtils.isEmpty(phoneNumber)) {
            request.setTelephone(phoneNumber);
        }
        if(!TextUtils.isEmpty(address)) {
            request.setAddress(address);
        }
        if(!TextUtils.isEmpty(zipCode)) {
            request.setPostCode(zipCode);
        }
        if(latitude!=0.0) {
            request.setLatitude(latitude);
        }
        if(longitude!=0.0) {
            request.setLongitude(longitude);
        }
        decription = et_description.getText().toString().trim();
        if (!TextUtils.isEmpty(decription)) {
            request.setDescription(decription);
        }
        if(mAnswerBeen.size()>0) {
            request.setAnswers(mAnswerBeen);
        }
        if(imageKeys.size()>0) {
            request.setUrls(imageKeys);
        }
        ServiceClient.requestServer(this, "上传中...", request, CreateProjectRequest.CreateProjectResponse.class,
                new ServiceClient.OnSimpleActionListener<CreateProjectRequest.CreateProjectResponse>() {
                    @Override
                    public void onSuccess(CreateProjectRequest.CreateProjectResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getOperateCode() == 0) {
                            int projectId = responseDto.getProjectId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("itemId",projectId);
                            IntentUtil.startActivity(ProjectThreeActivity.this, ProjectDetailsActivity.class,bundle);
                        }else{
                            ToastUtils.showShortToast(ProjectThreeActivity.this,"发布失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(ProjectThreeActivity.this,"发布失败,请稍后再试!");
                        return false;
                    }
                });
    }


    private void loadingImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.drr.size()) {//图片的SD卡路径
                        Message message = new Message();
                        message.what = IMAGE_LOADING;
                        sendUiMessage(message);
                        break;
                    } else {
                        try {
                            String path = Bimp.drr.get(Bimp.max);
                            Bitmap bm = Bimp.revitionImageSize(path);
                            Bimp.bmp.add(bm);
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            if (bm != null) {
                                FileUtils.saveBitmap(bm, "" + Bimp.max);
                            }
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = IMAGE_LOADING;
                            sendUiMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Bimp.bmp.clear();
                            Bimp.drr.clear();
                            Bimp.max = 0;
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish ://发布工程
                if(Bimp.drr.size()>0) {
                    if(Bimp.max == Bimp.drr.size()) {
                        if(mLoadingDialog == null) {
                            mLoadingDialog = new LoadingDialog(this);
                            mLoadingDialog.setMessage("上传中...");
                        }
                        mLoadingDialog.show();
                        for (int i = 0;i<Bimp.drr.size();i++){
                            publishProject(Bimp.drr.get(i));
                        }
                    }
                }
//                createProject();
//                IntentUtil.startActivity(this, ProjectDetailsActivity.class);
                break;
            case R.id.btn_preview:
                IntentUtil.startActivity(this, ReviseProjectActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void publishProject(final String imagePath) {
        UpLoadHeadRequest request = new UpLoadHeadRequest();
        request.setFileType(2);
        ServiceClient.requestServer(this, "发布中...", request, UpLoadHeadRequest.UpLoadHeadResponse.class,
                new ServiceClient.OnSimpleActionListener<UpLoadHeadRequest.UpLoadHeadResponse>() {
                    @Override
                    public void onSuccess(UpLoadHeadRequest.UpLoadHeadResponse responseDto) {
                        if(responseDto.getOperateCode() == 0) {
                            String token = responseDto.getToken();
                            final String host = responseDto.getHost();
                            UploadManager uploadManager = new UploadManager();
                            uploadManager.put(imagePath, null, token, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    Log.i("qiniu", key + ",\r\n " + info + ",\r\n "
                                            + response+"-------------x"+host);
                                    try {
                                        String imageKey = response.getString("key");
                                        imageKeys.add(imageKey);
                                        if(imageKeys.size() == Bimp.drr.size()) {
                                            Message message = new Message();
                                            message.what = IMAGE_UPLOAD_SUCCESS;
                                            sendUiMessage(message);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },null);
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(ProjectThreeActivity.this,"图片上传失败,请稍后再试!");
                        return false;
                    }
                });
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

    public class PopupWindows extends PopupWindow {
        public PopupWindows(Context context, View parent, int num) {
            super(parent);
            View view = View.inflate(context, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.push_bottom_in_2));
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();
            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            if (num == 1) {
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
            } else {
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
            }
            /********调用相机拍照********/
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(PermissionUtils.isCameraPermission(ProjectThreeActivity.this,TAKE_PICTURE)) {
                        photo();
                    }
                    dismiss();
                }
            });
            /********从自定义的相册中选择多张图片********/
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjectThreeActivity.this, TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
        ContentValues values = new ContentValues();
        Uri uri = this.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        photoUri = uri;
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        Bimp.bmp.clear();
        Bimp.drr.clear();
        Bimp.max = 0;
        FileUtils.deleteDir();
        FileUtils.deleteVideoDir();
        FileUtils.deleteZipDir();
    }

}
