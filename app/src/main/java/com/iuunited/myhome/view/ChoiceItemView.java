package com.iuunited.myhome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/28 15:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/28.
 */

public class ChoiceItemView extends LinearLayout implements Checkable {

    private TextView mTitle;
    private CheckBox selectBox;

    public ChoiceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_multiple_choice, this, true);
        mTitle  = (TextView) view.findViewById(R.id.author);
        selectBox  = (CheckBox) view.findViewById(R.id.radio);
    }

    public void setName(String text){
        mTitle.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        selectBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return selectBox.isChecked();
    }

    @Override
    public void toggle() {
        selectBox.toggle();
    }
}
