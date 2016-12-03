package com.iuunited.myhome.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.task.ICommentListener;
import com.iuunited.myhome.ui.StartActivity;
import com.iuunited.myhome.ui.project.ProjectDetailsActivity;
import com.iuunited.myhome.util.ScreenUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/3 10:34
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 创建领域的专业知识
 * Created by xundaozhe on 2016/11/3.
 */
public class CreateLoreDialog extends Dialog {

    private TextView tv_title;
    private EditText et_message;
    private Button dialog_btn_cancel;
    private Button dialog_btn_sure;
    private TextView mEtMessage;

    private ICommentListener mCommentListener;
    private Context mContext;
    private String titleText;
    private String textHint;



    public CreateLoreDialog(Context context,ICommentListener listener, String title, String text) {
        super(context, R.style.Alert_Dialog_Style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCommentListener = listener;
        mContext = context;
        titleText = title;
        textHint = text;
        initDialog(context);
    }

    private void initDialog(final Context context) {
        setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.widget_dialog_create_lore, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        et_message = (EditText) view.findViewById(R.id.et_message);
        dialog_btn_cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);
        dialog_btn_sure = (Button) view.findViewById(R.id.dialog_btn_sure);
        mEtMessage = (TextView) view.findViewById(R.id.et_message);

        dialog_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCommentListener != null) {
                    mCommentListener.commentClick(v.getId(),mEtMessage.getText().toString());
                }
            }
        });

        dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(mCommentListener != null) {
                    mCommentListener.commentClick(v.getId(),mEtMessage.getText().toString());
                }
            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = ScreenUtil.getScreenWidth();
        window.setAttributes(params);

        tv_title.setText(titleText);
        dialog_btn_sure.setText("创建");
        dialog_btn_cancel.setText("取消");
        mEtMessage.setHint(textHint);
    }




}
