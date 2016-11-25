package com.iuunited.myhome.ui.mine;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.bean.BadgeBean;
import com.iuunited.myhome.task.ICommentListener;
import com.iuunited.myhome.ui.adapter.BadgeAdapter;
import com.iuunited.myhome.ui.adapter.GridAdapter;
import com.iuunited.myhome.ui.adapter.TerritoryLoreAdapter;
import com.iuunited.myhome.ui.home.PhotoActivity;
import com.iuunited.myhome.ui.home.TestPicActivity;
import com.iuunited.myhome.util.Bimp;
import com.iuunited.myhome.util.FileUtils;
import com.iuunited.myhome.view.MyGridView;
import com.iuunited.myhome.view.MyListView;
import com.iuunited.myhome.view.smartimage.CreateLoreDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.toolsfinal.StringUtils;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 10:54
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class ProDetailsFragment extends BaseFragments implements View.OnClickListener {

    private final static String TAG = "ProDetailsFragment";
    private static final int TAKE_PICTURE = 0x000000;
    private static final int IMAGE_LOADING = 0x001;

    private Context mContext;
    private RecyclerView recyclerView;
    private BadgeAdapter mAdapter;
    private List<BadgeBean> mDatas;
    private int[] image = {R.drawable.bbb, R.drawable.warranty,
            R.drawable.compensation, R.drawable.references,
            R.drawable.business,R.drawable.liabilite,R.drawable.accreditation};
    private String[] title ={"bbb","warranty","compensation",
            "references","business-\\nlicense","liabilite-\\ninsurance","accreditation"};

    private MyGridView gv_publish_image;

    private Uri photoUri;
    private PopupWindows mPopupWindow;
    private GridAdapter mGridAdapter;

    private TextView tv_edit_photo;

    private MyListView lv_territory_lore;
    private TerritoryLoreAdapter mTerritoryLoreAdapter;
    private TextView tv_lore_edit;
    private CreateLoreDialog mLoreDialog;
//    private List<String> datas = new ArrayList<>();
    private List<HashMap<Integer,String>> datas = new ArrayList<>();
    private int index = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_details, null);
        mContext = getActivity();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        int spanCount = 1;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        tv_lore_edit = (TextView) view.findViewById(R.id.tv_lore_edit);
        lv_territory_lore = (MyListView) view.findViewById(R.id.lv_territory_lore);
        initLoreAdapter();
        tv_edit_photo = (TextView) view.findViewById(R.id.tv_edit_photo);
        gv_publish_image = (MyGridView) view.findViewById(R.id.gv_publish_image);
        setAdapter();

    }

    private void setAdapter() {
        mGridAdapter = new GridAdapter(mContext);
        gv_publish_image.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        BadgeBean bean = null;
        if(bean ==null) {
            for (int i = 0;i<image.length;i++){
                bean = new BadgeBean();
                bean.setImageId(image[i]);
                for (int j = i;j<title.length;j++){
                    bean.setTitle(title[j]);
                    mDatas.add(bean);
                    break;
                }
            }
        }
        if(mDatas!=null) {
            if(mAdapter==null) {
                mAdapter = new BadgeAdapter(mContext,mDatas);
                recyclerView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
        }

        tv_edit_photo.setOnClickListener(this);

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

        tv_lore_edit.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                Uri selectedImage = photoUri;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
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
        }
    }

    @Override
    public void onStart() {
        loadingImg();
        super.onStart();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit_photo :
                gv_publish_image.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_lore_edit:
                mLoreDialog = new CreateLoreDialog(mContext, new ICommentListener() {
                    @Override
                    public void commentClick(int id, String content) {
                        switch (id) {
                            case R.id.dialog_btn_sure :
                                if(StringUtils.isEmpty(content)) {
                                    return;
                                }
                                HashMap<Integer, String> data = new HashMap<>();
                                if(datas.size()!=0) {
                                    index++;
                                }else{
                                    index = 0;
                                }
                                if(datas.size()<index) {
                                    index = index - datas.size();
                                    index = index+datas.size()-index;
                                    data.put(index,content);
                                    datas.add(data);
                                }else {
                                    data.put(index, content);
                                    datas.add(data);
                                }
                                mTerritoryLoreAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                },"领域的专业知识","");
                mLoreDialog.show();
                break;
        }
    }


    private void initLoreAdapter() {
        if(mTerritoryLoreAdapter == null) {
            mTerritoryLoreAdapter = new TerritoryLoreAdapter(mContext,datas);
            lv_territory_lore.setAdapter(mTerritoryLoreAdapter);
            mTerritoryLoreAdapter.notifyDataSetChanged();
        }
        mTerritoryLoreAdapter.notifyDataSetChanged();
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
                    Intent intent = new Intent(getActivity(), TestPicActivity.class);
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

    public void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
        ContentValues values = new ContentValues();
        Uri uri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        photoUri = uri;
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }
}
