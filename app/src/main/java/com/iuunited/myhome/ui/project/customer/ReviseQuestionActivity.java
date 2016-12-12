package com.iuunited.myhome.ui.project.customer;

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

    private WebView webView;


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
        webView = (WebView)findViewById(R.id.webView);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setVisibility(View.GONE);
        iv_share.setVisibility(View.GONE);
        WebSettings settings = webView.getSettings();

        /********TODO 问题页面,android如何获取用户在页面上点击JS的内容********/
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                if(mLoadingDialog!=null) {
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//当打开新的连接时,使用当前的webview,不使用系统其他浏览器
                return true;
            }
        });
        loadUrl("http://www.iuunited.com:9000/");
//        loadUrl("http://www.taobao.com");
    }

    private void loadUrl(String url){
        if(webView!=null) {
            webView.loadUrl(url);
            if(mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
                mLoadingDialog.setMessage("加载中...");
            }
            mLoadingDialog.show();
            webView.reload();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
//            this.finish();
            return true;
        }
        this.finish();
        return false;
    }

}
