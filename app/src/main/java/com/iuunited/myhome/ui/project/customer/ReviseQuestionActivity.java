package com.iuunited.myhome.ui.project.customer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.view.LoadingDialog;

import java.util.ArrayList;

import static android.R.attr.description;
import static com.iuunited.myhome.R.id.view;
import static com.iuunited.myhome.R.id.webView;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/7 16:28
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes H5问题页面
 * Created by xundaozhe on 2016/12/7.
 */

public class ReviseQuestionActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_question);
        initView();
        initData();
    }

    private void initView() {

        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
        }
    }

}
