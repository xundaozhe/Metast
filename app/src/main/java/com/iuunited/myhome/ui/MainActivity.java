package com.iuunited.myhome.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.ActivityCollector;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.header.HeaderFragment;
import com.iuunited.myhome.ui.home.HomeFragment;
import com.iuunited.myhome.ui.message.MessageFragment;
import com.iuunited.myhome.ui.mine.MineFragment;
import com.iuunited.myhome.ui.project.ProjectFragment;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.HeaderPopupWin;
import com.iuunited.myhome.view.MyFragmentTabhost;

import static com.iuunited.myhome.R.id.view;
import static com.iuunited.myhome.base.BaseFragmentActivity.setColor;
import static com.iuunited.myhome.util.UIUtils.getResources;

public class MainActivity extends BaseFragmentActivity {

    private String[] titles = {"首页","工程"," ","消息","我的"};

    private int[] images = {R.drawable.home_selector,R.drawable.engineering_selector,R.drawable.identity_icon,
            R.drawable.message_selector,R.drawable.mine_selector};
    
    private MyFragmentTabhost tabHost;
    
    private int fragmentId = -1;

    private WindowManager.LayoutParams params;

    private String typeMessage;

    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        ActivityCollector.finishAll();
        initView();
    }

    private void initView() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,"");
        tabHost = (MyFragmentTabhost) findViewById(R.id.tabHost);
        tabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.content);
        tabHost.getTabWidget().setDividerDrawable(null);
        //添加标签页面
        tabHost.addTab(tabHost.newTabSpec("one")
                .setIndicator(getView(0,titles,R.drawable.home_selector)), HomeFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("two")
                .setIndicator(getView(1,titles,R.drawable.engineering_selector)), ProjectFragment.class, null);

        if(userType!=null) {
            if(userType.equals("1")) {
                tabHost.addTab(tabHost.newTabSpec("three")
                        .setIndicator(getView(2,titles,R.drawable.identity_icon)), HeaderFragment.class, null);
            }else{
                tabHost.addTab(tabHost.newTabSpec("three")
                        .setIndicator(getView(2,titles,R.drawable.icon_pro)), HeaderFragment.class, null);
            }
        }

        tabHost.addTab(tabHost.newTabSpec("four")
                .setIndicator(getView(3, titles, R.drawable.message_selector)), MessageFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("five")
                .setIndicator(getView(4,titles,R.drawable.mine_selector)), MineFragment.class, null);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//                if(tabId.equals("three")) {
//                    HeaderFragment headerFragment = new HeaderFragment();
//                    headerFragment.show(getSupportFragmentManager(),"fragmentDialog");
//
//                }
            }
        });

        tabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderFragment headerFragment = new HeaderFragment();
                headerFragment.show(getSupportFragmentManager(),"fragmentDialog");
            }
        });
//        onclick();
    }

    private void onclick() {
        typeMessage = DefaultShared.getStringValue(this,"typeMessage","");
        if (!TextUtils.isEmpty(typeMessage)) {
            if(!typeMessage.equals("")) {
                if (typeMessage.equals("message")) {
                    tabHost.getTabWidget().getChildTabViewAt(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShortToast(MainActivity.this,"请点击退出后再进行此类操作!");
                        }
                    });
                    tabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShortToast(MainActivity.this,"请点击退出后再进行此类操作!");
                        }
                    });
                    tabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShortToast(MainActivity.this,"请点击退出后再进行此类操作!");
                        }
                    });
                    tabHost.getTabWidget().getChildTabViewAt(3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShortToast(MainActivity.this,"请点击退出后再进行此类操作!");
                        }
                    });
                    tabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showShortToast(MainActivity.this,"请点击退出后再进行此类操作!");
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentId = getIntent().getIntExtra("mainFragmentId",-1);
        if(fragmentId == 0) {
            tabHost.setCurrentTab(0);
        }else  if(fragmentId == 1) {
            tabHost.setCurrentTab(1);
        }else  if(fragmentId == 2) {
            tabHost.setCurrentTab(2);
//            showPopFromBottom();
        }else  if(fragmentId == 3) {
            tabHost.setCurrentTab(3);
        }else  if(fragmentId == 4) {
            tabHost.setCurrentTab(4);
        }
    }

    private void showPopFromBottom() {
        HeaderPopupWin headerPopupWin = new HeaderPopupWin(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        headerPopupWin.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params =getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        headerPopupWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }

//    public View getView(int idx, String [] title, int[] image){
//        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
//        ((TextView) view.findViewById(R.id.tab_text)).setText(title[idx]);
//        ((ImageView) view.findViewById(R.id.tab_image)).setImageResource(image[idx]);
//        return view;
//    }
    public View getView(int idx, String [] title, int image){
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ((TextView) view.findViewById(R.id.tab_text)).setText(title[idx]);
        ((ImageView) view.findViewById(R.id.tab_image)).setImageResource(image);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
