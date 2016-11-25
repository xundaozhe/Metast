package com.iuunited.myhome.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.iuunited.myhome.R;


/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/9/29 16:33
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/9/29.
 */
public class LoadingDialog extends Dialog {

    private TextView mTvMessage = null;

    public LoadingDialog(Context context) {
        super(context, R.style.Alert_Dialog_Style);
        initProgressDialog(context);
    }

    private void initProgressDialog(Context context) {
        setCanceledOnTouchOutside(false);

        View view = View.inflate(context, R.layout.widget_dialog_loading, null);
        setContentView(view);

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = -2;
        params.height = -2;
        window.setAttributes(params);
    }

    public void setMessage(String msg) {
        mTvMessage.setText(msg);
    }
}
