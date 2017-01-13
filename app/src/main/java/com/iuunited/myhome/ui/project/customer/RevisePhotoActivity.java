package com.iuunited.myhome.ui.project.customer;

import android.app.Activity;
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

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.UpLoadHeadRequest;
import com.iuunited.myhome.bean.UploadProjectUrlsRequest;
import com.iuunited.myhome.event.AddProjectEvent;
import com.iuunited.myhome.event.UploadProjectUrlEvent;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.ui.adapter.EditProjectGvAdapter;
import com.iuunited.myhome.ui.adapter.GridAdapter;
import com.iuunited.myhome.ui.home.PhotoActivity;
import com.iuunited.myhome.ui.home.ProjectThreeActivity;
import com.iuunited.myhome.ui.home.ProjectThreeFragment;
import com.iuunited.myhome.ui.home.TestPicActivity;
import com.iuunited.myhome.util.Bimp;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.FileUtils;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.ProjectCancelDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.view.View.X;
import static com.iuunited.myhome.R.id.view;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 16:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/7.
 */

public class RevisePhotoActivity extends BaseFragmentActivity implements ServiceClient.IServerRequestable {

    private static final int UPLOAD_IMAGE_SUCCESS = 0X002;
    private static final int IMAGE_UPLOAD_SUCCESS = 0X003;
    private static final int UPLOAD_PROJECT_URL_SUCCESS = 0X004;


    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private final static String TAG = "RevisePhotoActivity";
    private static final int TAKE_PICTURE = 0x000000;
    private static final int IMAGE_LOADING = 0x001;

    private Context mContext;
    private GridView gv_publish_image;

    private Uri photoUri;
    private PopupWindows mPopupWindow;
    private GridAdapter mAdapter;
    private LinearLayout ll_image;
    private GridView gv_image_url;
    private EditProjectGvAdapter mImageAdapter;
    private EditText et_description;

    private int projectId;
    private String description;
    private ArrayList<String> imageUrls;
    private TextView tv_point_out;
    private Button btn_ensure;

