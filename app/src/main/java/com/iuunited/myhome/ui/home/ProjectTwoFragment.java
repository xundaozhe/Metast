package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragments;
import com.iuunited.myhome.event.ChangeProjectFmEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 11:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/27.
 */
public class ProjectTwoFragment extends BaseFragments implements View.OnClickListener {


    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_two, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        WebSettings settings = webView.getSettings();
        /********TODO 问题页面,android如何获取用户在页面上点击JS的内容********/
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if(mLoadingDialog!=null) {
//                    mLoadingDialog.dismiss();
//                }
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//当打开新的连接时,使用当前的webview,不使用系统其他浏览器
                return true;
            }
        });
        webView.loadUrl("http://www.iuunited.com:9000/");
//        loadUrl("http://www.taobao.com");
    }

    private void initView(View view) {
        webView = (WebView) view.findViewById(R.id.webView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_next :
//                EventBus.getDefault().post(new ChangeProjectFmEvent(2));
//                break;
        }
    }
}
