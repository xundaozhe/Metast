package com.iuunited.myhome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.bean.AnswerBean;
import com.iuunited.myhome.util.TextUtils;

import java.util.List;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/26 19:08
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/26.
 */

public class DetailsQuestionAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<AnswerBean> mDatas;

    public DetailsQuestionAdapter(Context context,List<AnswerBean> lists){
        this.mContext = context;
        this.mInflater = mInflater.from(context);
        this.mDatas = lists;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnswerBean answerBean = mDatas.get(position);
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_details_question, null);
            holder.mTvQuestionName = (TextView) convertView.findViewById(R.id.tv_question_name);
            holder.mTvQuestionAnswer = (TextView) convertView.findViewById(R.id.tv_question_answer);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(answerBean!=null) {
            String questionName = answerBean.getQuestionName();
            if(!TextUtils.isEmpty(questionName)) {
                holder.mTvQuestionName.setText(questionName);
            }
            int questionType = answerBean.getQuestionType();
            if (questionType == 1 || questionType == 2) {
                String answer = answerBean.getOptionTexts();
                String replace = null;
                if (!TextUtils.isEmpty(answer)) {
                    if(answer.contains(",")) {
                        replace = answer.replace(",", "\n");
                        holder.mTvQuestionAnswer.setText(replace);
                    }else{
                        holder.mTvQuestionAnswer.setText(answer);
                    }
                }

            }
            if (questionType == 0) {
                String optionValues = answerBean.getOptionValues();
                if (!TextUtils.isEmpty(optionValues)) {
                    holder.mTvQuestionAnswer.setText(optionValues);
                }
            }
        }
        return convertView;
    }

    static class ViewHolder{
        private TextView mTvQuestionName;
        private TextView mTvQuestionAnswer;
    }
}