    private List<String> imageKeys = new ArrayList<>();
    private boolean isLocal = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_photo);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        projectId = getIntent().getIntExtra("itemId",0);
        description = getIntent().getStringExtra("decription");
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_image = (LinearLayout)findViewById(R.id.ll_image);
        gv_image_url = (GridView) findViewById(R.id.gv_image_url);
        et_description = (EditText) findViewById(R.id.et_description);
        tv_point_out = (TextView) findViewById(R.id.tv_point_out);
        btn_ensure = (Button) findViewById(R.id.btn_ensure);
        
        gv_publish_image = (GridView) findViewById(R.id.gv_publish_image);
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new GridAdapter(mContext);
        gv_publish_image.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        loadingImg();
        super.onStart();
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        btn_ensure.setOnClickListener(this);
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("加载中...");
        }
        mLoadingDialog.show();
        if(projectId!=0) {
            if(!TextUtils.isEmpty(description)) {
                et_description.setHint(description);
            }
            if(imageUrls.size()>0) {
                setImageAdapter();
                tv_point_out.setText("您可以点击下方图片重新选择图片");
            }else{
                tv_point_out.setText("您可以点击下方图片选择图片");
                ll_image.setVisibility(View.GONE);
            }
        }else{
            ToastUtils.showShortToast(this,"获取信息失败,请稍后重试!");
            this.finish();
        }


        loadingImg();
        gv_publish_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.bmp.size()) {
                    if (Bimp.bmp.size() > 0) {
                        mPopupWindow = new PopupWindows(mContext, gv_publish_image, 1);
                    } else {
                        mPopupWindow = new PopupWindows(mContext, gv_publish_image, 3);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });
    }

    private void setImageAdapter() {
        if(mImageAdapter == null) {
            mImageAdapter = new EditProjectGvAdapter(this, imageUrls);
            gv_image_url.setAdapter(mImageAdapter);
        }
        mImageAdapter.notifyDataSetChanged();
        Message message = new Message();
        message.what = UPLOAD_IMAGE_SUCCESS;
        sendUiMessage(message);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ensure:
                if(mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog(this);
                    mLoadingDialog.setMessage("修改中...");
                }
                mLoadingDialog.show();
                if(Bimp.drr.size()>0) {
                    for (int i = 0;i<Bimp.drr.size();i++){
                        uploadImage(Bimp.drr.get(i));
                    }
                }else{
                    uploadUrl();
                }
                break;
        }
    }

    private void uploadImage(final String path) {
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
                            uploadManager.put(path, null, token, new UpCompletionHandler() {
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
                        }else{
                            if(mLoadingDialog!=null) {
                                mLoadingDialog.dismiss();
                            }
                            ToastUtils.showShortToast(RevisePhotoActivity.this,"图片上传失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(RevisePhotoActivity.this,"图片上传失败,请稍后再试!");
                        return false;
                    }
                });
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
                Log.e(TAG, "图片有" + Bimp.bmp.size() + "张++++");
                setAdapter();
                break;
            case UPLOAD_IMAGE_SUCCESS:
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
                break;
            case IMAGE_UPLOAD_SUCCESS:
                uploadUrl();
                break;
            case UPLOAD_PROJECT_URL_SUCCESS:
                this.finish();
                break;
        }
    }

    private void uploadUrl() {
        final UploadProjectUrlsRequest request = new UploadProjectUrlsRequest();
        String uploadDescription = et_description.getText().toString().trim();
        if (!TextUtils.isEmpty(uploadDescription)) {
            request.setDescription(uploadDescription);
        }else{
            et_description.setText(description);
            request.setDescription(description);
        }
        if(projectId!=0) {
            request.setId(projectId);
        }else{
            ToastUtils.showShortToast(this, "发布失败，请稍后再试！");
            return;
        }
        if(imageKeys.size()>0) {
            isLocal = true;
            request.setUrls(imageKeys);
        }else{
            isLocal = false;
            request.setUrls(imageUrls);
        }
        ServiceClient.requestServer(this, "上传中...", request, UploadProjectUrlsRequest.UpdateProjectUrlsResponse.class,
                new ServiceClient.OnSimpleActionListener<UploadProjectUrlsRequest.UpdateProjectUrlsResponse>() {
                    @Override
                    public void onSuccess(UploadProjectUrlsRequest.UpdateProjectUrlsResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getIsSuccessful()) {
                            String description = request.getDescription();
                            List<String> urls = request.getUrls();
                            ArrayList<String> imageUrls = new ArrayList<>();
                            for (int i = 0;i<urls.size();i++){
                                String key = urls.get(i);
                                String imageUrl = "";
                                if(isLocal) {
                                    imageUrl = "http://oj7029k8u.bkt.clouddn.com/"+key;
                                }else{
                                    imageUrl =  key;
                                }
                                imageUrls.add(imageUrl);
                            }
                            EventBus.getDefault().post(new UploadProjectUrlEvent(description, imageUrls));
                            EventBus.getDefault().post(new AddProjectEvent(1));
                            ToastUtils.showShortToast(RevisePhotoActivity.this, "上传成功!");
                            Message message = new Message();
                            message.what = UPLOAD_PROJECT_URL_SUCCESS;
                            sendUiMessageDelayed(message,1000);
                        } else {
                            ToastUtils.showShortToast(RevisePhotoActivity.this, "上传失败,请稍后再试!");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        ToastUtils.showShortToast(RevisePhotoActivity.this, "上传失败,请稍后再试!");
                        return false;
                    }
                });
    }

    private void loadingImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.drr.size()) {
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

    public void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
        ContentValues values = new ContentValues();
        Uri uri = getContentResolver().insert(
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
                    photo();
                    dismiss();
                }
            });
            /********从自定义的相册中选择多张图片********/
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RevisePhotoActivity.this, TestPicActivity.class);
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
}
