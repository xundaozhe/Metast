package com.iuunited.myhome.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.mine.ProIntroductActivity;
import com.iuunited.myhome.util.IntentUtil;

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

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private TextView tv_message_chat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_message_chat = (TextView) findViewById(R.id.tv_message_chat);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("对方昵称");
        iv_share.setVisibility(View.GONE);
        tv_message_chat.setVisibility(View.VISIBLE);
        tv_message_chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_chat :
                IntentUtil.startActivity(this, ProIntroductActivity.class);
                break;
        }
    }
}
