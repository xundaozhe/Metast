package com.iuunited.myhome.ui.header;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.view.HeaderPopupWin;

import static com.iuunited.myhome.R.id.view;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/30 19:07
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/30.
 */

public class HeaderFragment extends DialogFragment {

    private static final String TAG = "HeaderFragment";

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private Context mContext;
    private View view;

    private WindowManager.LayoutParams params;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_header,null);
//        mContext = getActivity();
//        initView(view);
//        initData();
////        showPopFromBottom(view);
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.take_photo_anim);
        dialog.setContentView(R.layout.home_header_pop);
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private void showPopFromBottom(View view) {
        HeaderPopupWin headerPopupWin = new HeaderPopupWin(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        headerPopupWin.showAtLocation(view.findViewById(R.id.pop_parent), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
         params = getActivity().getWindow().getAttributes();
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);
        headerPopupWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getActivity().getWindow().getAttributes();
                params.alpha = 1.0f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }

    private void initView(View view) {
        iv_back = (RelativeLayout) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);

    }

    private void initData() {
        iv_back.setVisibility(View.GONE);
        tv_title.setText("MyHome");
        iv_share.setVisibility(View.GONE);
    }


}
