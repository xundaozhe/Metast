package com.iuunited.myhome.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.ui.MainActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/29 12:03
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 关于我们
 * Created by xundaozhe on 2016/10/29.
 */
public class AboutMyActivity extends BaseFragmentActivity {

    private RelativeLayout iv_back;
    private TextView tv_title;
    private ImageView iv_share;

    private Button btn_about;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setColor(this,getResources().getColor(R.color.myHomeBlue));
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        btn_about = (Button)findViewById(R.id.btn_about);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_title.setText("关于我们");
//        iv_share.setVisibility(View.GONE);
        iv_share.setOnClickListener(this);
        btn_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                Intent intent = new Intent();
                intent.putExtra("mainFragmentId",4);
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_share:
//                new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).withText("hello,I am MyHome")
//                        .setCallback(umShareListener).share();
                break;
            case R.id.btn_about:
               IntentUtil.startActivity(this,MyHomeSynopsisActivity.class);
                break;
        }
    }

    /**
     * 友盟分享SDK需要的
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
