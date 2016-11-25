package com.iuunited.myhome.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.ui.StartActivity;
import com.iuunited.myhome.ui.login.SettingPwdActivity;
import com.iuunited.myhome.util.IntentUtil;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/26 16:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/26.
 */
public class RegisterSuccessDialog extends Dialog {

        public Button btn_into;

        public RegisterSuccessDialog(Context context) {
            super(context, R.style.Alert_Dialog_Style);
            initDialog(context);
        }

    private void initDialog(final Context context) {
        setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.widget_dialog_success, null);
        setContentView(view);
        btn_into = (Button) view.findViewById(R.id.btn_into);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.alpha = 1.0f;
        params.width = -2;
        params.height = -2;
        window.setAttributes(params);
    }

    public void setCheck(final Activity activity){
        btn_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(activity,MainActivity.class);
            }
        });
    }
}
