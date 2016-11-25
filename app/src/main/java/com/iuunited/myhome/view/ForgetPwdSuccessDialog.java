package com.iuunited.myhome.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.iuunited.myhome.R;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 17:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class ForgetPwdSuccessDialog extends Dialog {

    public TextView tv_success;
    private String text = "密 码\\n修 改 成 功 !";

    public ForgetPwdSuccessDialog(Context context) {
        super(context, R.style.Alert_Dialog_Style);
        initDialog(context);
    }

    private void initDialog(final Context context) {
        setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.widget_dialog_forgetpwd, null);
        setContentView(view);
        tv_success = (TextView) view.findViewById(R.id.tv_success);
        tv_success.setText(text.replace("\\n","\n"));

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.alpha = 1.0f;
        params.width = -2;
        params.height = -2;
        window.setAttributes(params);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
