package com.iuunited.myhome.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.util.ScreenUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 18:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class SelectPhotoDialog extends Dialog {
    private TextView tv_photo, tv_camera, tv_cancel;

    private Context mContext;

    private View.OnClickListener mListener;

    public SelectPhotoDialog(Context context, View.OnClickListener listener) {
        super(context, R.style.Alert_Dialog_Style);
        mListener = listener;
        mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        setCanceledOnTouchOutside(false);

        View view = View.inflate(context, R.layout.widget_dialog_select_photo,
                null);
        setContentView(view);
        init(view);
    }

    public void setText(){
        tv_photo.setText("是");
        tv_camera.setText("否");
    }

    private void init(View view){
        tv_photo = (TextView) view.findViewById(R.id.tv_photo);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_camera = (TextView) view.findViewById(R.id.tv_camera);



        tv_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                if (mListener != null)
                    mListener.onClick(arg0);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                if (mListener != null)
                    mListener.onClick(arg0);

            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                if (mListener != null)
                    mListener.onClick(arg0);

            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.alpha = 1.0f;
        params.width = ScreenUtil.getScreenWidth();
        window.setAttributes(params);
    }
}
