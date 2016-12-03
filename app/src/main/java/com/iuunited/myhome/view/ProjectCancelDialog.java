package com.iuunited.myhome.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.task.ICancelListener;
import com.iuunited.myhome.task.ICommentListener;
import com.iuunited.myhome.util.ScreenUtil;

import static com.iuunited.myhome.R.id.et_message;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/27 13:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/11/27.
 */

public class ProjectCancelDialog extends Dialog {

    private TextView tv_title;
    private Button dialog_btn_cancel;
    private Button dialog_btn_sure;
    private TextView mEtMessage;

    private ICancelListener mICancelListener;
    private Context mContext;
    private String titleText;
    private String textHint;



    public ProjectCancelDialog(Context context,ICancelListener listener, String title, String text) {
        super(context, R.style.Alert_Dialog_Style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mICancelListener = listener;
        mContext = context;
        titleText = title;
        textHint = text;
        initDialog(context);
    }

    private void initDialog(final Context context) {
        setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.widget_dialog_cancel, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        dialog_btn_cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);
        dialog_btn_sure = (Button) view.findViewById(R.id.dialog_btn_sure);
        mEtMessage = (TextView) view.findViewById(et_message);

        dialog_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mICancelListener != null) {
                    mICancelListener.cancelClick(v.getId(), getOwnerActivity());
                }
            }
        });

        dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                if(mICancelListener != null) {
//                    mICancelListener.commentClick(v.getId(),mEtMessage.getText().toString());
//                }
            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = ScreenUtil.getScreenWidth();
        window.setAttributes(params);

        tv_title.setText(titleText);
        dialog_btn_sure.setText("是");
        dialog_btn_cancel.setText("否");
        mEtMessage.setText(textHint);
    }
}
