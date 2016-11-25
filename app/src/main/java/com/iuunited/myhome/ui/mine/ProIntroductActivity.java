package com.iuunited.myhome.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.base.ViewPagerAdapter;
import com.iuunited.myhome.util.SDCardUtil;
import com.iuunited.myhome.util.SdcardConfig;
import com.iuunited.myhome.view.LoadingDialog;
import com.iuunited.myhome.view.RoundImageView;
import com.iuunited.myhome.view.SelectPhotoDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 10:29
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 装修商简介
 * Created by xundaozhe on 2016/10/31.
 */
public class ProIntroductActivity extends BaseFragmentActivity {

    private final static int REQUEST_CODE_PIC = 1001;
    private final static int REQUEST_CODE_TAKE_PHOTO = 1002;
    private static final int REQUEST_CODE_TAKE_PHOTO_CROP = 1003;

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private EditText edit_username;
    private TextView tv_username;
    private TextView tv_edit;
    private TextView tv_submit;

    private RadioGroup pro_rg_banner;
    private RadioButton rb_pro_details,rb_pro_grade;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> fragments = null;

    private ProDetailsFragment mProDetailsFragment;
    private ProGradeFragment mProGradeFragment;
    
    private RoundImageView iv_user_head;
    private SelectPhotoDialog mPhotoDialog;
    private File mSdcardTempFile;// 相册中的图片
    private Bitmap mCurBitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_introduct);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        edit_username = (EditText) findViewById(R.id.edit_username);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_edit = (TextView)findViewById(R.id.tv_edit);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        iv_user_head = (RoundImageView)findViewById(R.id.iv_user_head);

        pro_rg_banner = (RadioGroup) findViewById(R.id.pro_rg_banner);
        rb_pro_details = (RadioButton) findViewById(R.id.rb_pro_details);
        rb_pro_grade = (RadioButton) findViewById(R.id.rb_pro_grade);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        iv_user_head.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        rb_pro_details.setOnClickListener(this);
        rb_pro_grade.setOnClickListener(this);
        initPager();
    }

    private void initPager() {
        fragments = new ArrayList<>();
        mProDetailsFragment = new ProDetailsFragment();
        mProGradeFragment = new ProGradeFragment();
        fragments.add(mProDetailsFragment);
        fragments.add(mProGradeFragment);
        viewPager.setOffscreenPageLimit(fragments.size());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.setFragments(fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
        swichTab(0);
    }

    private void swichTab(int index) {
        viewPager.setCurrentItem(index, true);
        mAdapter.notifyDataSetChanged();
        pro_rg_banner.check(pro_rg_banner.getChildAt(index).getId());
        switch (index) {
            case 0:
                rb_pro_details.setTextColor(getResources().getColor(R.color.textWhite));
                rb_pro_details.setBackgroundResource(R.drawable.progect_one_shape_check);
                rb_pro_grade.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_pro_grade.setBackgroundResource(R.drawable.project_four_shape);
                break;
            case 1:
                rb_pro_details.setTextColor(getResources().getColor(R.color.myHomeBlue));
                rb_pro_details.setBackgroundResource(R.drawable.progect_one_shape);
                rb_pro_grade.setTextColor(getResources().getColor(R.color.textWhite));
                rb_pro_grade.setBackgroundResource(R.drawable.project_four_shape_check);
                break;
        }}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.rb_pro_details:
                swichTab(0);
                break;
            case R.id.rb_pro_grade:
                swichTab(1);
                break;
            case R.id.iv_user_head:
                /********仿Ios样式的dialog,访问本机相册还是拍照********/
                showHeadImage();
                break;
            case R.id.tv_edit:
                tv_edit.setVisibility(View.GONE);
                tv_submit.setVisibility(View.VISIBLE);
                tv_username.setVisibility(View.GONE);
                edit_username.setVisibility(View.VISIBLE);
                edit_username.setText(tv_username.getText());
                break;
            case R.id.tv_submit:
                tv_submit.setVisibility(View.GONE);
                tv_edit.setVisibility(View.VISIBLE);
                edit_username.setVisibility(View.GONE);
                tv_username.setText(edit_username.getText());
                tv_username.setVisibility(View.VISIBLE);
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                    break;
        }
    }


    private void showHeadImage() {
        if(mPhotoDialog == null) {
            mPhotoDialog = new SelectPhotoDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tv_photo :
                            // 手机相册
                            if (!SDCardUtil.hasSDCard()) {
                                showToast("手机没有sd卡，请您先检查一下");
                                return;
                            }
                            mSdcardTempFile = getFile(SdcardConfig.PHOTO_FOLDER
                                    + SystemClock.currentThreadTimeMillis()
                                    + ".jpg");
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_PIC);
                            break;
                        case R.id.tv_camera: {
                            // 拍照
                            // 拍照
                            if (!SDCardUtil.hasSDCard()) {
                                showToast("手机没有sd卡，请您先检查一下");
                                return;
                            }
                            // 选择拍照
                            Intent cameraintent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            // 指定调用相机拍照后照片的储存路径
                            mSdcardTempFile = getFile(SdcardConfig.PHOTO_FOLDER
                                    + SystemClock.currentThreadTimeMillis() + SDCardUtil.getPhotoFileName());
                            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(mSdcardTempFile));
                            startActivityForResult(cameraintent,
                                    REQUEST_CODE_TAKE_PHOTO);
                            break;
                        }
                    }
                }
            });
        }
        mPhotoDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        updateHead();
//        Picasso.with(this).load(imageUrl).into(riv_info_head);
        if (requestCode == REQUEST_CODE_PIC && data != null) {// 手机相册
            startPhotoZoom(data.getData(),Uri.fromFile(mSdcardTempFile));
//            updateHead();
//            Picasso.with(this).load(imageUrl).into(riv_info_head);
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {// 拍照
            // 选择拍照
            startPhotoZoom(Uri.fromFile(mSdcardTempFile),Uri.fromFile(mSdcardTempFile));
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO_CROP && data != null) {// 拍照之后裁剪的
            // 裁切大图使用Uri
            Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(mSdcardTempFile));// decode
            // bitmap
            try {
                mCurBitmap = bitmap;
                // 上传图片
                if (mCurBitmap != null) {
                    updateHead();
//                    Picasso.with(this).load(imageUrl).into(riv_info_head);
                }
            } catch (Exception e) {
                showToast("上传头像失败");
            }
        }
    }

    private void updateHead() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("上传中...");
        }
        mLoadingDialog.show();
        String base64ImageArray = "";
        if (mCurBitmap != null) {
            base64ImageArray = SDCardUtil.bitmapToBase64(mCurBitmap);
        } else {
            showToast("上传失败");
            return;
        }
        //TODO  进行网络请求上传图片并显示
    }

    /**
     * uri转换为bitmap
     *
     * @param uri
     * @return
     */
    public Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(this
                    .getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * @param cutImgUri
     * @Title: startPhotoZoom
     * @Description: 裁剪图片
     * @param @param uri 设定文件
     * @return void 返回类型
     * @throws
     */
    private void startPhotoZoom(Uri uri, Uri cutImgUri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 3);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cutImgUri);// 写入截取的图片
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO_CROP);
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    private File getFile(String path) {
        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            if(pro_rg_banner.getCheckedRadioButtonId() != pro_rg_banner.getChildAt(position).getId()) {
                swichTab(position);
            }
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }
}
