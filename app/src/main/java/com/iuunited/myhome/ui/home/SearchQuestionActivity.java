package com.iuunited.myhome.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.Helper.ServiceClient;
import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.FilteredItems;
import com.iuunited.myhome.bean.SearchQuestionRequest;
import com.iuunited.myhome.event.QuestionNameEvent;
import com.iuunited.myhome.ui.adapter.SearchFruitAdapter;
import com.iuunited.myhome.util.TextUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.iuunited.myhome.view.ClearEditText;
import com.iuunited.myhome.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.R.string.no;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/30 10:31
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/30.
 */

public class SearchQuestionActivity extends BaseFragmentActivity implements ClearEditText.OnSearchClickListener, AdapterView.OnItemClickListener, ServiceClient.IServerRequestable {

    private RelativeLayout iv_back;
    private ClearEditText et_project_name;
    private TextView tv_search;
    private ListView lv_fruit;
    private SearchFruitAdapter mAdapter;
    private List<FilteredItems> mData = new ArrayList<>();

    private String searchKey;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (RelativeLayout)findViewById(R.id.iv_back);
        et_project_name = (ClearEditText)findViewById(R.id.et_project_name);
        tv_search = (TextView)findViewById(R.id.tv_search);
        lv_fruit = (ListView)findViewById(R.id.lv_fruit);
    }

    private void initData() {
        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        et_project_name.setOnSearchClickListener(this);

        setAdapter();
        lv_fruit.setOnItemClickListener(this);
    }

    private void setAdapter() {
        if(mAdapter == null) {
            mAdapter = new SearchFruitAdapter(this,mData);
            lv_fruit.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back :
                finish();
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search(){
        searchKey = et_project_name.getText().toString().trim();
        if(TextUtils.isEmpty(searchKey)) {
            ToastUtils.showShortToast(this, "请输入搜索关键字!");
            return;
        }else{
            doSearchQuery();
        }
    }

    private void doSearchQuery() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage("搜索中...");
        }
        mLoadingDialog.show();
        SearchQuestionRequest request = new SearchQuestionRequest();
        request.setName(searchKey);
        ServiceClient.requestServer(this,"查询中",request, SearchQuestionRequest.SearchQuestionResponse.class,
                new ServiceClient.OnSimpleActionListener<SearchQuestionRequest.SearchQuestionResponse>() {
                    @Override
                    public void onSuccess(SearchQuestionRequest.SearchQuestionResponse responseDto) {
                        if(mLoadingDialog!=null) {
                            mLoadingDialog.dismiss();
                        }
                        if (responseDto.getOperateCode() == 0) {
                            List<FilteredItems> items = responseDto.getFilteredItems();
                            if(index!=0) {
                                mData.clear();
                            }
                            if(items!=null&&items.size()>0) {
                                for (int i = 0;i<items.size();i++){
                                    FilteredItems items1 = items.get(i);
                                    mData.add(items1);
                                }
                            }else{
                                ToastUtils.showShortToast(SearchQuestionActivity.this,"查询失败,换个关键字试试吧...");
                            }
                            index++;
                            mAdapter.notifyDataSetChanged();
                        }else{
                            ToastUtils.showShortToast(SearchQuestionActivity.this,"查询失败,请稍后再试...");
                        }
                    }

                    @Override
                    public boolean onFailure(String errorMessage) {
                        return false;
                    }
                });
    }

    @Override
    public void onSearchClick(View view) {
        search();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FilteredItems items = mData.get(position);
        if(items!=null) {
            int itemsId = items.getId();
            String itemName = items.getName();
            EventBus.getDefault().post(new QuestionNameEvent(itemsId,itemName));
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoadingDialog(String text) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showCustomToast(String text) {

    }

    @Override
    public boolean getSuccessful() {
        return false;
    }

    @Override
    public void setSuccessful(boolean isSuccessful) {

    }
}
