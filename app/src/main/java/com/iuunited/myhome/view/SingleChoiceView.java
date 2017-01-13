package com.iuunited.myhome.view;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.iuunited.myhome.R;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/28 10:40
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/28.
 */

public class SingleChoiceView extends FrameLayout implements Checkable{

    private TextView mSingleText;
    public RadioButton mSingleBtn;


    public SingleChoiceView(Context context) {
        super(context);
        View.inflate(context, R.layout.item_single_choice,this);
        mSingleText = (TextView) findViewById(R.id.single_text);
        mSingleBtn = (RadioButton) findViewById(R.id.checkedView);
    }

    public void setText(String text){
        mSingleText.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        mSingleBtn.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mSingleBtn.isChecked();
    }

    @Override
    public void toggle() {
        mSingleBtn.toggle();
    }


}
