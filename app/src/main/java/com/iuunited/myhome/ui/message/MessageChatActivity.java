package com.iuunited.myhome.ui.message;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.ChatMsgEntity;
import com.iuunited.myhome.entity.Config;
import com.iuunited.myhome.ui.adapter.ChatMessageViewAdapter;
import com.iuunited.myhome.ui.mine.BriefIntroductActivity;
import com.iuunited.myhome.ui.mine.ProIntroductActivity;
import com.iuunited.myhome.util.DefaultShared;
import com.iuunited.myhome.util.IntentUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/11/14 15:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 消息聊天界面
 * Created by xundaozhe on 2016/11/14.
 */

public class MessageChatActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private TextView tv_message_chat;

    private String userType;

    private Button btn_send;
    private EditText et_sendmessage;
    private ListView mListview;
    private ChatMessageViewAdapter mAdapter;
    private List<ChatMsgEntity> mdatas = new ArrayList<>();

    private final static int COUNT = 12;// 初始化数组总数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        initView();
        initData();
        mListview.setSelection(mAdapter.getCount() - 1);
    }

    private String[] msgArray = new String[] { "有大吗", "有！你呢？", "我也有", "那上吧",
            "打啊！你放大啊！", "你TM咋不放大呢？留大抢人头啊？CAO！你个菜B", "2B不解释", "尼滚...",
            "今晚去网吧包夜吧？", "有毛片吗？", "种子一大堆啊~还怕没片？", "OK,搞起！！" };

    private String[] dataArray = new String[] { "2016-11-30 18:00:02",
            "2016-11-30 18:10:22", "2016-11-30 18:11:24",
            "2016-11-30 18:20:23", "2016-11-30 18:30:31",
            "2016-11-30 18:35:37", "2016-11-30 18:40:13",
            "2016-11-30 18:50:26", "2016-11-30 18:52:57",
            "2016-11-30 18:55:11", "2016-11-30 18:56:45",
            "2016-11-30 18:57:33", };
    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_message_chat = (TextView) findViewById(R.id.tv_message_chat);
        btn_send = (Button)findViewById(R.id.btn_send);
        et_sendmessage = (EditText)findViewById(R.id.et_sendmessage);
        mListview = (ListView)findViewById(R.id.listview);
    }

    private void initData() {
        userType = DefaultShared.getStringValue(this, Config.CONFIG_USERTYPE,0+"");
        iv_back.setOnClickListener(this);
        tv_title.setText("对方昵称");
        iv_share.setVisibility(View.GONE);
        if(userType.equals("1")) {
            tv_message_chat.setText("装修商简介");
        }else if(userType.equals("2")){
            tv_message_chat.setText("客户简介");
        }
        tv_message_chat.setVisibility(View.VISIBLE);
        tv_message_chat.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("肖B");
                entity.setMsgType(true);// 收到的消息
            } else {
                entity.setName("必败");
                entity.setMsgType(false);// 自己发送的消息
            }
            entity.setMessage(msgArray[i]);
            mdatas.add(entity);
        }

        mAdapter = new ChatMessageViewAdapter(this, mdatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_chat :
                if(userType.equals("1")) {
                    IntentUtil.startActivity(this, ProIntroductActivity.class);
                }else if(userType.equals("2")) {
                    IntentUtil.startActivity(this, BriefIntroductActivity.class);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_send:
                send();
                break;
        }
    }

    private void send() {
        String contString = et_sendmessage.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("必败");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setMsgType(false);

            mdatas.add(entity);
            mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            et_sendmessage.setText("");// 清空编辑框数据

            mListview.setSelection(mListview.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

//    private void showInputManager(EditText editText) {
//        editText.setFocusable(true);
//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();
//
//        /** 目前测试来看，还是挺准的
//         * 原理：OnGlobalLayoutListener 每次布局变化时都会调用
//         * 界面view 显示消失都会调用，软键盘显示与消失时都调用
//         * */
//        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
//        InputMethodManager inputManager =
//                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//
//    }
//
//    ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            //判断窗口可见区域大小
//            Rect r = new Rect();
//            // getWindowVisibleDisplayFrame()会返回窗口的可见区域高度
//            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//            //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
//            int heightDifference = WT.mScreenHeight - (r.bottom - r.top);
//            boolean isKeyboardShowing = heightDifference > WT.mScreenHeight / 3;
//            if(isKeyboardShowing){
////                D.i("slack","show..."+ r.bottom + " - " + r.top + " = " + (r.bottom - r.top) +","+ heightDifference);
//                // bottomView 需要跟随软键盘移动的布局
//                // setDuration(0) 默认300, 设置 0 ，表示动画执行时间为0，没有过程，只有动画结果了
//                bottomView.animate().translationY(-heightDifference).setDuration(0).start();
//            }else{
////                D.i("slack","hide...");
//                bottomView.animate().translationY(0).start();
//            }
//        }
//    };
}
