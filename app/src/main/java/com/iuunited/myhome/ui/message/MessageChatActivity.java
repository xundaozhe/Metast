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
import com.iuunited.myhome.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.iuunited.myhome.util.UIUtils.getResources;

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

    private final static int COUNT = 8;// 初始化数组总数
// AIzaSyAuEzV57Lqjf0ZQzQJUp6tCm4IqtW-IwLY
    private boolean isMe = true;//是谁发出的消息
    private TextView tv_msgType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
        mListview.setSelection(mAdapter.getCount() - 1);
    }

    private String[] msgArray = new String[] { "你好", "您好,请问有什么可以帮到您?",
            "我想知道修好我的门大概需要多长时间?", "按照目前的进度,再3个小时就可以了",
            "好的,那您帮忙算一下一共是多少钱呢?", "280", "好的,谢谢", "不客气"};

    private String[] dataArray = new String[] { "2016-11-30 18:00:02",
            "2016-11-30 18:10:22", "2016-11-30 18:11:24",
            "2016-11-30 18:20:23", "2016-11-30 18:30:31",
            "2016-11-30 18:35:37", "2016-11-30 18:40:13",
            "2016-11-30 18:50:26" };
    private void initView() {
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_message_chat = (TextView) findViewById(R.id.tv_message_chat);
        btn_send = (Button)findViewById(R.id.btn_send);
        et_sendmessage = (EditText)findViewById(R.id.et_sendmessage);
        mListview = (ListView)findViewById(R.id.listview);
        tv_msgType = (TextView)findViewById(R.id.tv_msgType);
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
                entity.setName("Bobo");
                entity.setMsgType(true);// 收到的消息
            } else {
                entity.setName("Eric");
                entity.setMsgType(false);// 自己发送的消息
            }
            entity.setMessage(msgArray[i]);
            mdatas.add(entity);
        }

        mAdapter = new ChatMessageViewAdapter(this, mdatas);
        mListview.setAdapter(mAdapter);
        tv_msgType.setOnClickListener(this);
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
                if(isMe) {
                    send();
                }else{
                    accept();
                }
                break;
            case R.id.tv_msgType:
                if(isMe) {
                    tv_msgType.setText("对方");
                    isMe = false;
                }else{
                    tv_msgType.setText("自己");
                    isMe = true;
                }
                break;
        }
    }

    private void accept(){
        String contString = et_sendmessage.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("Bobo");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setMsgType(true);

            mdatas.add(entity);
            mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            et_sendmessage.setText("");// 清空编辑框数据

            mListview.setSelection(mListview.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }else{
            ToastUtils.showShortToast(this,"不能发送空消息!");
        }
    }

    private void send() {
        String contString = et_sendmessage.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("Eric");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setMsgType(false);

            mdatas.add(entity);
            mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            et_sendmessage.setText("");// 清空编辑框数据

            mListview.setSelection(mListview.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }else{
            ToastUtils.showShortToast(this,"不能发送空消息!");
        }
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

}
